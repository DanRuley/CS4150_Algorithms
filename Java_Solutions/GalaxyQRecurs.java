import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class GalaxyQRecurs {
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

		for (int i = 0; i < star_count; i += 2) {
			token = new StringTokenizer(br.readLine());

			// i is at the last star - we know this is an odd man out case
			if (i == star_count - 1) {
				y = new int[] { Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()) };
				univ_stars.add(y);
				break;
			}

			token1 = new StringTokenizer(br.readLine());

			int[] star1 = { Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()) };
			int[] star2 = { Integer.parseInt(token1.nextToken()), Integer.parseInt(token1.nextToken()) };

			univ_stars.add(star1);
			univ_stars.add(star2);

			if (computeDistanceSquared(star1, star2) <= diam_sq) {
				sub_arr.add(star1);
			}
		}

		int[] x = findMajority(sub_arr, diam_sq);
		
		int n = -1;
		if (x == null) {
			if (y != null) 
				n = finalCheck(univ_stars, y, diam_sq);
		}
		else { 
			n = finalCheck(univ_stars, x, diam_sq);
		}
			
		if (n > 0) System.out.println(n);
		else System.out.println("NO");

		br.close();
	}

	private static int[] findMajority(ArrayList<int[]> sub_arr, long diam_sq) {

		if (sub_arr.size() == 0)
			return null;
		
		else if (sub_arr.size() == 1)
			return sub_arr.get(0);

		else {

			ArrayList<int[]> next_arr = new ArrayList<int[]>();
			// If size is even, no odd man out so iterate all the way. If size is odd we
			// have an odd man out so only iterate to
			// length - 1 & assign a value to y.
			int iterate_to = (sub_arr.size() % 2 == 0) ? sub_arr.size() : sub_arr.size() - 1;

			int[] y = (sub_arr.size() % 2 == 0) ? null : sub_arr.get(sub_arr.size() - 1);

			int[] x = null;

			for (int i = 0; i < iterate_to; i += 2) {
				if (computeDistanceSquared(sub_arr.get(i), sub_arr.get(i + 1)) <= diam_sq) {
					next_arr.add(sub_arr.get(i));
				}
			}

			x = findMajority(next_arr, diam_sq);

			if (x == null)
				if (y != null)
					return checkCandidate(sub_arr, y, diam_sq);
				else
					return null;
			else
				return checkCandidate(sub_arr, x, diam_sq);
		}
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
}
