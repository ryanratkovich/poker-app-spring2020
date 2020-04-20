public class Utilities {
	public static int comb(int n, int r) {
		if(r > n)
			return 0;
		else if(n==r)
			return 1;
		int num = fact(n);
		int denom = fact(r)*fact(n-r);
		return num/denom;
	}
	public static int fact(int n) {
		if(n <= 1)
			return 1;
		return n*fact(n-1);
	}
}