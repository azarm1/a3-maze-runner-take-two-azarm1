package ca.mcmaster.se2aa4.mazerunner;

public class MazeSolverFactory {
    
    public static MazeSolver createSolver(String method) throws Exception {
        switch (method.toLowerCase()) {
            case "BFS":
                return new MazeGraphSolverAdapter(new BFSsolver());
            case "righthand":
                return new RightHandSolver();
            case "tremaux":
                return new TremauxSolver();
            default:
                throw new Exception("Maze solving method '" + method + "' not supported.");
        }
    }
}
