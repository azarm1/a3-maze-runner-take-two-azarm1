package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BFSsolverTest {
    private Maze maze;
    private MazeGraph mazeGraph;
    private BFSsolver solver;

    @BeforeEach
    void setUp() throws Exception {
        // Assuming "./examples/large.maz.txt" is a valid maze file
        // You might want to test with simpler mazes too
        maze = new Maze("./examples/large.maz.txt");
        mazeGraph = new MazeGraph(maze);
        solver = new BFSsolver();
    }

    @Test
    void testSolverFindsPath() {
        Path path = solver.solve(mazeGraph);
        assertNotNull(path, "Solver should return a non-null path");

        // Verifying the path starts at the start vertex and ends at the end vertex
        //assertTrue(path.isValidStart(mazeGraph.getStartVertex().getPosition()), "Path should start at the maze's start position");
        //assertTrue(path.isValidEnd(mazeGraph.getEndVertex().getPosition()), "Path should end at the maze's end position");

        // Optionally, verify the correctness of the path
        assertTrue(maze.validatePath(path), "The path found by the solver must be valid according to the maze rules");
    }
}
