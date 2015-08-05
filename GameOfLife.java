import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;



/**
 * 
 */

/**
 * @author Nitin Maheshwari
 * Assumptions: 5x5 matrix, life board hard coded for easier execution
 * User can also input a new matrix 
 */
public class GameOfLife {
	
	private int[][] lifeBoard = {
			{0, 1, 0, 0, 0},
			{1, 0, 0, 1, 1},
			{1, 1, 0, 0, 1},
			{0, 1, 0, 0, 0},
			{1, 0, 0, 0, 1}
		};
	
	private int[][] newLifeBoard;
	
	public GameOfLife() {
		newLifeBoard = new int[5][5];
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				newLifeBoard[row][col] = lifeBoard[row][col];
			}
		}
	}
	
	
	public int neighbourCount(int currentRow, int currentCol) {
		int count = 0;
		for (int row = (currentRow-1 < 0? 0: currentRow -1); row < (currentRow+2 > 5? 5: currentRow+2); row++) {
			for (int col = (currentCol-1 < 0? 0: currentCol -1); col < (currentCol+2 > 5? 5: currentCol+2); col++) {
				if (row == currentRow && col == currentCol){}
				else
					count += lifeBoard[row][col];
			}
		} 
		return count;
	}
	
	/*
	 * RULES:
	 * 1) Any live cell with fewer than two live neighbours dies (under-population)
	 * 2) Any live cell with two or three live neighbours lives on to the next generation (survival)
	 * 3) Any live cell with more than three live neighbours dies (overcrowding)
	 * 4) Any dead cell with exactly three live neighbours becomes a live cell (reproduction)
	 */
	public void evolveGeneration(){
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				int neighbours = neighbourCount(row, col);
				if (neighbours < 2) //Rule 1
					newLifeBoard[row][col] = 0;
				else if (lifeBoard[row][col] == 1 && (neighbours > 3)) //Rule 3
					newLifeBoard[row][col] = 0;
				else if (neighbours == 3) //Rule 4
					newLifeBoard[row][col] = 1;
			}
		} 
	}
	
	public String toString() {
		String arrayString = "Before\n";
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				arrayString += lifeBoard[row][col] + " ";
			}
			arrayString += "\n";
		}
		arrayString += "\n\nAfter\n";
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				arrayString += newLifeBoard[row][col] + " ";
			}
			arrayString += "\n";
		}
		return arrayString;
	}
	


	/**
	 * Takes user input to create a new board
	 * Any thing other then Y will be considered no for the default response
	 */
	private void createNewBoard() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Do you want to use the default board (Y/N) :- ");
        try {
			String s = br.readLine();
			if (s.equalsIgnoreCase("Y"))
				return;
		} catch (IOException e) {
            System.err.println("Some exception, will procede with user input!");
		}
        System.out.println("\nEnter data for new Life Board. System will help you with the input row by row:");
		for (int row = 0; row < 5; row++) {
		       System.out.println("\nEnter data for row :" + (row+1) );
			for (int col = 0; col < 5; col++) {
				while (true){
			       try{
			    		   int i = Integer.parseInt(br.readLine());
			    		   
			    		   if (i == 0 || i == 1)
			    		   {
			    			   lifeBoard[row][col] = i;
			    			   break;
			    		   }
			    		   else {
					            System.err.println("Invalid number, try again!");			    			   
			    		   }
			        }catch(NumberFormatException | IOException nfe){
			            System.err.println("Invalid Format!");
			        }
				}
			}
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameOfLife gol = new GameOfLife();
		gol.createNewBoard();
		gol.evolveGeneration();
		System.out.println(gol.toString());

	}
}
