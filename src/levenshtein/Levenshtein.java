package levenshtein;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Levenshtein {
	private int insertCost;
	private int deleteCost;
	private int substituteCost;
	private int anagramCost;
	private String fword;
	private String sword;

	public Levenshtein(int insertCost, int deleteCost, int substituteCost, int anagramCost, String fword,
			String sword) {
		super();
		this.insertCost = insertCost;
		this.deleteCost = deleteCost;
		this.substituteCost = substituteCost;
		this.anagramCost = anagramCost;
		this.fword = fword;
		this.sword = sword;
	}

	public int getInsertCost() {
		return insertCost;
	}

	public void setInsertCost(int insertCost) {
		this.insertCost = insertCost;
	}

	public int getDeleteCost() {
		return deleteCost;
	}

	public void setDeleteCost(int deleteCost) {
		this.deleteCost = deleteCost;
	}

	public int getSubstituteCost() {
		return substituteCost;
	}

	public void setSubstituteCost(int substituteCost) {
		this.substituteCost = substituteCost;
	}

	public int getAnagramCost() {
		return anagramCost;
	}

	public void setAnagramCost(int anagramCost) {
		this.anagramCost = anagramCost;
	}

	public String getFword() {
		return fword;
	}

	public void setFword(String fword) {
		this.fword = fword;
	}

	public String getSword() {
		return sword;
	}

	public void setSword(String sword) {
		this.sword = sword;
	}

	/*
	 * sorts a string in ascending order
	 * 
	 * @return a string in ascending order without modifying the original
	 */
	public String sortWord(String word) {
		String copyword = word.toLowerCase();
		char[] sortedword = copyword.toCharArray();
		Arrays.sort(sortedword);
		return new String(sortedword);
	}

	/*
	 * creates a hashmap of a dictionary using the given text
	 * 
	 * @param text the text file that contains all the words used in the
	 * dictionary
	 * 
	 * @return a hashmap of with keys that are ordered strings and values that
	 * contains those chars
	 */
	public HashMap<String, ArrayList<String>> loadDictionary(File text) {
		HashMap<String, ArrayList<String>> dictionary = new HashMap<String, ArrayList<String>>();
		Scanner scan;
		try {
			scan = new Scanner(text);
			while (scan.hasNextLine()) {
				String temp = scan.nextLine();
				// word has to be 3 letters or longer
				if (temp.length() >= 3) {
					String key = this.sortWord(temp);
					if (dictionary.containsKey(key)) {
						if (!dictionary.containsValue(temp)) {
							ArrayList<String> words = dictionary.get(key);
							words.add(temp);
							dictionary.put(temp, words);
						}
					} else {
						ArrayList<String> words = new ArrayList<String>();
						words.add(temp);
						dictionary.put(key, words);
					}
				}

			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return dictionary;
	}

	public int levenDist(ArrayList<String> path, HashMap<String, ArrayList<String>> dictionary) {
		String fword = this.fword;
		String sword = this.sword;
		int x = fword.length();
		int y = sword.length();
		int cost;

		if (x == 0) {
			return x;
		}
		if (y == 0) {
			return y;
		}

		int dist[][] = new int[x + 1][y + 1]; // grid

		for (int i = 0; i <= x; i++) {
			dist[i][0] = i;
		}
		for (int j = 0; j <= y; j++) {
			dist[0][j] = j;
		}
		
		for (int i = 1; i <= x; i++) {
			char fword_i = fword.charAt(i - 1);
			for (int j = 1; j <= y; j++) {
				char sword_j = sword.charAt(j - 1);
				if (fword_i == sword_j) {
					cost = 0;
				} else {
					cost = 1;
				}
				// need to add anagram
				dist[i][j] = this.minimum(dist[i - 1][j] + 1, dist[i][j - 1] + 1, dist[i - 1][j - 1] + cost);
			}
		}
		this.printGrid(dist);
		return dist[x][y];
	}

	private int minimum(int insert, int delete, int swap) {
		int cost = insert;
		if (delete < cost) {
			cost = delete;
		}
		if (swap < cost) {
			cost = swap;
		}

		return cost;
	}

	/*
	 * checks if a word is an anagram of another word
	 * 
	 * @param firstword is the string that anagram are being found
	 * 
	 * @param secondword is the string that is being compared if both strings
	 * match
	 * 
	 * @param dictionary is a hashmap of words
	 * 
	 * @return true if the first word is an anagram of the second and is found
	 * in the dictionary else it return false
	 */
	public boolean isAnagram(String firstword, String secondword, HashMap<String, ArrayList<String>> dictionary) {
		// check if words are in the dictionary
		if (dictionary.containsKey(this.sortWord(firstword)) && dictionary.containsKey(this.sortWord(secondword))) {
			// check if the first word is an anagram of the second
			ArrayList<String> sameletter = dictionary.get(this.sortWord(firstword));
			for (String word : sameletter) {
				if (word.equals(secondword)) {
					return true;
				}
			}
		}
		return false;
	}

	public void printGrid(int[][] grid) {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[r].length; c++)
				System.out.print(grid[r][c] + " ");
			System.out.println();
		}

	}

}
