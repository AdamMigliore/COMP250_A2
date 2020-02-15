public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

	public TrainNetwork(int nLines) {
		this.networkLines = new TrainLine[nLines];
	}

	public void addLines(TrainLine[] lines) {
		this.networkLines = lines;
	}

	public TrainLine[] getLines() {
		return this.networkLines;
	}

	// YOUR CODE GOES HERE
	public void dance() {
		System.out.println("The tracks are moving!");
		// YOUR CODE GOES HERE
		for (int i = 0; i < networkLines.length; i++) {
			networkLines[i].shuffleLine();
		}
	}

	// YOUR CODE GOES HERE
	public void undance() {
		// YOUR CODE GOES HERE
		for (int i = 0; i < networkLines.length; i++) {
			networkLines[i].sortLine();
		}
	}

	// YOUR CODE GOES HERE
	public int travel(String startStation, String startLine, String endStation, String endLine) {

		int hoursCount = 0;
		TrainLine curLine = null; // use this variable to store the current line.
		TrainStation curStation = null; // use this variable to store the current station.

		try {
			curLine = getLineByName(startLine); // use this variable to store the current line.
			curStation = curLine.findStation(startStation); // use this variable to store the current station.
		} catch (StationNotFoundException snfe) {
			return 168;
		}

		// idk if i can add variables
		TrainStation prevStation = null;
		TrainStation tempStation = null;

		System.out.println("Departing from " + startStation);

		// YOUR CODE GOES HERE

		// condition should be if you arrived
		while (!curStation.getName().equals(endStation)
				&& !curLine.getName().equals(endLine) /* you can change this */) {

			// break
			if (hoursCount == 168) {
				System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
				return hoursCount;
			}
			
			if(hoursCount%2==0 && hoursCount!=0) {
				dance();
			}

				// changes station
				tempStation = curStation;
				// when we get a line switch it doesnt work
				//returning null
				curStation = curLine.travelOneStation(curStation, prevStation);
				curLine = curStation.getLine();
				prevStation = tempStation;

			// increments hours
			hoursCount++;

			// prints an update on your current location in the network.
			System.out.println("Traveling on line " + curLine.getName() + ":" + curLine.toString());
			System.out.println("Hour " + hoursCount + ". Current station: " + curStation.getName() + " on line "
					+ curLine.getName());
			System.out.println("=============================================");

			// break; //remove this! this break is only there so the template compiles.
			// make the tracks move

		}

		System.out.println("Arrived at destination after " + hoursCount + " hours!");
		return hoursCount;
	}

	// you can extend the method header if needed to include an exception. You
	// cannot make any other change to the header.
	public TrainLine getLineByName(String lineName) {
		// YOUR CODE GOES HERE
		for (int i = 0; i < networkLines.length; i++) {
			if (networkLines[i].getName().equals(lineName)) {
				return networkLines[i];
			}
		}

		throw new LineNotFoundException(lineName); // change this
	}

	// prints a plan of the network for you.
	public void printPlan() {
		System.out.println("CURRENT TRAIN NETWORK PLAN");
		System.out.println("----------------------------");
		for (int i = 0; i < this.networkLines.length; i++) {
			System.out.println(this.networkLines[i].getName() + ":" + this.networkLines[i].toString());
		}
		System.out.println("----------------------------");
	}
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	String name;

	public LineNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "LineNotFoundException[" + name + "]";
	}
}