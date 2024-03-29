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

package org.ops5.push.ProbClass;


import org.ops5.push.core.GAIndividual;
import org.ops5.push.core.GATestCase;
import org.ops5.push.core.Interpreter;
import org.ops5.push.core.ObjectPair;
import org.ops5.push.core.Program;
import org.ops5.push.core.PushGP;
import org.ops5.push.core.PushGPIndividual;
import org.ops5.push.core.intStack;
import org.ops5.push.core.TestCase.TestCaseGenerator;


/**
 * This problem class implements symbolic regression for integers. See also IntSymbolicRegression for integer symbolic
 * regression.
 */
public class IntSymbolicRegression extends PushGP
{
  private static final long serialVersionUID = 1L;

  protected float _noResultPenalty = 1000;

  protected void InitFromParameters()
      throws Exception
  {
    super.InitFromParameters();

    String cases = GetParam("test-cases", true);
    String casesClass = GetParam("test-case-class", true);
    if (cases == null && casesClass == null)
    {
      throw new Exception("No acceptable test-case parameter.");
    }

    if (casesClass != null)
    {
      // Get test cases from the TestCasesClass.
      Class<?> iclass = Class.forName(casesClass);
      Object iObject = iclass.newInstance();
      if (!(iObject instanceof TestCaseGenerator))
      {
        throw (new Exception("test-case-class must inherit from class TestCaseGenerator"));
      }

      TestCaseGenerator testCaseGenerator = (TestCaseGenerator) iObject;
      int numTestCases = testCaseGenerator.TestCaseCount();

      for (int i = 0; i < numTestCases; i++)
      {
        ObjectPair testCase = testCaseGenerator.TestCase(i);

        Integer in = (Integer) testCase._first;
        Integer out = (Integer) testCase._second;

        Print(";; Fitness case #" + i + " input: " + in + " output: " + out + "\n");

        _testCases.add(new GATestCase(in, out));
      }
    }
    else
    {
      // Get test cases from test-cases.
      Program caselist = new Program(cases);

      for (int i = 0; i < caselist.size(); i++)
      {
        Program p = (Program) caselist.peek(i);

        if (p.size() < 2) throw new Exception("Not enough elements for fitness case \"" + p + "\"");

        Integer in = new Integer(p.peek(0).toString());
        Integer out = new Integer(p.peek(1).toString());

        Print(";; Fitness case #" + i + " input: " + in + " output: " + out + "\n");

        _testCases.add(new GATestCase(in, out));
      }
    }
  }

  protected void InitInterpreter(Interpreter inInterpreter)
  {
  }

  public float EvaluateTestCase(GAIndividual inIndividual, Object inInput, Object inOutput)
  {
    _interpreter.ClearStacks();

    int currentInput = (Integer) inInput;

    intStack stack = _interpreter.intStack();

    stack.push(currentInput);

    // Must be included in order to use the input stack.
    _interpreter.inputStack().push(currentInput);

    _interpreter.Execute(((PushGPIndividual) inIndividual).Program, _executionLimit);

    int result = stack.top();
    // System.out.println( _interpreter + " " + result );

    // Penalize individual if there is no result on the stack.
    if (stack.size() == 0)
    {
      return _noResultPenalty;
    }

    return result - ((Integer) inOutput);
  }
}
