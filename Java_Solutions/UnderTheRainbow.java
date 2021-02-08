import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UnderTheRainbow {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line = br.readLine();
		
		int n = Integer.parseInt(line);
		int[] hotels = new int[n+1];
		for(int i = 0; i < n + 1; i++) {
			line = br.readLine();
			hotels[i] = Integer.parseInt(line);
		}
		System.out.println(FindMinPenalty(hotels));
	}
	
	 private static int FindMinPenalty(int[] hotels)
     {
         int[] penalties = new int[hotels.length];

         for (int i = hotels.length - 2; i >= 0; i--)
         {
             //i is the hotel whose penalty(i) we're calculating - penalties(j) is the candidate hotel we are trying to minimize
             int min = Integer.MAX_VALUE;
             for (int j = i + 1; j < penalties.length; j++)
             {
                 int penalty_factor = 400 - (hotels[j] - hotels[i]);
                 int penalty = penalty_factor * penalty_factor;
                 if (penalty + penalties[j] < min) min = penalty + penalties[j];
             }
             if (i == 750) System.out.println();
             
             penalties[i] = min;
         }
         return penalties[0];
     }
}
