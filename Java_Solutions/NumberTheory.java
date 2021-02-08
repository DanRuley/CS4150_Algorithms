import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class NumberTheory {
	public static void main(String[] args) throws IOException {

//		long[] ans = rsa(13,17);
//		long modulus = ans[0];
//		long e = ans[1];
//		long d = ans[2];
//		System.out.println("Modulus: " + ans[0] + "\tPublic exponent: " + ans[1] + "\tPrivate exponent: " + ans[2]);
//		long eeleven = iterativeExp(11, e, modulus);
//		long esix = iterativeExp(6, e, modulus);
//		long dec_seventytwo = iterativeExp(72, d, modulus);
//		System.out.println();
		
		
		long[] ans = rsa(23,29);
		System.out.println("Modulus: " + ans[0] + "\tPublic exponent: " + ans[1] + "\tPrivate exponent: " + ans[2]);
		long dec_hundo = iterativeExp(100, 411, 667);
		System.out.println(dec_hundo);
	}
	
	static ArrayList<Integer> fermat(int N) {

		ArrayList<Integer> a = new ArrayList<Integer>();
		for (int i = 2; i < N; i++) {
			if (iterativeExp(i, N - 1, N) == 1) {
				a.add(i);
			}
		}
		return a;
	}
	
	
	/**
	 * Iterative version of the modular exponentiation algorithm. Experimentally, it
	 * does seem to run slightly faster than the recursive version.
	 */
	public static long iterativeExp(long x, long y, long N) {
		long z = 1;

		String bin_exp = Integer.toBinaryString((int) y);

		for (int i = 0; i < bin_exp.length(); i++) {
			if (bin_exp.charAt(i) == '1') {
				z = ((x % N) * ((z * z) % N)) % N;
			} else {
				z = (z * z) % N;
			}
		}
		return z;
	}
	
	public static boolean isPrime(long N) {
		if (N == 2 || N == 3 || N == 5) return true;
		int[] a = { 2, 3, 5 };
		for (int i = 0; i < a.length; i++) {
			if (iterativeExp(a[i], N - 1, N) != 1)
				return false;
		}
		return true;
	}


	/**
	 * Iterative version of the Euclid GCD algorithm. It is slightly faster than the
	 * recursive version.
	 */
	public static long iterativeEuclidGCD(long a, long b) {
		while (b > 0) {
			long a_mod_b = mod(a, b);
			a = b;
			b = a_mod_b;
		}
		return a;
	}

	public static long[] extendedEuclid(long a, long b) {
		long[] x = new long[3];
		if (b == 0)
			return new long[] { 1, 0, a };
		x = extendedEuclid(b, mod(a, b));
		return new long[] { x[1], x[0] - (a / b) * x[1], x[2] };
	}

	public static long modMultInv(long a, long N) {
		long[] x = extendedEuclid(a, N);
		if (x[2] == 1)
			return mod(x[0], N);
		else
			return -1;
	}

	

	public static long[] rsa(long p, long q) {
		long[] results = new long[3];
		results[0] = (p * q);
		long phi = (p - 1) * (q - 1);
		for (int i = 2; i < phi; i++) {
			if (iterativeEuclidGCD(i, phi) == 1) {
				results[1] = i;
				break;
			}
		}
		results[2] = modMultInv(results[1], phi);

		return results;
	}

	public static long mod(long a, long b) {
		if (a < 0)
			return ((a + b) % b) % b;
		else
			return a % b;
	}

}



/**
 * 
 * 										I/O
 * 
 */

//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//String line;
//while ((line = br.readLine()) != null) {
//	
//	StringTokenizer token = new StringTokenizer(line);
//	if (!token.hasMoreElements())
//		break;
//	
//	switch(token.nextToken()) {
//	
//	case "gcd":
//		System.out.println(iterativeEuclidGCD(Integer.parseInt(token.nextToken()),Integer.parseInt(token.nextToken())));
//		break;
//		
//	case "inverse":
//		long r = modMultInv(Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken()));
//		if (r < 0) System.out.println("none");
//		else
//			System.out.println(r);
//		break;
//		
//	case "exp":
//		System.out.println(iterativeExp(Integer.parseInt(token.nextToken()),Integer.parseInt(token.nextToken()), Integer.parseInt(token.nextToken())));
//		break;
//		
//	case "key":
//		long[] result = rsa(Integer.parseInt(token.nextToken()),Integer.parseInt(token.nextToken()));
//		System.out.println(result[0] + " " + result[1] + " " + result[2]);
//		break;
//		
//	case "isprime":
//		if (isPrime(Integer.parseInt(token.nextToken())))
//			System.out.println("yes");
//		else
//			System.out.println("no");
//		break;
//	}
//	
//}