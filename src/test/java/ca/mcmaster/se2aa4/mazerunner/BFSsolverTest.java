package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BFSsolverTest {
    private Maze maze;
    private MazeGraph mazeGraph;
    private BFSsolver solver;

    @BeforeEach
    void setUp() throws Exception {
        maze = new Maze("./examples/large.maz.txt"); //change filepath to test on different mazes
        mazeGraph = new MazeGraph(maze);
        solver = new BFSsolver();
    }

    @Test
    void testSolverFindsCorrectPath() {
        Path solvedPath = solver.solve(mazeGraph);
        assertTrue(maze.validatePath(solvedPath), "The solved path should be valid according to the maze's rules.");

    }
}
