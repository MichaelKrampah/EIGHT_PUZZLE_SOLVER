# 8-Puzzle Solver README

## Overview

This project implements an 8-puzzle solver using the Depth-First Search (DFS) algorithm. The application includes a graphical user interface (GUI) to visualize the puzzle and allows users to input initial and goal states for solving.

## Packages

### puzzleDFS

This package contains the following classes:

1. `DFSSolver`
2. `Main`
3. `PuzzleBoard`
4. `PuzzleState`

### Class Details

#### DFSSolver

This class implements the DFS algorithm to solve the 8-puzzle.

- **Fields:**
  - `initialState`: The initial state of the puzzle.
  - `solution`: The solution state of the puzzle.
  - `nodesExpanded`: The number of nodes expanded during the search.
  - `maxDepth`: The maximum depth reached during the search.
  - `runningTime`: The time taken to find the solution.
  - `solutionPath`: The path from the initial state to the solution.

- **Methods:**
  - `DFSSolver(PuzzleState initialState)`: Constructor to initialize the solver with the initial state.
  - `Solution solvePuzzle()`: Solves the puzzle and returns the solution details.
  - `List<PuzzleState> reconstructPath(Stack<PuzzleState> stack, Set<PuzzleState> visited)`: Reconstructs the path from the initial state to the solution.
  - `void printPuzzleState(PuzzleState state)`: Prints the puzzle state in a readable format.

- **Inner Class: Solution**
  - `Solution(PuzzleState finalState, int nodesExpanded, int maxDepth, long runningTime, List<PuzzleState> solutionPath)`: Constructor to initialize the solution details.
  - Getter methods to retrieve solution details.

#### Main

This class contains the `main` method to run the application.

- **main(String[] args)**: Initializes the initial state and goal state of the puzzle and creates an instance of `PuzzleBoard`.

#### PuzzleBoard

This class implements the GUI for the 8-puzzle solver.

- **Fields:**
  - GUI components such as `JButton[] tiles`, `JLabel` labels for nodes expanded, search depth, path cost, and running time.
  - `initialStateField`, `finalStateField`: Input fields for the initial and final state of the puzzle.
  - `solveButton`: Button to trigger the solving process.

- **Methods:**
  - `PuzzleBoard(PuzzleState initialState)`: Constructor to initialize the GUI with the initial state.
  - `void initializeGUI()`: Initializes and sets up the GUI components.
  - `void updateGUI(DFSSolver.Solution solution)`: Updates the GUI with the solution details.
  - `void updateTiles(int[] state)`: Updates the tiles on the board with the current state.
  - `void displaySolutionPath(List<PuzzleState> solutionPath)`: Displays the solution path in the console.
  - `void printPuzzleState(PuzzleState state)`: Prints the puzzle state in the console.
  - `boolean isValidState(String stateStr)`: Validates the input state format.
  - `int[] parseState(String stateStr)`: Parses the input state string into an integer array.

- **Inner Class: SolveButtonListener**
  - Handles the action event when the solve button is clicked, validating input states, solving the puzzle, and updating the GUI.

#### PuzzleState

This class represents a state of the 8-puzzle.

- **Fields:**
  - `state`: The current state of the puzzle as an integer array.
  - `blankIndex`: The index of the blank tile (0) in the state array.
  - `pathCost`: The cost to reach the current state.
  - `goalstate`: The goal state of the puzzle.

- **Methods:**
  - `PuzzleState(int[] state, int pathCost, int[] goalState)`: Constructor to initialize the puzzle state.
  - `List<PuzzleState> getSuccessors()`: Generates and returns the successor states.
  - `boolean isGoalState()`: Checks if the current state is the goal state.
  - `int[] getState()`: Returns the current state array.
  - `int getPathCost()`: Returns the path cost to reach the current state.
  - `boolean equals(Object obj)`: Checks equality of two puzzle states.
  - `int hashCode()`: Returns the hash code of the state.

## Running the Application

1. Compile the Java files.
2. Run the `Main` class to start the application.
3. Enter the initial and goal states in the provided fields in the GUI.
4. Click the "Solve" button to solve the puzzle.
5. The GUI will display the solution details and the final state of the puzzle.

## Dependencies

- Java Development Kit (JDK) 8 or higher.
- Swing library for GUI components (included in JDK).

## Notes

- Ensure the initial and goal states are valid 8-puzzle configurations (a comma-separated list of 9 numbers from 0 to 8, with no duplicates).
- The application will display an error message if the input states are invalid.

## License

This project is licensed under the MIT License.
