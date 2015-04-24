import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class Panel extends JPanel {
    private JButton solve, resize;
    private JPanel gridSpace, buttonSpace;
    private int[][] grid;
    private int length; // Total length of grid
    private ArrayList<JTextField> textBoxes;
    
    public Panel(int[][] grid) {
        setLayout(new BorderLayout());
        this.grid = grid;
        textBoxes = new ArrayList<JTextField>();
        length = 9;
        solve = new JButton("Solve Puzzle");
        solve.addActionListener(new solveListener());
        resize = new JButton("Change Length");
        resize.addActionListener(new resizeListener());
        /*generateRegular = new JButton("Generate Sudoku Puzzle");
        generateRegular.addActionListener(new generateRegularListener());
        generateSamurai = new JButton("Generate Samurai Sudoku Puzzle");
        generateSamurai.addActionListener(new generateSamuraiListener());*/
        gridSpace = new JPanel();
        gridSpace.setLayout(new GridLayout(grid.length, grid.length));
        buttonSpace = new JPanel();
        buttonSpace.setLayout(new GridLayout(1, 2));
        buttonSpace.add(solve);
        buttonSpace.add(resize);
        showPuzzle(grid);
        add(gridSpace);
        add(buttonSpace, BorderLayout.SOUTH);
        //add(generateRegular, BorderLayout.SOUTH);
        //add(generateSamurai, BorderLayout.SOUTH);
    }

    public void showPuzzle(int[][] puzzle) {
        gridSpace.removeAll();
        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++) {
            	String text = "";
            	if (puzzle[i][j] != 0)
            		text += puzzle[i][j];
            	JTextField tf = new JTextField(text);
            	textBoxes.add(tf);
                gridSpace.add(tf);
            }
        revalidate();
        repaint();
    }

    public int getValueAt(int i, int j) {
    	if (i * length + j >= textBoxes.size())
    		return 0;
    	String str = textBoxes.get(i * length + j).getText();
    	if (str.equals(""))
    		return 0;
    	return Integer.parseInt(str);
    }
    
    public void updateGrid() {
    	grid = new int[length][length];
    	for (int i = 0; i < length; i++)
    		for (int j = 0; j < length; j++)
    			grid[i][j] = getValueAt(i, j);
    	return;
    }
    
    private class solveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // print and save the start time
            Date startTime = new Date();
            System.out.println("Started at " + startTime);
            updateGrid();
            int[][] solution = SudokuSolver.solve(grid);
            showPuzzle(solution);
            grid = solution;
            // print and save end time
            Date endTime = new Date();
            System.out.println("Ended at " + endTime);
            // Get the time difference
            System.out.println("(Took " + (endTime.getTime() - startTime.getTime()) / 1000 + " seconds.)");
            if (solution[0][0] == 0)// Infeasible sudoku (Refer to SudokuSolver.java.27)
                JOptionPane.showMessageDialog(null, "This sudoku is not feasible. (Took " + (endTime.getTime() - startTime.getTime()) / 1000 + " seconds.)");
            else // Feasible sudoku
                JOptionPane.showMessageDialog(null, "Done! (Took " + (endTime.getTime() - startTime.getTime()) / 1000 + " seconds.)");
        }
    }

    private class resizeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	length = Integer.parseInt(JOptionPane.showInputDialog("Enter new edge length for the grid"));
        	gridSpace.setLayout(new GridLayout(length, length));
        	updateGrid();
        	showPuzzle(grid);
        }
    }

    /*private class generateSamuraiListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }*/
}