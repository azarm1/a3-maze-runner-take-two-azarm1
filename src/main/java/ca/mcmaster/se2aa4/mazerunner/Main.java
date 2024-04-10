package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');


            long mazeLoadStart = System.currentTimeMillis();
            Maze maze = new Maze(filePath);
            long mazeLoadEnd = System.currentTimeMillis();

            System.out.println("Time spent loading maze: " + (mazeLoadEnd-mazeLoadStart));

            if (cmd.getOptionValue("p") != null) {
                logger.info("Validating path");
                Path path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else {
                String method = cmd.getOptionValue("method", "BFS");
                
                long methodStart = System.currentTimeMillis();
                Path path = solveMaze(method, maze);
                long methodEnd = System.currentTimeMillis();

                long methodTime = methodEnd - methodStart;
                System.out.println("Time spent exploring maze with method: " + methodTime);

                System.out.println(path.getFactorizedForm());

                if (cmd.getOptionValue("baseline") != null) {
                    // Compute the baseline solution path
                    String baseline = cmd.getOptionValue("baseline");
                    long baselineStart = System.currentTimeMillis();
                    solveMaze(baseline, maze);
                    long baselineEnd = System.currentTimeMillis();

                    long baselineTime = baselineEnd - baselineStart;
                    System.out.println("Time spent exploring maze with baseline: " + baselineTime);

                    double speedup = (double) baselineTime/ methodTime;
                    
                    System.out.println("Speedup: " + speedup);
                }
            }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
    }

    /**
     * Solve provided maze with specified method.
     *
     * @param method Method to solve maze with
     * @param maze Maze to solve
     * @return Maze solution path
     * @throws Exception If provided method does not exist
     */
    private static Path solveMaze(String method, Maze maze) throws Exception {
        MazeSolver solver = MazeSolverFactory.createSolver(method);

        logger.info("Computing path");
        return solver.solve(maze);
    }

    /**
     * Get options for CLI parser.
     *
     * @return CLI parser options
     */
    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));
        options.addOption(new Option("baseline", true, "Specify which method to use as baseline for speedup calculation"));

        return options;
    }
}
