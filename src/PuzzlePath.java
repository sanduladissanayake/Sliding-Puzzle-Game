import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PuzzlePath {
    private final char[][] puzzle; // puzzle array
    private final boolean[][] visitedArray; // visited points array
    private Points startingPoint; // starting point
    private Points endingPoint;   // ending point
    private final ArrayList<Points> nodeQueue = new ArrayList<>();   //nodes to be evaluated

    public PuzzlePath(char[][] puzzle){
        this.visitedArray = new boolean[puzzle.length][puzzle.length];
        this.puzzle = puzzle;
    }

    /**
     * Method to find starting point
     * find the 's' point
     */

    private void findStartingPoint() {
        for( int i = 0 ; i<puzzle.length; i++){
            for ( int j = 0; j < puzzle[i].length; j++){
                String start = "S";
                if (puzzle[i][j] == start.charAt(0)){
                    startingPoint = new Points(i,j);
                }
            }
        }
    }

    /**
     * Method to find ending point
     * find the 'F' point
     */

    private void findEndingPoint() {
        for( int i = 0 ; i<puzzle.length; i++){
            for ( int j = 0; j < puzzle[i].length; j++){
                String start = "F";
                if (puzzle[i][j] == start.charAt(0)){
                    endingPoint = new Points(i,j);
                }
            }
        }
    }

    /**
     * Method to validate the point
     */

    public boolean validatePoint(int row, int column) {  //check whether point is valid
        return row >= 0 && column >= 0
                && row < puzzle.length && column < puzzle[0].length
                && puzzle[row][column] != '0'
                && !visitedArray[row][column];
    }

    /**
     * Method to find sliding path
     */
    public void findPath() throws IOException {
        findStartingPoint();
        findEndingPoint();

        nodeQueue.add(startingPoint);    //add starting point to the queue
        visitedArray[startingPoint.getRowNumber()][startingPoint.getColumnNumber()] = true;
        Points currentPoint;
        boolean pathFound = false;

        while (!nodeQueue.isEmpty()) {      //evaluating all the nodes in queue
            currentPoint = nodeQueue.remove(0);  //remove the first node in the queue
            int rowNumberVisited = currentPoint.getRowNumber();
            int columnNumberVisited = currentPoint.getColumnNumber();

            if (puzzle[rowNumberVisited][columnNumberVisited] == 'F') { //if the row number and column number at the position F the path is found
                System.out.println("Path Found");
                displayPath(currentPoint);   //displaying the path
                pathFound = true;
                break;
            }
            shiftPoint(currentPoint, -1, 0);      //shift the node to the left
            shiftPoint(currentPoint, 1, 0);       //shift the node to the right
            shiftPoint(currentPoint, 0, 1);       //shift the node to the down
            shiftPoint(currentPoint, 0, -1);      //shift the node to the up
        }

        if (!pathFound){ //if path not found print the error message
            System.out.println("No Path Found");
        }
    }

    /**
     * Method to slide the node in the puzzle
     */
    public void shiftPoint(Points position, int x, int y) {
        int row = position.getRowNumber();
        int column = position.getColumnNumber();

        while(true) {
            row += y;
            column += x;

            if (!validatePoint(row, column)) { //check whether the point is valid or not
                break;
            }

            if (puzzle[row][column] == 'F') {
                Points nextElement = new Points(row, column);
                nextElement.setParent(position);
                nodeQueue.add(0, nextElement);    //add the node to the front of the queue
                nextElement.direction = getShiftingDirections(position,nextElement);   //get the direction
                visitedArray[row][column] = true;
                break;
            }

            int nextRow  = row + y;
            int nextColumn = column + x;
            if ((nextRow < 0 || nextColumn < 0) || (nextRow >= puzzle.length || nextColumn >= puzzle.length) || (puzzle[nextRow][nextColumn] == '0')) {  // if the next position is a wall or a rock the position before is stored
                Points nextElement = new Points(row, column);
                nextElement.setParent(position);
                nodeQueue.add(nextElement);
                nextElement.direction = getShiftingDirections(position,nextElement);
                visitedArray[row][column] = true;
                break;
            }
        }
    }

    /**
     * Method to get the direction
     */
    private String getShiftingDirections(Points node, Points neighbour) {
        String directions = "";
        if (neighbour.getColumnNumber() < node.getColumnNumber()){
            directions = "Left";
        }
        if (neighbour.getColumnNumber() > node.getColumnNumber()){
            directions = "Right";
        }
        if (neighbour.getRowNumber() > node.getRowNumber()){
            directions = "Down";
        }
        if (neighbour.getRowNumber() < node.getRowNumber()){
            directions = "Up";
        }
        return directions;
    }

    /**
     * Method to display the path
     */
    private void displayPath(Points currentPoint) throws IOException {

        ArrayList<Points> path = new ArrayList<>();
        Points currentNode = currentPoint;
        while (currentNode.getParent() != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        Collections.reverse(path);
        System.out.println("01. Start at (" + (startingPoint.getColumnNumber()+1) + "," + (startingPoint.getRowNumber()+1) + ")"); //display the starting point

        int count = 2;
        for (Points points : path) {
            if (count < 10){
                System.out.println("0" + count  + ". " + "Move " + points.direction + " To (" + (points.getColumnNumber()+1) + "," + (points.getRowNumber()+1) + ")");
            }
            else {
                System.out.println(count  + ". " + "Move " + points.direction + " To (" + (points.getColumnNumber()+1) + "," + (points.getRowNumber()+1) + ")");
            }
            count++;
        }
        System.out.println(count + ". "  + "Done!");
    }
}
