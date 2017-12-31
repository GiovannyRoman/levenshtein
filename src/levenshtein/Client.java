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
		Dictionary dictionary = new Dictionary();
		dictionary.setWords(dictionary.loadFile(dictionaryfile));	
		
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
							// consumes the newline
							userInput.nextLine();

							firstword = userInput.nextLine().toUpperCase();

							if (userInput.hasNextLine()) {
								secondword = userInput.nextLine().toUpperCase();
								
								// check if costs are not negative, word has to be 3 letters or longer, and words are valid
								if (insertCost > 0 && deleteCost > 0 && anagramCost > 0 && swapCost > 0 && firstword.length() >= 3 && secondword.length() >= 3 
										&& dictionary.lookupWord(firstword.toUpperCase()) && dictionary.lookupWord(secondword.toUpperCase())) {
									validInput = true;
								}
							}
						}
					}
				}

			}
		}
		userInput.close();
		if (validInput == true) {
			ArrayList<ArrayList<String>> allPaths = dictionary.getAllPaths(firstword, secondword);
			int[] info = lowestCost(allPaths,dictionary,insertCost, deleteCost, swapCost, anagramCost);
			System.out.print("(" +info[0] + ")(");
			ArrayList<String> sequence = allPaths.get(info[1]);
			for(int i =0; i < sequence.size();i++){
				System.out.print(sequence.get(i));
				if(i != sequence.size()-1){
					System.out.print("-");
				}
			}
			System.out.println(")\n");

		} else {
			System.out.println("(output: -1)");

		}

	}
	
	public static int[] lowestCost(ArrayList<ArrayList<String>> possibleRoutes,Dictionary dict,int insertCost,int deleteCost,int swapCost, int anagramCost){
		//first number is min and second number is index
		int[] set = {-1,-1};
		
		int total= 0;
		for(int i = 0; i< possibleRoutes.size();i++){
			ArrayList<String> path = possibleRoutes.get(i);
			for(int j = 0; j <path.size();j++){
				if(j == 0){
					total = 0;
				}
				else{
					String prev = path.get(j-1);
					String next = path.get(j);
					if(prev.length() > next.length()){
						total += deleteCost;
					}
					else if(prev.length() <next.length()){
						total+= insertCost;
					}
					else if(dict.isAnagram(prev,next)){
						total += anagramCost;
					}
					else if(prev.length() == next.length() && !prev.equals(next)){
						total += swapCost;
					}
				}
				if(set[0] > total &&set[0]<0){
					set[0] = total;
					set[1] = i;
				}else{
					set[0] = total;
					set[1] = i;
				}
			}
			total = 0;
		}
	
		return set;
	}

}
