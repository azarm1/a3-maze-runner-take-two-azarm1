package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MazeGraph {

    private ArrayList<Vertex> vertices;
    private Maze maze;
    private Vertex startVertex; 
    private Vertex endVertex;

    public MazeGraph(Maze inputMaze) {
        this.vertices = new ArrayList<Vertex>();
        this.maze = inputMaze;
        createMazeGraph(maze);
    }

    public Vertex getStartVertex() {
        return startVertex;
    }

    public Vertex getEndVertex() {
        return endVertex;
    }

    public ArrayList<Vertex> getVertices() {
        return new ArrayList<>(vertices);
    }

    private Vertex addVertex(Position inputPosition, Direction inputDirection){
        Vertex newVertex = new Vertex(inputPosition, inputDirection);
        this.vertices.add(newVertex);
        return newVertex;
    }

    private void createMazeGraph(Maze maze){
        Position start = maze.getStart();
        Direction startDir = Direction.RIGHT;
        startVertex = addVertex(start, startDir);

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(startVertex);

        while(!queue.isEmpty()){
            Vertex currentVert = queue.remove();
            Position currentPos = currentVert.getPosition();
            Direction currentDir = currentVert.getDirection();

            if (currentPos.equals(maze.getEnd())){
                endVertex = currentVert;
            }

            if (maze.isValidMove(currentPos, currentDir)){

                Position newPos = currentPos.move(currentDir);
                Vertex newVertex =  addVertex(newPos, currentDir);
                Path path = new Path("F");
                currentVert.addEdge(newVertex, path);

                queue.add(newVertex);

            }

            Direction right = currentDir.turnRight();
            if (maze.isValidMove(currentPos, right)){

                Position newPos = currentPos.move(right);
                Vertex newVertex =  addVertex(newPos, right);
                Path path = new Path("RF");
                currentVert.addEdge(newVertex, path);

                queue.add(newVertex);

            }

            Direction left = currentDir.turnLeft();
            if (maze.isValidMove(currentPos, left)){

                Position newPos = currentPos.move(left);
                Vertex newVertex =  addVertex(newPos, left);
                Path path = new Path("LF");
                currentVert.addEdge(newVertex, path);

                queue.add(newVertex);

            }

        }


    }

    public void printGraph() {
        System.out.println("Maze Graph Representation:");
        for (Vertex vertex : vertices) {
            // Print vertex details
            Position pos = vertex.getPosition();
            Direction dir = vertex.getDirection();
            System.out.println("Vertex - Position: (" + pos.x() + ", " + pos.y() + "), Direction: " + dir);
    
            // Print edges for this vertex
            for (Edge edge : vertex.edges) {
                Vertex start = edge.start;
                Vertex end = edge.end;
                Path path = edge.path;
                System.out.println("  Edge from (" + start.position.x() + ", " + start.position.y() +
                        ") to (" + end.position.x() + ", " + end.position.y() +
                        ") with Path: " + path.getPathSteps());
            }
        }
    }    
    
    public class Vertex{
        private Position position;
        private ArrayList<Edge> edges;
        private Direction direction;

        public Vertex(Position inputPosition, Direction inputDirection) {
            this.position = inputPosition;
            this.direction = inputDirection;
            this.edges = new ArrayList<Edge>();
        }

        public void addEdge(Vertex endVertex, Path path) {
            this.edges.add(new Edge(this, endVertex, path));
        }

        public Position getPosition(){
            return position;
        }

        public Direction getDirection(){
            return direction;
        }

        public ArrayList<Edge> getEdges(){
            return edges;
        }

    }

    public class Edge{
        private Vertex start;
	    private Vertex end;
	    private Path path;

	    public Edge(Vertex startV, Vertex endV, Path edgePath) {
		    this.start = startV;
		    this.end = endV;
		    this.path = edgePath;
	    }

        public Vertex getEndVertex(){
            return end;
        }

        public Path getPath(){
            return path;
        }

    }
}
