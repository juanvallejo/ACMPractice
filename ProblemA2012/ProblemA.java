/**
 * Inout begins with of 6 integers
 * #ofAparmnets{1,100} widthOfEachRoom{8,100} lenghtOfEachRoom{8,100} heightOfEachRoom{8,30} areaAbleToBeCoveredByCan{100,1000} mNumberOfWindowsDoors{0,100}
 * Input ends with 0 for each value
 *
 * Goal: for each case, output a single integer on its own line, indicating number of cans of paint needed to paint everything (not walls or windows)
 * TODO: iterate through each case and calculate the total paint can number for each one, right now only the first case per input is calculated
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

public class ProblemA {

	public static void readFile(String filename) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line = br.readLine();

			Case kase = new Case(line);
			int numWinDoors = kase.getNumWinDoors();

			// read the next n lines from file
			for(int i = 0; i < numWinDoors; i++) {
				kase.addWindDoor(br.readLine());
			}

			System.out.println("Total amount of paintcans -> " + kase.getPaintCanNumber());

			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	// calculates number of cans of paint needed to paint every case
	public static void calculateCans() {

	}

	public static void main(String[] args) {
		readFile("input.txt");
	}

}

/**
 * Public Case
 */
class Case {

	private ArrayList<WinDoor> winDoors;

	// number of total rooms
	private int numRooms;

	// number of total windows / doors per room
	private int numWinDoors;

	//width of each room
	private int roomWidth;
	private int roomLength;
	private int roomHeight;

	// area that the single can of paint we were given in this testcase can cover
	private int paintCanArea;

	public Case(String lineFromInput) {
		String[] inputs = lineFromInput.split(" ");
		numRooms = Integer.parseInt(inputs[0]);
		roomWidth = Integer.parseInt(inputs[1]);
		roomLength = Integer.parseInt(inputs[2]);
		roomHeight = Integer.parseInt(inputs[3]);
		paintCanArea = Integer.parseInt(inputs[4]);
		numWinDoors = Integer.parseInt(inputs[5]);

		// initialize our ArrayList
		winDoors = new ArrayList<WinDoor>();
	}

	private void parseInput(String lineFromInput) {

	}

	// takes a line of given input, creates a new WinDoor and stores it
	// in the winDoors ArrayList
	public void addWindDoor(String lineFromInput) {
		winDoors.add(new WinDoor(lineFromInput));
	}

	// returns the number of windows / doors for the current testcase
	public int getNumWinDoors() {
		return this.numWinDoors;
	}

	// returns the area of all rooms minus the area of windows / doors
	// does NOT take into account the existence of floors
	public int getTotalArea() {
		return (((this.roomWidth * this.roomLength) + (this.roomWidth * this.roomHeight * 2) + (this.roomLength * this.roomHeight * 2)) * this.numRooms) - this.getTotalAreaOfWinDoors();
	}

	public int getTotalAreaOfWinDoors() {
		if(winDoors.size() <= 0) {
			return 0;
		}

		int totalArea = 0;
		for(int i = 0; i < winDoors.size(); i++) {
			totalArea += winDoors.get(i).getArea();
		}

		return totalArea * this.numRooms;
	}

	// number of paint cans needed to paint all apartments
	public int getPaintCanNumber() {
		System.out.println(this.getTotalArea());
		return this.getTotalArea() / this.paintCanArea;
	}

}

class WinDoor {

	private int area;

	public WinDoor(String lineFromInput) {
		String[] input = lineFromInput.split(" ");
		this.area = (Integer.parseInt(input[0]) * Integer.parseInt(input[1]));
	}

	public int getArea() {
		return this.area;
	}

}