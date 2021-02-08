import java.util.Random;

/**
 * Suppose you have two arrays of integers, A and B. They are both arranged in
 * ascending order. A has length n, B has length m, and n + m > 0. We would like
 * an O(log n + log m) divide & conquer algorithm that can find the element that
 * would be stored at index k if the arrays were merged into a single sorted
 * array.
 * 
 * @author Dan
 *
 */
public class SelectPS3_2 {

	public static void main(String[] args) {
		int[] a = { 1,2,3 };
		int[] b = { 1,2,4};
//		A = [2, 5, 7, 10, 22]
//		B = [7, 8, 9, 13, 14]
		int[] a1 = {2,5,7,10,22};
		int[] a2 = {7,8,9,13,14};
		
		int[] b1 = {1,2,3,4};
		int[] b2 = {4,5,6};
//		for (int i = 0; i < 10; i++) {
//			System.out.println(select(a1, a2, i));
//		}

		for (int i = 0; i < 10; i++) {
			//System.out.println(select(b1,b2,i));
			System.out.println(select(a1,a2, i));
		}
	}

	public static int select(int[] A, int[] B, int k) {
		Random r = new Random();
		return select(A, 0, A.length - 1, B, 0, B.length - 1, k);
	}

	private static int select(int[] A, int loA, int hiA, int[] B, int loB, int hiB, int k) {


		if (k == 3)
			System.out.println();
		// A[loA...hiA] is empty
		if (hiA < loA)
			return B[k - loA];

		// B[loB...hiB] is empty
		if (hiB < loB)
			return A[k - loB];

		// Get the midpoints of A[loA...hiA] and B[loB...hiB]
		int i = (loA + hiA) / 2;
		int j = (loB + hiB) / 2;

		// Which of the four cases apply?
		if (A[i] > B[j]) {

			//exclude right half of A
			if (k <= i + j) {
				return select(A,  loA, i - 1, B, j, hiB, k);
			}
			
			else {
				//exclude left half of A
				return select(A, i, hiA, B, j+1, hiB, k);
			}
		}

		else {
			// exclude right half of B
			if (k <= i + j) {
				return select(A, i, hiA, B, loB, j - 1, k);

				//exclude B to the left of j
			} else {
				return select(A, i+1, hiA, B, j, hiB, k);
			}
		}
	}
	
}
