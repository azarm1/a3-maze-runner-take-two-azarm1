package ca.mcmaster.se2aa4.mazerunner;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFSsolver implements MazeSolver{

    @Override
    public Path solve(Maze maze) {
        Queue<PathState> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();
        Position start = maze.getStart();
        
        queue.add(new PathState(start, Direction.RIGHT, new Path())); // Assume starts facing RIGHT
        visited.add(start);

        while (!queue.isEmpty()) {
            PathState current = queue.poll();
            Position currentPosition = current.position;
            Path currentPath = current.path;
            Direction currentDirection = current.direction;

            if (currentPosition.equals(maze.getEnd())) {
                return currentPath;
            }

            // Try moving forward
            Position forwardPosition = currentPosition.move(currentDirection);
            if (maze.isWithinBounds(forwardPosition) && !maze.isWall(forwardPosition) && !visited.contains(forwardPosition)) {
                visited.add(forwardPosition);
                Path newPath = new Path(currentPath.getCanonicalForm());
                newPath.addStep('F');
                queue.add(new PathState(forwardPosition, currentDirection, newPath));
            }

            // Try turning right and moving
            Direction newDirectionRight = currentDirection.turnRight();
            Position rightPosition = currentPosition.move(newDirectionRight);
            if (maze.isWithinBounds(rightPosition) && !maze.isWall(rightPosition) && !visited.contains(rightPosition)) {
                visited.add(rightPosition);
                Path newPath = new Path(currentPath.getCanonicalForm());
                newPath.addStep('R');
                newPath.addStep('F');
                queue.add(new PathState(rightPosition, newDirectionRight, newPath));
            }

            // Try turning left and moving
            Direction newDirectionLeft = currentDirection.turnLeft();
            Position leftPosition = currentPosition.move(newDirectionLeft);
            if (maze.isWithinBounds(leftPosition) && !maze.isWall(leftPosition) && !visited.contains(leftPosition)) {
                visited.add(leftPosition);
                Path newPath = new Path(currentPath.getCanonicalForm());
                newPath.addStep('L');
                newPath.addStep('F');
                queue.add(new PathState(leftPosition, newDirectionLeft, newPath));
            }
        }

        return null; 
    }

    private static class PathState {
        Position position;
        Direction direction;
        Path path;

        PathState(Position position, Direction direction, Path path) {
            this.position = position;
            this.direction = direction;
            this.path = path;
        }
    }
    
}
