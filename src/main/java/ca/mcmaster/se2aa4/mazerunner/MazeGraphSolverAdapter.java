package ca.mcmaster.se2aa4.mazerunner;

public class MazeGraphSolverAdapter implements MazeSolver{
    private final GraphMazeSolver graphSolver;
    
    public MazeGraphSolverAdapter(GraphMazeSolver graphSolver) {
        this.graphSolver = graphSolver;
    }
    
    @Override
    public Path solve(Maze maze) {
        MazeGraph mazeGraph = new MazeGraph(maze);
        return graphSolver.solve(mazeGraph);
    }
}
