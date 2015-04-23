import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFrame;


public class SudokuSolver {

	/*static int[][] generate2DMatrix(int n, boolean isSamurai) throws FileNotFoundException {
		int[][] c = new int[n][n];		
		return null;
	}*/
	
	public static Integer[][] solve(Integer[][] grid) {
		ArrayList<Integer[][]> possibilities = new ArrayList<Integer[][]>();
		possibilities.add(grid);
        // TODO: Might change depending on the dimensions of the sukodu
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
                if (!possibilities.isEmpty())
                    possibilities = generatePossibilities(possibilities, i, j);
        // If there is no solution or the given full sudoku is invalid
        if (possibilities.isEmpty() || !isValid(possibilities.get(0)))
            return new Integer[grid.length][grid.length];
        else
            return possibilities.get(0);
	}
	
	static Integer[][] getSquare(Integer[][] grid, int i, int j) {
        // TODO: Might change depending on the dimensions of the sukodu
		Integer[][] square = new Integer[3][3];
		for (int x = i; x < i + 3; x++)
			for (int y = j; y < j + 3; y++)
				square[x - i][y - j] = grid[x][y];
		return square;
	}
	
	static boolean checkSquare(Integer[][] square) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < square.length; i++)
			for (int j = 0; j < square.length; j++) {
				if (square[i][j] != 0 && list.contains(square[i][j]))
					return false;
				list.add(square[i][j]);
			}
		return true;
	}
	
	static boolean checkSquares(Integer[][] grid) {
        // TODO: Might change depending on the dimensions of the sukodu
		for (int i = 0; i < grid.length; i += 3)
			for (int j = 0; j < grid.length; j += 3)
				if (!checkSquare(getSquare(grid, i, j)))
					return false;
		return true;
	}
	
	static boolean checkHorizontals(Integer[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] != 0 && list.contains(grid[i][j]))
					return false;
				list.add(grid[i][j]);
			}				
		}
		return true;
	}
	
	static boolean checkVerticals(Integer[][] grid) {
		for (int j = 0; j < grid.length; j++) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < grid.length; i++) {
				if (grid[i][j] != 0 && list.contains(grid[i][j]))
					return false;
				list.add(grid[i][j]);
			}				
		}
		return true;
	}
		
	static boolean isValid(Integer[][] grid) {
        return checkHorizontals(grid) && checkVerticals(grid) && checkSquares(grid);
	}
	
	static ArrayList<Integer[][]> generatePossibilities(ArrayList<Integer[][]> current, int row, int col) {
		if (current.get(0)[row][col] != 0)
			return current;
		ArrayList<Integer[][]> next = new ArrayList<Integer[][]>();
		Iterator<Integer[][]> iter = current.iterator();
		while (iter.hasNext()) {
			Integer[][] grid = iter.next();
			iter.remove();
            // TODO: Might change depending on the dimensions of the sukodu
            // TODO: This outer for-loop have to replace with a markup later
			for (int k = 1; k <= 9; k++) {
				Integer[][] clone = new Integer[9][9];
				for (int i = 0; i < grid.length; i++)
					for (int j = 0; j < grid.length; j++)
						clone[i][j] = grid[i][j];
				clone[row][col] = k;
				if (isValid(clone))
					next.add(clone);
			}
		}
		return next;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
        // TODO: Might change depending on the dimensions of the sukodu
		// If we were to change this, we should ask the width/length of the small square (3 for a regular sudoku).
		// That way, we don't have to take square roots
        Integer[][] grid = new Integer[9][9];
		Scanner fileScanner = new Scanner(new File("default.txt"));
        //Scanner fileScanner = new Scanner(new File("answer.txt"));
        int i = 0;
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			if (line.length() == 0)
				continue;
			String[] data = line.split("   ");
            // TODO: Might change depending on the dimensions of the sukodu
            for (int j = 0; j < 3; j++) {
				grid[i][3 * j] = Character.getNumericValue(data[j].charAt(0));
				grid[i][3 * j + 1] = Character.getNumericValue(data[j].charAt(2));
				grid[i][3 * j + 2] = Character.getNumericValue(data[j].charAt(4));
			}				
			i++;
		}
		JFrame frame = new JFrame("Sudoku Solver");
		frame.setSize(500, 500);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel(grid));
		frame.setVisible(true);
		fileScanner.close();
	}

}
