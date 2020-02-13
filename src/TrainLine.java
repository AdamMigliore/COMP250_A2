import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}
	
	//YOUR CODE GOES HERE
	public int getSize() {

		// YOUR CODE GOES HERE
		
		//Iterated through the stations until a null and counts the number of stations
		
		/*
		//If the line is empty we return 0
		if(leftTerminus==null && rightTerminus==null) {
			return 0;
		}
		*/
		
		TrainStation tempStation = leftTerminus;
		int length = 1;
		while(tempStation.getRight()!=null) {
			length++;
			tempStation=tempStation.getRight();
		}
		return length; // change this!
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	//PROBABLY NEED TO CHECK IF THE IMPLEMENTATION IS CORRECT
	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {

		// YOUR CODE GOES HERE
		
		//handle error
		findStation(current.getName());
		
		//if the station has a connection then
		/*
		 * transfer to it
		 * if it was our previous then transfer to next station
		 */
		
		if (current.hasConnection && !current.getTransferStation().equals(previous)) {
			
			return current.getTransferStation();
			
		}else {
			return getNext(current);
		}
	}

	//NOT SURE IF PROPERLY IMPLEMENTEDs
	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) {
		
		//if there is an error it'll throw it
		findStation(station.getName());
		
		// YOUR CODE GOES HERE
		if(goingRight) {
			
			if(station.isRightTerminal()) {
				reverseDirection();
				return station.getLeft();
			}
			
			return station.getRight();
		}else {
			
			if(station.isLeftTerminal()) {
				reverseDirection();
				return station.getRight();
			}
			
			return station.getLeft();
		}
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) {

		// YOUR CODE GOES HERE
		
		//might be better to iterate over the array for time efficiency
		/*
		TrainStation tempStation = leftTerminus;
	
		while(tempStation.getRight()!=null) {
			if(tempStation.getName().equals(name)) {
				return tempStation;
			}else {
				tempStation=tempStation.getRight();
			}
		}
		*/
		
		for(int i=0;i<lineMap.length;i++) {
			if(lineMap[i].getName().equals(name)) {
				return lineMap[i];
			}
		}
		throw new StationNotFoundException(name ); // change this!
	}
	
	//NEED MORE EFFICIENT CODE -- THE SWAP SHOULD BE ON THE STATIONS DIRECTLY
	//YOUR CODE GOES HERE
	public void sortLine() {
		// YOUR CODE GOES HERE
		boolean repeat = true;
		int lastIndex = 0;
		TrainStation tempStation = null;
		
		while(repeat) {
			repeat=false;
			for(int i=0; i < lineMap.length - 1 - lastIndex;i++) {
				
				if(lineMap[i].getName().compareTo(lineMap[i+1].getName()) > 0) {
					
					
					tempStation=lineMap[i];
					lineMap[i]=lineMap[i+1];
					lineMap[i+1]=tempStation;
					
					//System.out.println("we switched " + lineMap[i+1].getName() + " with " + lineMap[i].getName() );
					repeat = true;
				}
				
				
			}
			lastIndex++;
			
		}
		
		leftTerminus=lineMap[0];
		rightTerminus=lineMap[lineMap.length-1];
		
		//the first terminal is now the terminus left
		//the last terminal is now the terminus right
		lineMap[0].setLeftTerminal();
		lineMap[lineMap.length-1].setRightTerminal();
		
		//set initial stations for the first
		lineMap[0].setLeft(null);
		lineMap[0].setRight(lineMap[1]);
		
		//set stations for the last station
		lineMap[lineMap.length-1].setRight(null);
		lineMap[lineMap.length-1].setLeft(lineMap[lineMap.length-2]);
		
		for (int i=1;i<lineMap.length-1;i++) {
			lineMap[i].setLeft(lineMap[i-1]);
			lineMap[i].setRight(lineMap[i+1]);
		}

	}
	
	//YOUR CODE GOES HERE
	public TrainStation[] getLineArray() {
		// YOUR CODE GOES HERE
		
		/*
		//if there are no elements return null
		if (leftTerminus==null && rightTerminus==null) {
			return null;
		}
		*/
		
		//might not be good because the reference might not exist after the method is done
		TrainStation[] myLine = new TrainStation[this.getSize()];
		//we go from the left terminus and return to the last terminus on the right
		TrainStation tempStation = leftTerminus;
		//our first index
		myLine[0]=leftTerminus;
		for(int i = 1; i< myLine.length;i++) {
			myLine[i]=tempStation.getRight();
			tempStation=tempStation.getRight();
		}
		return myLine; // change this
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();

		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}
	
	//MAYBE THERE IS A MORE EFFICIENT WAY OF DOING THIS
	//YOUR CODE GOES HERE
	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		
		leftTerminus=shuffledArray[0];
		rightTerminus=shuffledArray[shuffledArray.length-1];
		
		//the first terminal is now the terminus left
		//the last terminal is now the terminus right
		shuffledArray[0].setLeftTerminal();
		shuffledArray[shuffledArray.length-1].setRightTerminal();
		
		//set initial stations for the first
		shuffledArray[0].setLeft(null);
		shuffledArray[0].setRight(shuffledArray[1]);
		
		//set stations for the last station
		shuffledArray[shuffledArray.length-1].setRight(null);
		shuffledArray[shuffledArray.length-1].setLeft(shuffledArray[shuffledArray.length-2]);
		
		for (int i=1;i<shuffledArray.length-1;i++) {
			shuffledArray[i].setLeft(shuffledArray[i-1]);
			shuffledArray[i].setRight(shuffledArray[i+1]);
		}

		// YOUR CODE GOES HERE

	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
