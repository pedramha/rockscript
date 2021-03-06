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
package io.rockscript.api.events;

import io.rockscript.api.events.ExecutableEvent;
import io.rockscript.engine.impl.EngineScriptExecution;

public class ScriptEndedEvent extends ExecutableEvent<EngineScriptExecution> {

  /** constructor for gson serialization */
  ScriptEndedEvent() {
  }

  public ScriptEndedEvent(EngineScriptExecution scriptExecution) {
    super(scriptExecution);
  }

  @Override
  public void execute(EngineScriptExecution execution) {
    execution.setEnd(time);
  }

  @Override
  public boolean isUnlocking() {
    return true;
  }

  @Override
  public boolean isReplay() {
    return true;
  }

  @Override
  public String toString() {
    return "[" + scriptExecutionId + "] " +
        "Script execution ended";
  }

}
