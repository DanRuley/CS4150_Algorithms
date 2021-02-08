import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.*;

/*Simple solution to the MrAnaga anagram detection problem.
 *@Date: January 9, 2020
 *@Author: Dan Ruley
 */
public class MrAnaga {

	/*
	 * Main entry point for the application
	 */
	public static void main(String[] args) {
		int reps = 100;
		String alp = "abcdefghijklmnopqrstuvwxyz";
		char[] alph = alp.toCharArray();
		Random r = new Random(314);
		int n =  2000;
		int k = 5;

		for (k = 5; k < 100000000; k *= 2) {
			String[] dict = new String[n];
			for (int j = 0; j < n; j++) {
				
				String w = "";
				for (int i = 0; i < k; i++) {
					w = w + alph[r.nextInt(26)];
				}
				dict[j] = w;
			}


			double start = System.currentTimeMillis();
			while(System.currentTimeMillis() < start+1000) {
				numGrams(n,k,dict);
			}
			start = System.currentTimeMillis();
			
			for(int i = 0; i < reps; i ++) {
				numGrams(n,k,dict);
			}
			double mid = System.currentTimeMillis();
			for (int i = 0; i < reps; i++) {
				
			}
			double end = System.currentTimeMillis();
			System.out.println("Average time with n = " + n + ", k = " + k + ": " + ((mid - start - (end - mid))/100));
			
		}

	}

	public static int numGrams(int n, int k, String[] words) {

		HashSet<String> accepted = new HashSet<>();
		HashSet<String> rejected = new HashSet<>();

		for (int i = 0; i < n; i++) {

			// Sort the word, add it to accepted if it is not in either set. Otherwise,
			// remove from accepted and add to rejected.
			char[] c = words[i].toCharArray();
			Arrays.sort(c);
			String word = new String(c);

			if (!rejected.contains(word) && accepted.add(word))
				continue;
			else {
				accepted.remove(word);
				rejected.add(word);
			}
		}

		return accepted.size();
	}

}

//Scanner scn = new Scanner(System.in);
//HashSet<String> accepted = new HashSet<>();
//HashSet<String> rejected = new HashSet<>();
//
//String[] params = scn.nextLine().split(" ");
//int n = Integer.parseInt(params[0]);
//int k = Integer.parseInt(params[1]);
//
//for(int i = 0; i < n; i++) {
//
//	// Sort the word, add it to accepted if it is not in either set. Otherwise,
//	// remove from accepted and add to rejected.
//	char[] c = scn.nextLine().toCharArray();
//	Arrays.sort(c);
//	String word = new String(c);
//
//	if (!rejected.contains(word) && accepted.add(word))
//		continue;
//	else {
//		accepted.remove(word);
//		rejected.add(word);
//	}
//}
//
//System.out.println(accepted.size());
//scn.close();
