Tutorial 1 solutions by Dimitri Klimenko (tutor).

(1) Loading into Eclipse
To view and run the solutions, create a new Eclipse project,
and add the contents of the ZIP archive into the src folder of
the project.

The actual classes that run the solutions are
tutorial1.eight.EightPuzzleSolver
and
tutorial1.navigation.NavigationSolver

just run them from Eclipse to see the solutions, and 

Alternatively, follow (2) to compile and execute manually.


(2) Manual compilation and execution
Alternatively, to compile and run the solutions manually,
run the following commands in order:
    javac search/*.java
    javac search/heuristics/*.java
    javac search/algorithms/*.java
    javac tutorial1/navigation/*.java
    javac tutorial1/eight/*.java

Then the solutions can be run via the following commands:
    java tutorial1.navigation.NavigationSolver navigation.in
OR
    java tutorial1.eight.EightPuzzleSolver eight.in
