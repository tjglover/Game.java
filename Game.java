import java.util.Scanner;

public class Game {
	
	public static void main (String[] args) {

		int chips = Integer.parseInt(args[0]);
		int range = Integer.parseInt(args[1]);
		int option = Integer.parseInt(args[2]);

		int[][] cost = calculateCost(chips, range);
		int[][] guess = calculateGuess(chips, range, cost);

		if (option == 0) {
			System.out.printf("For a target number between 0 and %d, with %d chips, it takes at most %d question to identify the target in the worst case.\n", range, chips, cost[chips + 1][range + 1]);
		}
		else if (option == 1) {
			for (int i = 1; i <= chips; i++) {
				for (int j = 1; j <= range; j++) {
					System.out.printf("cost[%d][%d] = %d  ", i, j, cost[i][j]);
				}
				System.out.println();
			}
		}
		else if (option == 2) {
			for (int i = 1; i <= chips; i++) {
				for (int j = 1; j <= range; j++) {
					System.out.printf("cost[%d][%d] = %d  ", i, j, cost[i][j]);
				}
				System.out.println();
			}
			for (int i = 1; i <= chips; i++) {
				for (int j = 1; j <= range; j++) {
					System.out.printf("guess[%d][%d] = %d  ", i, j, guess[i][j]);
				}
				System.out.println();
			}
		}
		else if (option == 3) {
			guess(1, chips, range, guess, 0, range);
		}
		else {
			System.out.println("You have entered an incorrect option.");
		}

	}

	public static int[][] calculateCost(int chips, int range) {

		int cost[][] = new int [chips + 1] [range + 1];
		int temp;

		//if range is 0 or 1
		for (int i = 1; i <= chips; i++) {
			cost[i][0] = 0;
			cost[i][1] = 1;
		}

		//if only 1 chip
		for (int j = 1; j <= range; j++) {
			cost[1][j] = j;
		}

		//fill in the rest of the table 
		for (int i = 2; i <= chips; i++) {
			for (int j = 2; j <= range; j++) {
				cost[i][j] = Integer.MAX_VALUE;
				for (int x = 1; x <= j; x++) {
					if (cost[i-1][x-1] > cost[i][j-x]) {
						temp = 1 + cost[i-1][x-1];
					}
					else {
						temp = 1 + cost[i][j-x];
					}
					if (temp < cost[i][j]) {
						cost[i][j] = temp;
					}
				}
			}
		}

		return cost;
	}

	public static int[][] calculateGuess(int chips, int range, int[][] cost) {

		int guess[][] = new int [chips + 1] [range + 1];

		//if range is 0 or 1
		for (int i = 1; i <= chips; i++) {
			guess[i][0] = 0;
			guess[i][1] = 1;
		}

		// only 1 chip
		for (int j = 1; j <= range; j++) {
			guess[1][j] = 1;
		}

		//fill in the rest of the table
		for (int i = 2; i <= chips; i++) {
			for (int j = 2; j <= range; j++) {
				if (cost[i][j] == cost[i][j-1]) {
					guess[i][j] = guess[i][j-1] + 1;
				}
				else {
					guess[i][j] = 1;
				}
			}
		}

		return guess;
	}

	public static void guess(int question, int chips, int range, int[][] guess, int l, int r) {

		if (r - l <= 0) {
			System.out.printf("I nailed it! The target number is %d!! ;-)\n", l);
		}
		else {
			Scanner answer = new Scanner(System.in);
			int index = guess[chips][r - l];
			System.out.printf("Number of chips remaining: %d. Question %d: Is the target integer less than %d? (Y/N)\n", chips, question, l + index);
			String ans = answer.nextLine();
			if (ans.equals("Y") || ans.equals("y")) {
				chips = chips - 1;
				r = l + (index - 1);
			}
			else if (ans.equals("N") || ans.equals("n")) {
				l = l + index;
			}
			else {
				System.out.println("You have entered an incorrect value.");
			}
			guess(question + 1, chips, range, guess, l, r);
		}

	}
}