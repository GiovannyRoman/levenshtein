package levenshtein;

import java.io.File;
import java.util.*;

public class Client {

	public static void main(String[] args) {
		boolean validInput = false;
		int insertCost, deleteCost, swapCost, anagramCost;
		insertCost = deleteCost = swapCost = anagramCost = 0;
		String firstword, secondword;
		firstword = secondword = "";

		File dictionaryfile = new File("dictionary.txt");
		Scanner userInput = new Scanner(System.in);
		if (userInput.hasNextInt()) {
			insertCost = userInput.nextInt();

			if (userInput.hasNextInt()) {
				deleteCost = userInput.nextInt();

				if (userInput.hasNextInt()) {
					swapCost = userInput.nextInt();

					if (userInput.hasNextInt()) {
						anagramCost = userInput.nextInt();

						if (userInput.hasNextLine()) {
							//consumes the newline
							userInput.nextLine();
							
							firstword = userInput.nextLine();

							if (userInput.hasNextLine()) {
								secondword = userInput.nextLine();
								validInput = true;
							}
						}
					}
				}

			}
		}
		userInput.close();
		if (validInput == true) {
			Levenshtein distance = new Levenshtein(insertCost, deleteCost, swapCost, anagramCost, firstword,
					secondword);
			HashMap<String, ArrayList<String>> dictionary = distance.loadDictionary(dictionaryfile);
			ArrayList<String> path = new ArrayList<String>();
			int totalcost = distance.levenDist(path, dictionary);
			System.out.println("(output: "+ totalcost +")");

		} else {
			System.out.println("(output: -1)");

		}

	}

}
