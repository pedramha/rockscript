/*
 * Copyright (c) 2017 RockScript.io.
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.rockscript.api.commands;

import io.rockscript.Engine;
import io.rockscript.api.Command;
import io.rockscript.api.events.ScriptVersionSavedEvent;
import io.rockscript.api.model.Script;
import io.rockscript.api.model.ScriptVersion;
import io.rockscript.engine.EngineException;
import io.rockscript.engine.impl.Parse;
import io.rockscript.engine.impl.ScriptStore;
import io.rockscript.http.servlet.BadRequestException;

import java.util.List;

/** Saves a new script version, but does not make it the active one.
 *
 * Required fields: {@link #scriptText(String)}
 *
 * Example usage:
 * Use it like this:
 * <code>
 *   SaveScriptVersionResponse response = new SaveScriptVersionCommand()
 *     .name("Approval")
 *     .text("...the script text...")
 *     .activate()
 *     .execute(engine);
 * </code>
 *
 * DeployScriptVersionCommand's are serializable with Gson.
 */
public class SaveScriptVersionCommand implements Command<ScriptVersion> {

  protected String scriptId;
  protected String scriptName;
  protected String scriptText;

  @Override
  public String getType() {
    return "saveScript";
  }

  @Override
  public ScriptVersion execute(Engine engine) {
    Parse parse = engine.getScriptParser().parseScriptText(scriptText);

    ScriptStore scriptStore = engine.getScriptStore();

    if (scriptId!=null) {
      Script script = scriptStore.findScriptById(scriptId);
      BadRequestException.throwIfNull(script, "Script %s does not exist", scriptId);
      scriptName = script.getName();
    } else if (scriptName==null) {
      scriptName = "Unnamed script";
    }

    ScriptVersion scriptVersion = new ScriptVersion();
    String scriptVersionId = scriptVersion.getId();
    if (scriptVersionId==null) {
      scriptVersionId = engine.getScriptVersionIdGenerator().createId();
    }
    scriptVersion.setId(scriptVersionId);
    scriptVersion.setScriptId(scriptId);
    scriptVersion.setScriptName(scriptName);
    scriptVersion.setText(scriptText);
    scriptVersion.setErrors(parse.getErrors());
    scriptVersion.setActive(getActivate() ? Boolean.TRUE : null);

    engine
      .getEventDispatcher()
      .dispatch(new ScriptVersionSavedEvent(scriptVersion));

    scriptStore.addParsedScriptAstToCache(parse, scriptVersion);

    return scriptVersion;
  }

  protected boolean getActivate() {
    return false;
  }

  public String getScriptName() {
    return this.scriptName;
  }
  public void setScriptName(String scriptName) {
    this.scriptName = scriptName;
  }
  /** (Optional) the script name */
  public SaveScriptVersionCommand scriptName(String scriptName) {
    this.scriptName = scriptName;
    return this;
  }

  public String getScriptText() {
    return this.scriptText;
  }
  public void setScriptText(String scriptText) {
    this.scriptText = scriptText;
  }
  /** (Required) the script text */
  public SaveScriptVersionCommand scriptText(String scriptText) {
    this.scriptText = scriptText;
    return this;
  }
}
