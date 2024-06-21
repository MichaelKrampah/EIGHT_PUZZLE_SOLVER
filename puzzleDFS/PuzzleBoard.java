package puzzleDFS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PuzzleBoard extends JFrame {
    private static final long serialVersionUID = 1L;
    private PuzzleState initialState;
    private JButton[] tiles;
    private JLabel nodesExpandedLabel;
    private JLabel searchDepthLabel;
    private JLabel pathCostLabel;
    private JLabel runningTimeLabel;
    private JButton solveButton;
    private JTextField initialStateField;
    private JTextField finalStateField;

    public PuzzleBoard(PuzzleState initialState) {
        this.initialState = initialState;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("8-Puzzle Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        tiles = new JButton[9];
        Font tileFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);

        for (int i = 0; i < 9; i++) {
            int tileValue = initialState.getState()[i];
            tiles[i] = new JButton(tileValue == 0 ? "" : String.valueOf(tileValue));
            tiles[i].setFont(tileFont);
            tiles[i].setEnabled(false);
            boardPanel.add(tiles[i]);
        }

        add(boardPanel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Initial State:"));
        initialStateField = new JTextField();
        inputPanel.add(initialStateField);
        inputPanel.add(new JLabel("Final State:"));
        finalStateField = new JTextField();
        inputPanel.add(finalStateField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel(new GridLayout(5, 2));
        nodesExpandedLabel = new JLabel("Nodes Expanded: ");
        searchDepthLabel = new JLabel("Maximum Search Depth: ");
        pathCostLabel = new JLabel("Path Cost: ");
        runningTimeLabel = new JLabel("Running Time: ");
        solveButton = new JButton("Solve");
        solveButton.addActionListener(new SolveButtonListener());

        controlPanel.add(new JLabel(""));
        controlPanel.add(solveButton);
        controlPanel.add(nodesExpandedLabel);
        controlPanel.add(new JLabel());
        controlPanel.add(searchDepthLabel);
        controlPanel.add(new JLabel());
        controlPanel.add(pathCostLabel);
        controlPanel.add(new JLabel());
        controlPanel.add(runningTimeLabel);
        controlPanel.add(new JLabel());

        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateGUI(DFSSolver.Solution solution) {
        nodesExpandedLabel.setText("Nodes Expanded: " + solution.getNodesExpanded());
        searchDepthLabel.setText("Maximum Search Depth: " + solution.getMaxDepth());
        pathCostLabel.setText("Path Cost: " + solution.getFinalState().getPathCost());
        runningTimeLabel.setText("Running Time: " + solution.getRunningTime() + " ms");
    }

    private void updateTiles(int[] state) {
        for (int i = 0; i < 9; i++) {
            int tileValue = state[i];
            tiles[i].setText(tileValue == 0 ? "" : String.valueOf(tileValue));
        }
    }

    private void displaySolutionPath(List<PuzzleState> solutionPath) {
        System.out.println("Solution path:");
        for (PuzzleState state : solutionPath) {
            printPuzzleState(state);
            System.out.println();
        }
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

    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String initialStateStr = initialStateField.getText();
            String finalStateStr = finalStateField.getText();

            if (isValidState(initialStateStr) && isValidState(finalStateStr)) {
                int[] initialStateArray = parseState(initialStateStr);
                int[] finalStateArray = parseState(finalStateStr);

                PuzzleState initialState = new PuzzleState(initialStateArray, 0, finalStateArray);
                PuzzleState finalState = new PuzzleState(finalStateArray, 0, finalStateArray);

                if (finalState.isGoalState()) {
                    DFSSolver solver = new DFSSolver(initialState);
                    DFSSolver.Solution solution = solver.solvePuzzle();

                    if (solution.getFinalState() != null) {
                        updateGUI(solution);
                        updateTiles(solution.getFinalState().getState());
                        displaySolutionPath(solution.getSolutionPath());
                    } else {
                        JOptionPane.showMessageDialog(PuzzleBoard.this,
                                "Unable to find a solution for the given puzzle.",
                                "No Solution Found", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(PuzzleBoard.this,
                            "The provided final state is not the goal state.",
                            "Invalid Final State", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(PuzzleBoard.this,
                        "Invalid state format. Please enter a comma-separated list of 9 numbers from 0 to 8.",
                        "Invalid State", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean isValidState(String stateStr) {
        String[] tokens = stateStr.split(",");
        if (tokens.length != 9) {
            return false;
        }

        Set<Integer> values = new HashSet<>();
        boolean hasBlankTile = false;
        for (String token : tokens) {
            try {
                int value = Integer.parseInt(token.trim());
                if (value < 0 || value > 8) {
                    return false;
                }
                if (value == 0) {
                    if (hasBlankTile) {
                        return false; // Multiple blank tiles
                    }
                    hasBlankTile = true;
                }
                values.add(value);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return values.size() == (hasBlankTile ? 9 : 8);
    }

    private int[] parseState(String stateStr) {
        String[] tokens = stateStr.split(",");
        int[] state = new int[9];
        for (int i = 0; i < 9; i++) {
            state[i] = Integer.parseInt(tokens[i].trim());
        }
        return state;
    }
}