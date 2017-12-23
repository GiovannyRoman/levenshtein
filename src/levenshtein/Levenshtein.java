package levenshtein;

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

}
