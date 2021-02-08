import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GalaxyQuest {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// String[] params = br.readLine().split(" ");

		StringTokenizer token = new StringTokenizer(br.readLine());
		StringTokenizer token1;

		long diam = Long.parseLong(token.nextToken());
		long diam_sq = diam * diam;
		int star_count = Integer.parseInt(token.nextToken());

		int[] y = null;

		ArrayList<int[]> univ_stars = new ArrayList<>();
		ArrayList<int[]> sub_arr = new ArrayList<>();

		
		// We make the first star the champion before beginning
		int[] champ = null;

		for (int i = 0; i < star_count; i++) {

			//String[] coords = br.readLine().split(" ");
			token = new StringTokenizer(br.readLine());

			int[] star = { Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()) };
			univ_stars.add(star);

			// if we don't have a champ make the current star our champ
			if (champ == null)
				champ = new int[] { star[0], star[1], 1 };

			// if we do have a champ, see whether to increment or decrement its count
			else {

				// if this star is within the same galactic diameter as the champion
				if (computeDistanceSquared(champ, star) <= diam_sq) {
					champ[2]++;
				}
				
				//otherwise decrement champ count
				else {
					champ[2]--;
					// if champ's count reaches zero, it's no longer the champ
					if (champ[2] == 0) {
						champ = null;
					}
				}
			}
		}

		// need to make our final pass
		if (champ != null) {
			int galaxy_count = checkChamp(champ, univ_stars, diam_sq);
			if (galaxy_count > star_count / 2) {
				System.out.println(galaxy_count);
			}
			else
				System.out.println("NO");
		}
		else 
			System.out.println("NO");

		br.close();
	}


	public static int[] checkCandidate(ArrayList<int[]> arr, int[] candidate, long diam_sq) {
		int count = 0;
		for (int[] star : arr) {
			if (computeDistanceSquared(candidate, star) <= diam_sq)
				count++;
		}

		if (count > arr.size() / 2)
			return candidate;
		else
			return null;
	}
	
	public static int finalCheck(ArrayList<int[]> arr, int[] candidate, long diam_sq) {
		int count = 0;
		
		for (int[] star : arr) {
			if (computeDistanceSquared(candidate, star) <= diam_sq)
				count++;
		}

		if (count > arr.size() / 2)
			return count;
		else
			return -1;
	}


	private static long computeDistanceSquared(int[] star1, int[] star2) {
		long dx = star1[0] - star2[0];
		long dy = star1[1] - star2[1];
		return dx * dx + dy * dy;
	}

	public static int checkChamp(int[] champ, ArrayList<int[]> univ_stars, long diam_sq) {

		int count = 0;

		for (int i = 0; i < univ_stars.size(); i++) {
			if (computeDistanceSquared(champ, univ_stars.get(i)) <= diam_sq) {
				count++;
			}
		}

		return count;
	}

}
