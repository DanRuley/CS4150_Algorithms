import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BankQueue {

	public static void main(String[] args) throws IOException {
		HashMap<Integer, PriorityQueue<Integer>> cust = new HashMap<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line = br.readLine();

		StringTokenizer token = new StringTokenizer(line);

		int N = Integer.parseInt(token.nextToken());
		int T = Integer.parseInt(token.nextToken());
		int max_time = 0;

		for (int i = 0; i < N; i++) {

			token = new StringTokenizer(br.readLine());
			int deposit = Integer.parseInt(token.nextToken());
			int time = Integer.parseInt(token.nextToken());
			if (time >= max_time)
				max_time = time + 1;

			if (!cust.containsKey(time)) {
				cust.put(time, new PriorityQueue<Integer>((Integer i1, Integer i2) -> (i2 - i1)));
				cust.get(time).add(deposit);
			} else
				cust.get(time).add(deposit);
		}
		System.out.println(findMax(max_time, cust));
	}

	
	public static int findMax(int max_time, HashMap<Integer, PriorityQueue<Integer>> custs) {
		
		int dep = 0;
		
		for (int i = max_time - 1; i >= 0; i--) {
			int[] max = { -1, -1 };
			for (int j = i; j < max_time; j++) {
				if (custs.containsKey(j) && custs.get(j).peek() != null && custs.get(j).peek() > max[1]) {
					max[0] = j;
					max[1] = custs.get(j).peek();
				}
			}
			
			if (max[0] != -1) {
				dep += max[1];
				custs.get(max[0]).poll();
			}
		}
		return dep;
	}
}