import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jihoon on 4/23/2015.
 */
public class Markup {
    // TODO: May not be working correctly
    public static String print(int[][][] listOfMarkups) {
        String str = "";
        for (int i = 0; i < listOfMarkups.length; i++) {
            for (int j = 0; j < listOfMarkups[i].length; j++) {
                for (int k = 0; k < listOfMarkups[i][j].length; k++) {
                    str += " " + Arrays.toString(listOfMarkups[i][j]);
                }
            }
            str += "\n";
        }
        return str;
    }

    // Every markup is an array of the form {0, 0, 1, 0, 0, 1, 0, 0, 1}
    // This method transforms it into an ArrayList of the form {3, 6, 9}
    public static ArrayList<Integer> arrayToArrayList(int[] markup) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < markup.length; i++) {
            if (markup[i] == 1)
                list.add(i + 1); // i+1 because i is an index, not the number
        }
        return list;
    }

    public static void updateListOfMarkups(int[][] inputGrid, int[][][] listOfMarkups) {
        // TODO: Might change depending on the dimensions of the sukodu
        // The first and the second number indicate the row and the column, respectively
        // The third number indicates the number you are considering (1 if the number may be in the spot; 0 if not.)
        // Initialize all entries to 1 because you don't have any clues at first
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Populate with 1's
                Arrays.fill(listOfMarkups[i][j], 1);
            }
        }
        // For each entry in the inputGrid
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // Get the entry (Note: This is the actual number, not the index)
                int num = inputGrid[i][j];
                // If we do have a clue
                if (num != 0) {
                    // That number will not occur again in the same row
                    // So all the num-th entries in the same row -> 0
                    for (int k = 0; k < 9; k++)
                        listOfMarkups[i][k][num - 1] = 0; // num-1 because index

                    // That number will not occur again in the same column
                    // So all the num-th entries in the same column -> 0
                    for (int k = 0; k < 9; k++)
                        listOfMarkups[k][j][num - 1] = 0; // num-1 because index

                    // That number will not occur again in the same square
                    // So all the other entries in the same square -> 0
                    for (int r = 0; r < 3; r++) {
                        for (int c = 0; c < 3; c++) {
                            // The following detects which square you're working on (Note the use of integer division)
                            int rowOfSquare = i / 3;
                            int colOfSquare = j / 3;
                            listOfMarkups[rowOfSquare * 3 + r][colOfSquare * 3 + c][num - 1] = 0;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        //int[][][] abc = {{{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}, {{1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 1, 1, 1, 1, 1, 1}}};
        //System.out.print(print(abc));
    }
}
