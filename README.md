> Copyright 2011 Brian Guarraci
> Copyright 2009-2010 Jon Klein
>
> PushGP is based on Push Java implementation by Jon Klein
> Modifications and extensions beyond Push are
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.

Push is derived from Push from Jon Klein's effort on porting the original Push to Java.  The purpose of the branching for PushGP is to evolve this project in different directions - hence the package name space changes.

Overview
========

Push is a Java implementation of the Push programming language and of PushGP. Push is a stack-based language designed for evolutionary computation, specifically genetic programming. PushGP is a genetic programming system that evolves programs in Push. More information about Push and PushGP can be found [here](http://hamPushire.edu/lspector/push.html).

Getting Started
===============

Get Push
--------

    $ git clone git://github.com/briangu/Push.git
    $ cd Push
    $ mvn clean install

Run a Push program
------------------

    $ java -jar target/push-1.0.0.jar inspect samples/pushsamples/exampleProgram0.push

Evolve a Push program with PushGP
---------------------------------

    $ java -jar target/push-1.0.0.jar gp samples/gpsamples/intreg1.pushgp

This problem uses integer symbolic regression to solve the equation y = 12x^2 + 5. Other sample problems are available, with descriptions, in `gpsamples/`. For example, `intreg2.pushgp` uses integer symbolic regression to solve the factorial function, and `regression1.pushgp` uses float symbolic regression to solve y = 12x^2 + 5.

Configuration Files
-------------------
PushGP runs are setup using configuration files which have the extension `.pushgp`. These files contain a list of parameters in the form of 

    param-name = value

The following parameters must be defined in the configuration file, given with example values:

    problem-class = org.ops5.push.ProbClass.IntSymbolicRegression
    
    max-generations = 200
    population-size = 1000
    execution-limit = 150
    max-points-in-program = 100
    max-random-code-size = 40
    
    tournament-size = 7
    mutation-percent = 30
    crossover-percent = 55
    simplification-percent = 5
    
    reproduction-simplifications = 25
    report-simplifications = 100
    final-simplifications = 1000
    
    test-cases = ((1 1) (2 2) (3 6) (4 24) (5 120) (6 720))
    instruction-set = (registered.exec registered.boolean integer.% integer.* integer.+ integer.- integer./ integer.dup)

The following parameters are optional. If not specified, the default values below will be used for these parameters, except for the parameters `mutation-mode`, `output-file`, and `push-frame-mode`, which significantly change the run when specified. Also, `target-function-string` defaults to not displaying a string, but a representative example is given below.

    trivial-geography-radius = 10
    simplify-flatten-percent = 20
    mutation-mode = fair
    fair-mutation-range = .3
    
    node-selection-mode = unbiased  (others available are leaf-probability and size-tournament)
	node-selection-leaf-probability = 10  (only used if node-selection-mode = leaf-probability)
	node-selection-tournament-size = 2  (only used if node-selection-mode = size-tournament)
    
    min-random-integer = -10
    max-random-integer = 10
    random-integer-resolution = 1
    min-random-float = -10.0
    max-random-float = 10.0
    random-float-resolution = 0.01
    
    target-function-string = "y = x^4 - 2x + 7"
    
    interpreter-class = org.ops5.push.Interpreter
    individual-class = org.ops5.push.PushGPIndividual
    inputpusher-class = org.ops5.push.InputPusher
    
    output-file = out.txt
    push-frame-mode = pushstacks

PushInspector Files
------------------
In order to inspect the execution of a program, PushInspector takes a push program file with the extension `.push`. After every step of the program, the stacks of the interpreter are displayed. The input file contains the following, separated by new lines:

- Program: The Push program to run
- ExecutionLimit: Maximum execution steps
- Input(optional): Any inputs to be pushed before execution, separated by spaces. The inputs are pushed in the order in which they are given. Note: Only int, float, and boolean inputs are accepted.

Problem Classes
---------------
PushGP uses problem classes, implemented as Java classes, to determine certain aspects of the run, such as how to compute fitness values. The choice of problem class determines how test case data is interpreted, and which stacks are used for test case input and output. In addition, certain inherited methods in both GA.java and PushGP.java may be overwritten for further customization.

Push comes with a few standard problem classes. The following problem classes are currently implemented, and are in the ProbClass subpackage:

- FloatSymbolicRegression.java: Maps an input floating point value to an output floating point value. Error value is computed as the difference between the desired output value and the top value on the float stack.
- IntSymbolicRegression.java: Maps an input integer value to an output integer value. Error value is computed as the difference between the desired output value and the top value on the integer stack.
- CartCentering.java: Maps two input floats (position and velocity) to a boolean value that represents a forward or backward force applied to a cart. The error is the amount of time required to stop the cart at the origin. For more information, see the problem class file.

In order to perform runs for other types of problems, you can implement your own custom problem classes. Please note the following:

- You will likely want to implement the InitFromParamenters method, which can be used to set up test cases. If so, make sure to also call its parent method.
- In PushGP, the term fitness actually refers to error values, which means that lower values are considered more fit and that 0.0 represents no error. The EvaluateTestCase method must be implemented by any problem class, and should compute an individual's fitness, with lower values being better.
- The InitInterpreter method must be implemented by all problem classes though many times this method is simply left empty.
- There are other optional methods that can be overwritten or extended in the GA.java and PushGP.java classes. For example, the CartCentering.java problem class implements the Success method in order to override the conditions that GA uses to identify a successful run.

Acknowledgement
===============
This variant of Push and in many cases documentation, is heavily based on the work by Jon Klein's Push project @ https://github.com/jonklein/Push.

