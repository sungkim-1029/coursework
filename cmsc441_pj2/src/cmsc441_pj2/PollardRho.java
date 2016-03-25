package cmsc441_pj2;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
 
/**
 * CMSC 441
 * Project 2. RSA and Factoring Part 2: Factoring
 * It factors a modulus using Pollard rho algorithm
 * 
 * @Due Dec. 9. 2015
 * @author Sung Ho Kim
 * ----------
 * How to compile:
 * 	javac PollardRho.java
 * How to run:
 * 	java PollardRho
 * How to use it:
 * 	 Enter a number of modulus to be factored when the program asks.
 * ----------
 * Provided Moduli I factored:
 * 1. test80, 1392586057605831118799
 *     factors: 5714040173, 243713032363
 * 2. test100, 197854876186300186584002542069
 *     factors: 255924773389279, 773097787940011
 * 3. test120, 12501831755593877685146576909227993
 *     factors: 46212862790989429, 270527100044351317
 * 4. test140, 66615995630234310884017086736808008880453
 *     factors: 59356363109494972259, 1122305885004232134967
 * ---------- 
 * Fellow students' moduli I factored:
 * 1. Orange, 114824007088473458937716851207559309
 *     factors: 173781566420725337, 660737553777622357
 * 2. Kumquat, 2342703046571719605597318331863937733
 *     factors: 1315802979922879009, 1780436039679001637
 * ----------
 */
public class PollardRho    
{
	private static int CERTAINTY = 20;
	private static BigInteger ZERO = BigInteger.ZERO;
	private static BigInteger ONE = BigInteger.ONE;
	private static BigInteger TWO = new BigInteger("2");
	private static BigInteger y1;
    private static BigInteger c;
    
    /** Main function **/
	public static void main(String[] args)
	{
	    displayMenu();
	    Scanner scan = new Scanner(System.in);
	    BigInteger modulus = scan.nextBigInteger();
	    PollardRho pr = new PollardRho();
	    System.out.println("\nModulus(n) = " + modulus);
	    
	    // Creates a initial value of x1
	    y1 = makePrime(modulus.bitLength(), CERTAINTY);
	    // Creates a random constant of c
	    c  = new BigInteger(modulus.bitLength(), new Random());
	    pr.factor (modulus);
	}

	private static void displayMenu() {
		System.out.println("----------------------");
		System.out.println("Project 2. RSA and Factoring Part 2: Factoring");
		System.out.println("- Pollard Rho");
		System.out.println("- Author: Sung Ho Kim");
		System.out.println("----------------------");
		System.out.print("Please enter the modulus(n): ");
	}

	/**
	 * Prints out factors of the modulus, n
	 * @param modulus
	 */
	public void factor(BigInteger modulus) {
		if (modulus.compareTo(ONE) == 0) {
			return;
		}
		if (modulus.isProbablePrime(CERTAINTY)) {
			System.out.println(modulus);
			return;
		}
		
		BigInteger divisor = rho(modulus, y1);
		factor(divisor);
		factor(modulus.divide(divisor));
	}

	/**
     * Produces the next value of xi in the infinite sequence
     * @param x variable xi
     * @param modulus
     * @return the value of the equation (X^2 mod modulus(n) ) + c
     */
    private BigInteger functionF(BigInteger x, BigInteger modulus)    
    {
        return x.multiply(x).mod(modulus).add(c);
    }

    /**
     * Calculates and determines the divisor, d
     * @param modulus
     * @param y1 the initial value
     * @return the divisor, d
     */
    private BigInteger rho(BigInteger modulus, BigInteger y1)
    {
    	BigInteger x1 = y1;
    	BigInteger x2 = y1;
        BigInteger divisor;
        
        if (modulus.mod(TWO).compareTo(ZERO) == 0)
        	return TWO;
        
        do {
            x1 = functionF(x1, modulus).mod(modulus);
            x2 = functionF(functionF(x2, modulus).mod(modulus), modulus).mod(modulus);
            divisor = (x1.subtract(x2)).gcd(modulus);
        } while (divisor.compareTo(ONE) == 0);
        
        return divisor;
    }
 
	/**
	 * Makes a prime number. It sets its first and last bits to 1 to make
	 * a binary representation of a probable prime number. In binary, all prime numbers
	 * except 2 begin and end with 1. It reduces repetition. After both prime numbers
	 * are created, it checks whether they are prime using both trivial cases and
	 * Miller Rabin test.
	 * @param bitLength the binary length of the prime number
	 * @param certainty a probability of being prime
	 * equal to: 1-1/2^certainty
	 * @return
	 */
	private static BigInteger makePrime(int bitLength, int certainty) {
		BigInteger x = new BigInteger(bitLength, new Random());
		// Make a binary representation of a probable prime number
		// In binary, all prime numbers except 2 begin and end with 1
		// It reduces repetition
		x = x.setBit(bitLength - 1);
		x = x.setBit(0);
		while (!x.isProbablePrime(certainty)) {
			x = x.add(TWO);
		}
		return x;
	}
}