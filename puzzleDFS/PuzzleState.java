package puzzleDFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PuzzleState {
    private int[] state;
    private int blankIndex;
    private int pathCost;
    private int[] goalstate;
    
    public PuzzleState(int[] state, int pathCost, int[] goalState) {
        this.state = state;
        this.pathCost = pathCost;
        this.blankIndex = getBlankIndex(state);
        this.goalstate = goalState;
    }

    private int getBlankIndex(int[] state) {
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                return i;
            }
        }
        return -1; // Invalid state, no blank found
    }

    public List<PuzzleState> getSuccessors() {
        List<PuzzleState> successors = new ArrayList<>();
        int[] newState;
        int newBlankIndex;

        // Move blank tile up
        if (blankIndex >= 3) {
            newState = state.clone();
            newBlankIndex = blankIndex - 3;
            swap(newState, blankIndex, newBlankIndex);
            successors.add(new PuzzleState(newState, pathCost + 1, goalstate));
        }

        // Move blank tile down
        if (blankIndex < 6) {
            newState = state.clone();
            newBlankIndex = blankIndex + 3;
            swap(newState, blankIndex, newBlankIndex);
            successors.add(new PuzzleState(newState, pathCost + 1, goalstate));
        }

        // Move blank tile left
        if (blankIndex % 3 != 0) {
            newState = state.clone();
            newBlankIndex = blankIndex - 1;
            swap(newState, blankIndex, newBlankIndex);
            successors.add(new PuzzleState(newState, pathCost + 1, goalstate));
        }

        // Move blank tile right
        if (blankIndex % 3 != 2) {
            newState = state.clone();
            newBlankIndex = blankIndex + 1;
            swap(newState, blankIndex, newBlankIndex);
            successors.add(new PuzzleState(newState, pathCost + 1, goalstate));
        }

        return successors;
    }

    private void swap(int[] state, int i, int j) {
        int temp = state[i];
        state[i] = state[j];
        state[j] = temp;
    }

    public boolean isGoalState() {
        return Arrays.equals(state, goalstate);
    }

    public int[] getState() {
        return state;
    }

    public int getPathCost() {
        return pathCost;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PuzzleState) {
            PuzzleState other = (PuzzleState) obj;
            return Arrays.equals(this.state, other.state);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(state);
    }
}