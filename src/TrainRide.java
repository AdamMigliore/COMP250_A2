public class TrainRide {

	public static void main(String[] args) {
		System.out.println("Welcome to the Confusing Railroad!");

		// Constructs a train network
		TrainNetwork tNet = generateTrainNetwork();

		// tests for TrainNetwork class
		// test dancing
		tNet.printPlan();
		System.out.println("Bonjourno mister dancer");
		tNet.dance();
		tNet.printPlan();
		System.out.println("Bye bye  mister dancer");
		tNet.undance();
		tNet.printPlan();
		
		// Prints the train network plan
		tNet.printPlan();

		// Travels from Little Whinging to Hogwarts.
		tNet.travel("1.Little Whinging", "Scarlet", "5.Hogwarts", "Purple");
		System.out.println("Done!");
		tNet.printPlan();

		// Resets the network to its initial position
		System.out.println("Resetting the network");
		tNet.undance();
		tNet.printPlan();
	}

	// Calls constructors and methods to implement the network shown in the handout
	// map.
	public static TrainNetwork generateTrainNetwork() {
		// creating line 1
		TrainStation s1 = new TrainStation("1.Little Whinging");
		TrainStation s5 = new TrainStation("5.St Mungo's");

		s1.setRight(s5);
		s5.setLeft(s1);

		TrainLine l1 = new TrainLine(s1, s5, "Scarlet", true);

		/*
		 * Testing of findStation! Implicitly testing size also! Size works Size works
		 * with 0 elements
		 */
		System.out.println("My line size is: " + l1.getSize());
		System.out.println("My station was found :) " + l1.findStation("5.St Mungo's").getName());
		// System.out.println("My station was not found :(" +
		// l1.findStation("1.Whinging").getName());

		TrainStation s2 = new TrainStation("2.Wizard Hat");
		l1.addStation(s2);
		TrainStation s3 = new TrainStation("3.Hogsmeade");
		l1.addStation(s3);
		TrainStation s4 = new TrainStation("4.Diagon Alley- 1/3");
		l1.addStation(s4);

		/*
		 * Testing the getNext method from TrainLine works to go to left and right error
		 * works
		 */
		// right
		System.out.println("Get next is working s1? " + l1.getNext(s1).getName());
		System.out.println("Get next is working s2? " + l1.getNext(s2).getName());
		System.out.println("Get next is working s3? " + l1.getNext(s3).getName());
		System.out.println("Get next is working s4? " + l1.getNext(s4).getName());
		System.out.println("Get next is working s5? " + l1.getNext(s5).getName());

		// left
		System.out.println("Get next is working s2? " + l1.getNext(s2).getName());
		System.out.println("Get next is working s3? " + l1.getNext(s3).getName());
		System.out.println("Get next is working s4? " + l1.getNext(s4).getName());
		System.out.println("Get next is working s5? " + l1.getNext(s5).getName());

		// back to right
		l1.reverseDirection();
		System.out.println("Get next is working s1? " + l1.getNext(s1).getName());

		/*
		 * Testing travel one station So we can transfer accordingly if there is no
		 * transfer that must be done
		 */

		System.out.println("Lets do a transfer my guy!: " + l1.travelOneStation(s2, s3).getName());

		// end tests on line 1

		// creating line 2
		TrainStation t1 = new TrainStation("1.Gringotts");
		TrainStation t5 = new TrainStation("5.Leaky Cauldron");

		t1.setRight(t5);
		t5.setLeft(t1);

		TrainLine l2 = new TrainLine(t1, t5, "Grey", true);

		TrainStation t2 = new TrainStation("2.Diagon Alley - 2/3");
		l2.addStation(t2);
		TrainStation t3 = new TrainStation("3.Ollivanders");
		l2.addStation(t3);
		TrainStation t4 = new TrainStation("4.King's Cross - 3/5");
		l2.addStation(t4);

		s4.setConnection(l2, t2);
		t2.setConnection(l1, s4);

		// tests on line 2

		// transferring to stations
		/*
		 * The transfer works and if it was last transfer goes to a next station
		 */
		System.out.println("Lets do a transfer to another line my guy!: " + l2.travelOneStation(t2, t1).getName());

		/*
		 * The shuffle mixes the array and then reassigns everything correctly
		 */
		System.out.println("my original list: " + l2.toString());
		l2.shuffleLine();
		System.out.println("my new list: " + l2.toString());

		System.out.println("lets test out the shuffle!!! " + l2.getNext(t3).getName());

		l2.shuffleLine();
		System.out.println("shuffled " + l2.toString());

		l2.sortLine();
		System.out.println("sortedLine" + l2.toString());

		// end tests on line 2

		// creating line 3
		TrainStation u1 = new TrainStation("1.King's Cross - 4/5");
		TrainStation u5 = new TrainStation("5.Hogwarts");

		u1.setRight(u5);
		u5.setLeft(u1);

		TrainLine l3 = new TrainLine(u1, u5, "Purple", true);

		TrainStation u2 = new TrainStation("2.Ministry of Magic");
		l3.addStation(u2);
		TrainStation u3 = new TrainStation("3.Snowy Owl");
		l3.addStation(u3);
		TrainStation u4 = new TrainStation("4.Godric's Hollow");
		l3.addStation(u4);

		u1.setConnection(l2, t4);
		t4.setConnection(l3, u1);

		TrainNetwork tNet = new TrainNetwork(1);
		TrainLine[] lines = { l1, l2, l3 };
		tNet.addLines(lines);
		
		
		//more tests!
		
		System.out.println("Searching for a line " + tNet.getLineByName("Purple").toString());
		return tNet;
	}
}