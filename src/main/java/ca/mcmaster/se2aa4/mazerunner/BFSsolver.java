package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class BFSsolver implements MazeSolver{

    @Override
    public Path solve(Maze maze) {
        MazeGraph mazeGraph = new MazeGraph(maze);
        long startTime = System.currentTimeMillis();

        Queue<MazeGraph.Vertex> queue = new LinkedList<>();
        Map<MazeGraph.Vertex, Path> paths = new HashMap<>();
        Set<MazeGraph.Vertex> visited = new HashSet<>();


        MazeGraph.Vertex startVertex = mazeGraph.getStartVertex();
        MazeGraph.Vertex endVertex = mazeGraph.getEndVertex();

        queue.add(startVertex);
        visited.add(startVertex); 
        paths.put(startVertex, new Path()); 

        while (!queue.isEmpty()) {
            MazeGraph.Vertex currentVert = queue.remove();

            if (currentVert.equals(endVertex)) {
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
                return paths.get(currentVert);
            }

            for (MazeGraph.Edge edge : currentVert.getEdges()) {
                MazeGraph.Vertex neighbor = edge.getEndVertex();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    Path currPath = paths.get(currentVert);
                    Path newPath = new Path(currPath.addMove(edge.getPath()));
                    paths.put(neighbor, newPath);

                    queue.add(neighbor);
                }
            }
        }

        return new Path();
    }
    
}
