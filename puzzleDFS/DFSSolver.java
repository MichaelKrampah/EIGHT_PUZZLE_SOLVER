package puzzleDFS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class DFSSolver {
    private PuzzleState initialState;
    private PuzzleState solution;
    private int nodesExpanded;
    private int maxDepth;
    private long runningTime;
    private List<PuzzleState> solutionPath;

    public DFSSolver(PuzzleState initialState) {
        this.initialState = initialState;
    }

    public Solution solvePuzzle() {
        long startTime = System.currentTimeMillis();
        Stack<PuzzleState> stack = new Stack<>();
        Set<PuzzleState> visited = new HashSet<>();
        stack.push(initialState);
        visited.add(initialState);
        nodesExpanded = 0;
        maxDepth = 0;

        while (!stack.isEmpty()) {
            PuzzleState currentState = stack.pop();
            nodesExpanded++;
            maxDepth = Math.max(maxDepth, currentState.getPathCost());

            System.out.println("Exploring state:");
            printPuzzleState(currentState);
            System.out.println();

            if (currentState.isGoalState()) {
                solution = currentState;
                solutionPath = reconstructPath(stack, visited);
                break;
            }

            List<PuzzleState> successors = currentState.getSuccessors();
            for (PuzzleState successor : successors) {
                if (!visited.contains(successor)) {
                    stack.push(successor);
                    visited.add(successor);
                }
            }
        }

        long endTime = System.currentTimeMillis();
        runningTime = endTime - startTime;
        return new Solution(solution, nodesExpanded, maxDepth, runningTime, solutionPath);
    }

    private List<PuzzleState> reconstructPath(Stack<PuzzleState> stack, Set<PuzzleState> visited) {
        List<PuzzleState> path = new ArrayList<>();
        PuzzleState currentState = solution;
        path.add(currentState);

        while (!stack.isEmpty()) {
            PuzzleState parent = stack.pop();
            if (parent.getSuccessors().contains(currentState) && visited.contains(parent)) {
                path.add(0, parent);
                currentState = parent;
            }
        }

        return path;
    }

    private void printPuzzleState(PuzzleState state) {
        int[] stateArray = state.getState();
        for (int i = 0; i < stateArray.length; i++) {
            if (i % 3 == 0) {
                System.out.print("| ");
            }
            System.out.print(stateArray[i] + " ");
            if (i % 3 == 2) {
                System.out.println("|");
            }
        }
    }

    public static class Solution {
        private PuzzleState finalState;
        private int nodesExpanded;
        private int maxDepth;
        private long runningTime;
        private List<PuzzleState> solutionPath;

        public Solution(PuzzleState finalState, int nodesExpanded, int maxDepth, long runningTime, List<PuzzleState> solutionPath) {
            this.finalState = finalState;
            this.nodesExpanded = nodesExpanded;
            this.maxDepth = maxDepth;
            this.runningTime = runningTime;
            this.solutionPath = solutionPath;
        }

        public PuzzleState getFinalState() {
            return finalState;
        }

        public int getNodesExpanded() {
            return nodesExpanded;
        }

        public int getMaxDepth() {
            return maxDepth;
        }

        public long getRunningTime() {
            return runningTime;
        }

        public List<PuzzleState> getSolutionPath() {
            return solutionPath;
        }
    }
}