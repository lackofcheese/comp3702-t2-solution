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
Alternatively, to compile and run the solutions without Eclipse,
download and install Apache Ant, and then run the command
    ant
from the main project folder.

Then the solutions can be run via the following commands:
    java -cp bin tutorial1.navigation.NavigationSolver navigation.in
OR
    java -cp bin tutorial1.eight.EightPuzzleSolver eight.in
