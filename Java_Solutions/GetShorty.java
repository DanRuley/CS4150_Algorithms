import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class GetShorty {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line;

		while ((line = br.readLine()) != null) {
			
			HashMap<Integer, HashSet<Tuple>> verts = new HashMap<>();
			HashMap<Integer, Double> dist = new HashMap<Integer, Double>();
			HashSet<Integer> seen = new HashSet<Integer>();
			StringTokenizer params = new StringTokenizer(line);
			
			int intersections = Integer.parseInt(params.nextToken());
			int dest = intersections - 1;
			int corridors = Integer.parseInt(params.nextToken());

			if (intersections == 0 && corridors == 0)
				return; // Ignore last 0,0 test case.

			for (int i = 0; i < corridors; i++) {

				StringTokenizer edge = new StringTokenizer(br.readLine());

				int v1 = Integer.parseInt(edge.nextToken());
				int v2 = Integer.parseInt(edge.nextToken());

				if (v1 == v2)
					continue; // Ignore self-edges (not sure if they exist but hey).

				double f = Double.parseDouble(edge.nextToken());

				if (!verts.containsKey(v1)) {
					verts.put(v1, new HashSet<Tuple>());
					dist.put(v1, -1.0);
				}

				if (!verts.containsKey(v2)) {
					verts.put(v2, new HashSet<Tuple>());
					dist.put(v2, -1.0);
				}

				verts.get(v1).add(new Tuple(v2, f));
				verts.get(v2).add(new Tuple(v1, f));
			}

			djikstrasAlg(verts, dist, seen);
		}
	}
	
	public static void djikstrasAlg(HashMap<Integer, HashSet<Tuple>> verts, HashMap<Integer, Double> dist, HashSet<Integer> seen ) {
		dist.put(0, 1.0);

		PriorityQueue<Tuple> pq = new PriorityQueue<>(new TupleComp());
		pq.add(new Tuple(0, 1.0));

		while (!pq.isEmpty()) {

			Tuple t = pq.remove();

			if (seen.contains(t.key))
				continue;
			else
				seen.add(t.key);

			for (Tuple e : verts.get(t.key)) {

				if (dist.get(e.key) < dist.get(t.key) * e.factor) {
					dist.put(e.key, dist.get(t.key) * e.factor);
					pq.add(new Tuple(e.key, dist.get(e.key)));
				}
			}
		}

		String path = String.format("%.4f", dist.get(dist.size() - 1 ));
		System.out.println(path);
	}
}


/**
 * Simple class that just represents a K,V pair (used to store the destinations
 * of edges in the vertex hash map).
 */
class Tuple {

	int key;
	double factor;

	public Tuple(int k, double f) {
		key = k;
		factor = f;
	}

	@Override
	public int hashCode() {
		return key;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Tuple)
			return this.key == ((Tuple) o).key;
		else
			return false;
	}

	@Override
	public String toString() {
		return "{Vertex: " + key + " Factor: " + factor + "}";
	}
}

/**
 * Reversed comparator so that the PQ prioritizes higher factors for Djikstra's
 * Algorithm.
 */
class TupleComp implements Comparator<Tuple> {
	@Override
	public int compare(Tuple o1, Tuple o2) {
		if (o1.factor > o2.factor)
			return -1;
		else if (o1.factor < o2.factor)
			return 1;
		else
			return 0;
	}

}
