Tutorial 2 solution by Dimitri Klimenko (tutor).

(1) Commands and arguments.
The runnable files and their arguments are
    tutorial2.RobotSolver [input-file] [output-file]
and
    visualiser.Visualiser [problem-file] [solution-file]

(2) Loading into Eclipse
To view and run the solutions, create a new Eclipse project,
and add the contents of the ZIP archive to the project.

Just run the classes from Eclipse to see the solution. Also,
by editing those two classes, you can change which algorithm
is used to find the solution - also present are all of the
search algorithms mentioned in the lectures so far.

(3) Manual compilation and execution
Alternatively, to compile and run the solutions without Eclipse,
download and install Apache Ant, and then run the command
    ant
from the main project folder.

Then the solver and visualiser can be run via the following commands:
    java -cp bin tutorial2.RobotSolver
    java -cp bin visualiser.Visualiser problem.txt solution.txt