Tutorial 1 solutions by Dimitri Klimenko (tutor).

(1) Loading into Eclipse
To view and run the solutions, create a new Eclipse project,
and add the contents of the ZIP archive to the project.

The actual classes that run the solutions are
tutorial1.eight.EightPuzzleSolver
and
tutorial1.navigation.NavigationSolver

Just run them from Eclipse to see the solutions. Also,
by editing those two classes, you can change which algorithm
is used to find the solution - also present are all of the
search algorithms mentioned in the lectures so far.


(2) Manual compilation and execution
Alternatively, to compile and run the solutions manually,
cd to the src folder and run the following commands in order:
    javac search/*.java
    javac search/heuristics/*.java
    javac search/algorithms/*.java
    javac tutorial1/navigation/*.java
    javac tutorial1/eight/*.java

Then the solutions can be run via the following commands:
    java tutorial1.navigation.NavigationSolver navigation.in
OR
    java tutorial1.eight.EightPuzzleSolver eight.in
