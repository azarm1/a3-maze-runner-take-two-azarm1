package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class BFSsolver implements GraphMazeSolver{

    @Override
    public Path solve(MazeGraph maze) {

        Queue<MazeGraph.Vertex> queue = new LinkedList<>();
        Map<MazeGraph.Vertex, Path> paths = new HashMap<>();
        Set<MazeGraph.Vertex> visited = new HashSet<>();


        MazeGraph.Vertex startVertex = maze.getStartVertex();
        MazeGraph.Vertex endVertex = maze.getEndVertex();

        queue.add(startVertex);
        visited.add(startVertex); 
        paths.put(startVertex, new Path()); 

        while (!queue.isEmpty()) {
            MazeGraph.Vertex currentVert = queue.remove();

            if (currentVert.equals(endVertex)) {

                return paths.get(currentVert);
            }

            for (MazeGraph.Edge edge : currentVert.getEdges()) {
                MazeGraph.Vertex neighbor = edge.getEndVertex();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    Path currPath = new Path(paths.get(currentVert));
                    currPath.addMove(edge.getPath());
                    paths.put(neighbor, currPath);

                    queue.add(neighbor);
                }
            }
        }

        return new Path();
    }
    
}
