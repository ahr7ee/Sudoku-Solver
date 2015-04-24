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

    // Returns the square root of an integer
    // Assumes that the input is a nonzero perfect square
    public static int getSquareRoot(int n) {
        int m = 1;
        do {
            if (m * m == n)
                break;
            m++;
        } while (true);
        return m;
    }

    public static int[][] solve(int[][] grid) {
        ArrayList<int[][]> possibilities = new ArrayList<int[][]>();
        possibilities.add(grid);
        // TODO: markup code
        // int[][][] listOfMarkups = new int[grid.length][grid.length][grid.length];
        // TODO: Might change depending on the dimensions of the sukodu
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid.length; j++)
                if (!possibilities.isEmpty())
                    possibilities = generatePossibilities(possibilities, i, j, grid.length);
        // If there is no solution or the given full sudoku is invalid
        if (possibilities.isEmpty() || !isValid(possibilities.get(0)))
            return new int[grid.length][grid.length];
        else
            return possibilities.get(0);
    }

    static int[][] getSquare(int[][] grid, int i, int j) {
        int squareRoot = SudokuSolver.getSquareRoot(grid.length);
        int[][] square = new int[squareRoot][squareRoot];
        for (int x = i; x < i + squareRoot; x++)
            for (int y = j; y < j + squareRoot; y++)
                square[x - i][y - j] = grid[x][y];
        return square;
    }

    // TODO: Maybe we should use listOfMarkups for this
    static boolean checkSquare(int[][] square) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < square.length; i++)
            for (int j = 0; j < square.length; j++) {
                if (square[i][j] != 0 && list.contains(new Integer(square[i][j])))
                    return false;
                list.add(square[i][j]);
            }
        return true;
    }

    static boolean checkSquares(int[][] grid) {
        int squareRoot = SudokuSolver.getSquareRoot(grid.length);
        // TODO: Might change depending on the dimensions of the sukodu
        for (int i = 0; i < grid.length; i += squareRoot)
            for (int j = 0; j < grid.length; j += squareRoot)
                if (!checkSquare(getSquare(grid, i, j)))
                    return false;
        return true;
    }

    // TODO: Maybe we should use listOfMarkups for this
    static boolean checkHorizontals(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] != 0 && list.contains(new Integer(grid[i][j])))
                    return false;
                list.add(grid[i][j]);
            }
        }
        return true;
    }

    // TODO: Maybe we should use listOfMarkups for this
    static boolean checkVerticals(int[][] grid) {
        for (int j = 0; j < grid.length; j++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < grid.length; i++) {
                if (grid[i][j] != 0 && list.contains(new Integer(grid[i][j])))
                    return false;
                list.add(grid[i][j]);
            }
        }
        return true;
    }

    static boolean isValid(int[][] grid) {
        return checkHorizontals(grid) && checkVerticals(grid) && checkSquares(grid);
    }

    static ArrayList<int[][]> generatePossibilities(ArrayList<int[][]> current, int row, int col, int n) {
        if (current.get(0)[row][col] != 0)
            return current;
        // TODO: Might change depending on the dimensions of the sukodu
        int[][][] listOfMarkups = new int[n][n][n];
        ArrayList<int[][]> next = new ArrayList<int[][]>();
        Iterator<int[][]> iter = current.iterator();
        while (iter.hasNext()) {
            int[][] grid = iter.next();
            iter.remove();
            Markup.updateListOfMarkups(grid, listOfMarkups);
            // Don't try all numbers
            for (int k : Markup.arrayToArrayList(listOfMarkups[row][col])) {
                // Make a copy of the grid
                int[][] clone = new int[grid.length][grid.length];
                for (int i = 0; i < grid.length; i++)
                    for (int j = 0; j < grid.length; j++)
                        clone[i][j] = grid[i][j];
                // Change only one entry
                clone[row][col] = k;
                // Add to "next" only if it's valid
                if (isValid(clone))
                    next.add(clone);
            }
        }
        return next;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        Scanner keyboard = new Scanner(System.in);
        int square = 0;
        int squareRoot = 0;
        do {
            System.out.print("Enter a perfect square: ");
            square = keyboard.nextInt();
            squareRoot = Math.round((long) Math.sqrt(square));
            if (square == squareRoot * squareRoot)
                break;
        } while (keyboard.hasNextInt());
        System.out.println("Now look at the pop-up!");
        keyboard.close();
        int[][] grid = new int[square][square];
        //Scanner fileScanner = new Scanner(new File("default.txt"));
        //Scanner fileScanner = new Scanner(new File("answer.txt"));
        Scanner fileScanner = new Scanner(new File("16x16_1q.txt"));
        int i = 0;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            if (line.length() == 0)
                continue;
            String[] data = line.split("   ");
            for (int j = 0; j < squareRoot; j++) {
                String[] data2 = data[j].split(" ");
                for (int k = 0; k < squareRoot; k++) {
                    grid[i][squareRoot * j + k] = Integer.parseInt(data2[k]);
                }
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
