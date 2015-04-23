import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.*;


@SuppressWarnings("serial")
public class Panel extends JPanel{
	private JButton solve, generateRegular, generateSamurai;
	private JPanel gridSpace;
	private Integer[][] grid;

	public Panel(Integer[][] grid) {
		setLayout(new BorderLayout());
		this.grid = grid;
		solve = new JButton("Solve Puzzle");
		solve.addActionListener(new solveListener());
		generateRegular = new JButton("Generate Sudoku Puzzle");
		generateRegular.addActionListener(new generateRegularListener());
		generateSamurai = new JButton("Generate Samurai Sudoku Puzzle");
		generateSamurai.addActionListener(new generateSamuraiListener());
		gridSpace = new JPanel();
		gridSpace.setLayout(new GridLayout(grid.length, grid.length));
		showPuzzle(grid);
		add(gridSpace);
		add(solve, BorderLayout.SOUTH);
		//add(generateRegular, BorderLayout.SOUTH);
		//add(generateSamurai, BorderLayout.SOUTH);
	}
	
	public void showPuzzle(Integer[][] puzzle) {
		gridSpace.removeAll();
		for (int i = 0; i < puzzle.length; i++)
			for (int j = 0; j < puzzle.length; j++)
				gridSpace.add(new JLabel(puzzle[i][j]+""));
		revalidate();
        repaint();
	}
	
	private class solveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
            // print start time
            System.out.println(LocalDateTime.now());
			Integer [][] solution = SudokuSolver.solve(grid);
			showPuzzle(solution);
			grid = solution;
            // print end time
            System.out.println(LocalDateTime.now());
            if (solution[0][0] == null) // Infeasible sudoku
                JOptionPane.showMessageDialog(null, "This sudoku is not feasible.");
            else // Feasible sudoku
                JOptionPane.showMessageDialog(null, "Done!");
		}
	}
	
	private class generateRegularListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}
	
	private class generateSamuraiListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}
	
}
