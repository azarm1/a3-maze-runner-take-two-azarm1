package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class MazeGraphTest {

    private Maze maze;
    private MazeGraph mazeGraph;

    @BeforeEach
    void setUp() throws Exception {

        maze = new Maze("./examples/large.maz.txt");
        mazeGraph = new MazeGraph(maze);
    }

    @Test
    void testGraphInitialisation() {
        assertNotNull(mazeGraph.getStartVertex(), "Start vertex should be set during graph initialization.");
        assertNotNull(mazeGraph.getEndVertex(), "End vertex should be set during graph initialization.");
        assertEquals(maze.getStart(), mazeGraph.getStartVertex().getPosition(), "Graph start vertex should match maze start position.");
        assertEquals(maze.getEnd(), mazeGraph.getEndVertex().getPosition(), "Graph end vertex should match maze end position.");
    }

@Test
void testGraphEdgesReflectValidMazeMoves() {
    for (MazeGraph.Vertex vertex : mazeGraph.getVertices()) {
        Position currentPosition = vertex.getPosition();
        Direction currentDirection = vertex.getDirection();

        for (MazeGraph.Edge edge : vertex.getEdges()) {
            MazeGraph.Vertex connectedVertex = edge.getEndVertex();
            Position endPosition = connectedVertex.getPosition();
            Path path = edge.getPath();

            Direction moveDirection = switch (path.getCanonicalForm()) {
                case "F" -> currentDirection; 
                case "R F" -> currentDirection.turnRight(); 
                case "L F" -> currentDirection.turnLeft(); 
                default -> throw new IllegalArgumentException("Unexpected path: " + path);
            };

            Position expectedPosition = currentPosition.move(moveDirection);

            assertEquals(expectedPosition, endPosition, "The edge does not lead to the expected position based on its path.");

            assertTrue(maze.isValidMove(currentPosition, moveDirection), "The move " + path.getPathSteps() + " from " + currentPosition + " in direction " + currentDirection + " is not valid in the maze.");
        }
    }
}

@Test
void testEdgeConnectionsBasedOnMazeRules() {
    List<Position> visited = new ArrayList<>();

    for (MazeGraph.Vertex vertex : mazeGraph.getVertices()) {
        Position currentPosition = vertex.getPosition();
        visited.add(currentPosition);

        for (Direction direction : Direction.values()) {
            Position newPosition = currentPosition.move(direction);

            if (maze.isWithinBounds(newPosition) && !maze.isWall(newPosition) && !visited.contains(newPosition)) {

                boolean hasEdge = vertex.getEdges().stream().anyMatch(edge -> edge.getEndVertex().getPosition().equals(newPosition));
                
                assertTrue(hasEdge, "Expected an edge to exist for a valid move from " + currentPosition + " to " + newPosition);
            }
        }
    }
}

}
