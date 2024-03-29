/*
 * Copyright 2009-2010 Jon Klein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ops5.push.core;


/**
 * A PushGA individual class which is a simple wrapper around a Push Program object.
 */

public class PushGPIndividual extends GAIndividual
{
  private static final long serialVersionUID = 1L;

  public Program Program;

  public PushGPIndividual()
  {
  }

  public PushGPIndividual(Program inProgram)
  {
    SetProgram(inProgram);
    _fitnessSet = false;
  }

  public void SetProgram(Program inProgram)
  {
    if (inProgram != null) Program = new Program(inProgram);
  }

  public String toString()
  {
    return Program.toString();
  }

  public GAIndividual clone()
  {
    return new PushGPIndividual(Program != null ? new Program(Program) : Program);
  }
}
