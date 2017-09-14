package utils;

import java.security.InvalidAlgorithmParameterException;

public class EvMath {
	public static int log2 (int a) throws InvalidAlgorithmParameterException {		
		if (a < 1) 
			throw new InvalidAlgorithmParameterException();
		int r = 0;
		while (a > 0) {
			 a = a>>>1;
			r++;
		}
		r--;
		return r;
	}
	
	public static double log2 (double a) {		
		return (Math.log(a) / Math.log(2));
	}
	
	public static boolean smallerThanFactorial (int a, int b) {
		boolean res = false;
		long fac = 1;
		for (int i = 2; i <= a && !res; i++) {
			fac *= i;
			res = b <= fac;
		}
		return res;
	}
	
	public static long factorial (int a) {
		
		long res = 1;
		for (int i = 2; i <= a; i++) {
			res *= i;
		}
		return res;
	}
	

}
