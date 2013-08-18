Tutorial 2 solution by Dimitri Klimenko (tutor).

(1) Loading into Eclipse
To view and run the solutions, create a new Eclipse project,
and add the contents of the ZIP archive to the project.

The actual class that runs the solutions is
tutorial2.RobotSolver

Just run it from Eclipse to see the solution. Also,
by editing those two classes, you can change which algorithm
is used to find the solution - also present are all of the
search algorithms mentioned in the lectures so far.

Also included is a modified version of the Assignment 1 visualiser that works
for Tutorial 2; the class to run it is
visualiser.Visualiser

(2) Manual compilation and execution
Alternatively, to compile and run the solutions without Eclipse,
download and install Apache Ant, and then run the command
    ant
from the main project folder.

Then the solution and visualiser can be run via the following commands:
    java -cp bin tutorial2.RobotSolver
    java -cp bin visualiser.Visualiser