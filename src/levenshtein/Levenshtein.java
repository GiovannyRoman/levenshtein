package levenshtein;

public class Levenshtein {

	public Levenshtein() {
	}
	
	/*
	 *Calculate the minimum number of changes needed to change one word to another
	 *(Does not include anagrams)
	 *
	 * @param firstword is the string that is going to be changed
	 * @param secondword is the end word that is desired
	 * @return a number that is the minimum number of changes
	 */
	public int minDistance(String firstword, String secondword) {
		int matrix[][] = new int[firstword.length() + 1][secondword.length() + 1];
		int fwordsize = firstword.length();
		int swordsize = secondword.length();

		for (int i = 0; i <= fwordsize; i++) {
			matrix[i][0] = i;
		}
		for (int j = 0; j <= swordsize; j++) {
			matrix[0][j] = j;
		}

		for (int i = 1; i <= fwordsize; i++) {
			char fletter = firstword.charAt(i - 1);
			for (int j = 1; j <= swordsize; j++) {
				int swap = 0;
				char sletter = secondword.charAt(j - 1);
				if (fletter == sletter) {
					swap = 0;
				} else {
					swap = 1;
				}
				matrix[i][j] = minimum(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1, matrix[i - 1][j - 1] + swap);
			}
		}
		return matrix[fwordsize][swordsize];
	}

	/*
	 * determines the minimum number from the three operations
	 * 
	 * @param insert is the cost to insert
	 * @param delete is the cost to delete
	 * @param swap is the cost to swap
	 * return the smallest cost of operations
	 */
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
}
