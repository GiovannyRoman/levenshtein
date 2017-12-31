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
			if(allPaths.isEmpty()){
				System.out.println("(output: -1)");
			}
			else{
				// displays cost and word list
				ArrayList<Integer> info = Cost(allPaths,dictionary,insertCost, deleteCost, swapCost, anagramCost);
				int index = lowestCost(info);
				System.out.print("(" + info.get(index)+ ")(");
				ArrayList<String> route = allPaths.get(index);
				for(int i = 0; i < route.size(); i ++){
					System.out.print(route.get(i) );
					if(i !=route.size()-1){
						System.out.print( "-");
					}
				}
				System.out.println(")");
				System.out.println();
			}

		} else {
			System.out.println("(output: -1)");

		}

	}
	
	/*
	 *calculates the cost of each path and returns a list of the costs
	 *
	 * @param possibleRoutes are a list of valid paths
	 * @param dict is a list of all valid words
	 * @param insertCost is the cost of inserting a letter
	 * @param deleteCost is the cost of deleting a letter
	 * @param swapCost is the cost of swapping a letter
	 * @param anagramCost is the cost of getting an anagram a letter
	 * return a list of the cost of each path
	 */
	public static ArrayList<Integer> Cost(ArrayList<ArrayList<String>> possibleRoutes,Dictionary dict,int insertCost,int deleteCost,int swapCost, int anagramCost){
		//first number is min and second number is index
		ArrayList<Integer> weight = new ArrayList<Integer>(); 
		
		int total= 0;
		for(int i = 0; i< possibleRoutes.size();i++){
			ArrayList<String> path = possibleRoutes.get(i);
			for(int j = 0; j <path.size();j++){
				if(j == 0){
					total = 0;
				}
				else{
					// check if letters were deleted
					String prev = path.get(j-1);
					String next = path.get(j);
					if(prev.length() > next.length()){
						total += deleteCost;
					}
					//check if letters were inserted
					else if(prev.length() <next.length()){
						total+= insertCost;
					}
					// check if words are anagrams
					else if(dict.isAnagram(prev,next)){
						total += anagramCost;
					}
					//check if letters are swapped
					else if(prev.length() == next.length() && !prev.equals(next)){
						total += swapCost;
					}
				}
			}
			weight.add(total);
			total = 0;
		}
	
		return weight;
	}
	/*
	 * finds the index of the smallest number in the arraylist
	 * 
	 * @param costs is the arraylist of the cost of each different path 
	 * @return an Integer of the index with the smallest number
	 */
	public static int lowestCost(ArrayList<Integer> costs){
		int index = 0;
		if(!costs.isEmpty()){
			int min = costs.get(0);
			for(int i = 1 ; i< costs.size();i++){
				if(min> costs.get(i)){
					min = costs.get(i);
					index = i;
				}
			}
			return index;
		}
		
			return -1;
		
	}

}
