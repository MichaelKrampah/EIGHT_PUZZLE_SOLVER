package puzzleDFS;

public class Main {
    public static void main(String[] args) {
        // Define the initial state and goal state for the puzzle
        int[] initialState = {1, 2, 3, 4, 0, 5, 6, 7, 8}; // Example initial state
        int[] goalState = {0, 1, 2, 3, 4, 5, 6, 7, 8}; // Goal state

        // Create a PuzzleState object with the initial state, path cost, and goal state
        PuzzleState initialPuzzleState = new PuzzleState(initialState, 0, goalState);

        // Create an instance of the PuzzleBoard with the initial state
        PuzzleBoard puzzleBoard = new PuzzleBoard(initialPuzzleState);
    }
}