/*
 * Copyright (c) 2017, RockScript.io. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.rockscript;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

public class Deploy extends Rock {

  @Override
  public String getCommandName() {
    return "deploy";
  }

  @Override
  protected Options createOptions() {
    return null;
  }

  @Override
  protected void parse(CommandLine commandLine) {

  }

  @Override
  public void execute() throws Exception {
  }
}
