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
	
	public ArrayList<String> loadFile(File textfile){
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
		return text;
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
		ArrayList<String> dictionary = words;
		return dictionary.contains(word);
	}
	/*
	 * checks for all possible anagrams of word and return empty if not matches are found
	 * 
	 * @param word is the string that are trying to get all possible anagrams from
	 * returns a list of possible anagrams
	 */
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
	/*
	 * checks if both words are anagrams of each other
	 * 
	 * @param firstword is a word that is going to be compared to another to determine if it is an anagram
	 * @param secondword is a word that is going to be compared to another to determine if it is an anagram
	 * returns true if words are anagram else false
	 */
	public boolean isAnagram(String firstword, String secondword){
		String sortedword = sortWord(firstword);
		String temp = sortWord(secondword);
		if(sortedword.equals(temp)){
			return true;
		}
		return false;
	}
	/*
	 * gets the list of all possible words that are anagrams or a change in a letter(insert,delete,swap)
	 * 
	 * @param start is the original word
	 * @param end is the word that will be the changed to 
	 */
	private ArrayList<String> possibleMatches( String start, String end){
		ArrayList<String> words = this.words;
		ArrayList<String> matches = new ArrayList<String>();
		int oldval =  end.length() - start.length();
		for(String temp : words){
			Levenshtein distance = new Levenshtein();
			int newval =  end.length() - temp.length();
			if( distance.minDistance(start, temp) == 1 && Math.abs(oldval)>= Math.abs(newval)){
				matches.add(temp);
			}
		}
		ArrayList<String> possAna = getAnagrams(start);
		if(!possAna.isEmpty()){
			for(String anagram : possAna){
				matches.add(anagram);
			}
		}
		return matches;
	}
	
	/*
	 * updates the list of possible paths and returns a list of paths and null if it is not
	 * 
	 * @param changes is the number of changes needed to reach the end word
	 * @param prev is the previous word
	 * @param end is the end word
	 * @param currtaken is the list of words already taken
	 * @param vpath is the list of possible paths
	 * returns a list of paths and null if it is not
	 */
	private ArrayList<String> validPaths(int changes,String prev,String end, ArrayList<String> currtaken,ArrayList<ArrayList<String>> vpath){
		//check if prev word matches end word
		if( prev.equals(end))
		{
			vpath.add(currtaken);
			return currtaken;
		}
		//checks if more paths
		ArrayList<String> ppaths = possibleMatches(prev,end);
		if(!ppaths.isEmpty()){
			//takes each possible paths
			for(String word : ppaths){
				ArrayList<String> temp = new ArrayList<String>();
				for(String copy: currtaken){
					temp.add(copy);
				}
				//checks if path is taken
				if(!currtaken.contains(word) && changes >0){
					temp.add(word);
					validPaths(changes-1, word,end,temp, vpath);
				}
			}
		}
		 return null;
	}

	/*
	 * gets a list of all possible paths from start to end
	 * 
	 * @param start is the starting word
	 * @param end is the ending word
	 * returns a list of all possible paths
	 */
	public ArrayList<ArrayList<String>> getAllPaths(String start, String end){
		ArrayList<ArrayList<String>> vpaths = new ArrayList<ArrayList<String>>();
		Levenshtein lev = new Levenshtein();
		int differences = lev.minDistance(start, end);
		ArrayList<String> possMatch = possibleMatches(start, end);

		for(String word: possMatch){
			ArrayList<String> initial = new ArrayList<String>();
			initial.add(start);
			initial.add(word);
			
			this.validPaths(differences,word,end,initial,vpaths);
		}
		return vpaths;
	}
	

}
