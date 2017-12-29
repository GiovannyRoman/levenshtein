package levenshtein;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Dictionary {
	private ArrayList<String> words;

	public Dictionary() {
	}
	
	public Dictionary(ArrayList<String> words) {
		this.words = words;
	}

	public ArrayList<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}
	
	public void loadFile(File textfile){
		ArrayList<String> text = new ArrayList<String>();
		Scanner scan;
		try{
			scan = new Scanner(textfile);
			while(scan.hasNext()){
				String temp = scan.next();
				if(temp.length() >=3){
					text.add(temp.toUpperCase());
				}
				
			}
			scan.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * sorts a string in ascending order
	 * 
	 * @return a string in ascending order without modifying the original
	 */
	public String sortWord(String word) {
		char[] sortedword = word.toCharArray();
		Arrays.sort(sortedword);
		return new String(sortedword);
	}
	
	/*
	 * checks if a specific word is in the dictionary
	 * 
	 * @param word is the string that will be checked
	 * @return true if the string is in the dictionary else returns false 
	 */
	public boolean lookupWord(String word){
		ArrayList<String> dictionary = this.words;
		return dictionary.contains(word);
	}
	
	public ArrayList<String> getAnagrams(String word){
		ArrayList<String> anagrams = new ArrayList<String>();
		String sortedword = sortWord(word);
		for(String entry: this.words){
			String temp = sortWord(entry);
			if(sortedword.equals(temp) && !word.equals(entry)){
				anagrams.add(entry);
			}
		}
		return anagrams;
	}
	
	public boolean isAnagram(String firstword, String secondword){
		String sortedword = sortWord(firstword);
		String temp = sortWord(secondword);
		if(sortedword.equals(temp)){
			return true;
		}
		return false;
	}
	
	public ArrayList<String> possibleMatches(int difletter, String target){
		ArrayList<String> words = this.words;
		ArrayList<String> matches = new ArrayList<String>();
		for(String temp :words){
			Levenshtein distance = new Levenshtein();
			if(distance.minDistance(temp, target) == difletter){
				matches.add(temp);
			}
		}
		return matches;
	}
	

}
