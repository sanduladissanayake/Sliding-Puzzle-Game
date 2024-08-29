/**
 * Author : D.S.Y. Dissanayake
 * IIT ID: 20221170
 * UOW ID :w1954018
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<String> puzzleRows = new ArrayList<>();
    static int noOfRows = 0;
    static char[][] puzzle;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        System.out.println("Enter File Name");
        String fileName = input.next();
        File f1 = new File("Benchmarks/" + fileName);
        Scanner read = new Scanner(f1);
        while (read.hasNextLine()) {
            noOfRows++;
            puzzleRows.add(read.nextLine());
        }
        createPuzzle();

        if(validateFile()){

            System.out.println("-----------------------------------------------------------");
            PuzzlePath puzzlePath = new PuzzlePath(puzzle);
            puzzlePath.findPath();
            System.out.println("-----------------------------------------------------------");

            System.exit(0);

        }
        else{
            System.out.println("File not valid");
        }
    }

    /**
     * Method to create the puzzle
     */

    public static void createPuzzle(){
        puzzle = new char[noOfRows][noOfRows];  //puzzle creation
        for( int i =0; i<puzzleRows.size(); i++){
            puzzle[i] = puzzleRows.get(i).toCharArray();
        }
    }

    /**
     * method to check whether file has a starting point and ending point
     * check whether file has 'S' and 'F'
     */


    private static boolean validateFile() {
        boolean hasStartingPoint = false;
        boolean hasEndingPoint = false;
        for (char[] chars : puzzle) {
            for (char aChar : chars) {
                if (aChar == 'S') {
                    hasStartingPoint = true;
                }
                if (aChar == 'F') {
                    hasEndingPoint = true;
                }
            }
        }
        return hasStartingPoint && hasEndingPoint;
    }

}