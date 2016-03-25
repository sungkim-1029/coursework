package cmsc441_pj2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 * CMSC 441
 * Project 2. RSA and Factoring Part 1a: RSA Key Generation
 * 
 * @Due Nov. 25. 2015
 * @author Sung Ho Kim
 * ----------
 * How to compile:
 * 	javac RSAKeyGenerator.java
 * How to run:
 * 	java RSAKeyGenerator
 * How to use it:
 * 	 First of all, create a pair of keys. After it is made, follow the menu.
 * 	 If you want to view keys created, choose 4. View Keys. 
 *   To sign a message, select the number 5 and choose #6 to verify the message. 
 *   You can save keys and the result in a file. 
 * ----------
 * Multiplication algorithm: 
 * 	In Java, multiplication algorithm used by BigInteger uses several methods. It
 *  switches switches between the naive algorithm, the Toom-Cook
 *  algorithm, and Karatsuba depending on the size of the input to get
 *  excellent performance. 
 * Modular exponentiation algorithm:
 * 	It uses trivial case first. Then, if modulus is even, it tears it into two
 *  parts: an odd part and power of two part. After it exponentiate mod
 *  m1 and m2, it uses Chinese Remainder Theorem to combine them. In case
 *  of the odd modulus, it keeps a running product of n^(high-order bits
 *  of exp). Then, it keeps appending exponent bits to it. The repetition
 *  goes down the exponent and squares the result buffer as it goes.
 *	---------- 
 *	Original message: I deserve a B
 *  Message signed with a private key:
 * 		14100569610298264069746680641616809088489684353350531205813608123130500171153616795142349365645550099423348160308715893137895955250466394170752293483523535283265577439892643460653335484469819813536885515731046155156410605896137713594127895937580637806558746885636797592875024452898797544220788401123991908970544060773584959222681152311467755675514482717072316588968997442445578363633582668222393155454499994941225563792981211376519668220081103561444131066961806652704291199721770761828217619511508368770794986731680905568163735441952091433135060328253945147960807714684129038165202357643267277846549638267621481762873
 *  Public key:
 *      10786407838919138494632936214302521776774973007266284694861613473473820628152313734928117890051786067504564466623294046714867474873203018661555020258545008571347596010035237744277925975620766405921960534931949169420075987764274928595088558395361010428254924942476696845744560595661798180319249315011200317427599485698719659442351736119819811229962966612232407887015796690545326561733648336265056170772470983607773836151249682323668558550751412003251227135548390360373727628521038899019090527433370800196475269615677989944006554390253222907963158627628806711161005615544907988529466108555022943972038717119747898761173
 * 
 */
public class RSAKeyGenerator {

	private static BigInteger ZERO = BigInteger.ZERO;
	private static BigInteger ONE = BigInteger.ONE;
	private static BigInteger TWO = new BigInteger("2");
	private static int CERTAINTY = 20;
	private static String KEY_FILE_NAME = "keys.config";
	private static String SIGNED_PROPERTY_FILE_NAME = "result.txt";
	private static int QUIT = 8;

	private static int keySize = 0;
	private static BigInteger e = null;
	private static BigInteger d = null;
	private static BigInteger n = null;
	private static Scanner input = null;
	private static String originalMsg = null;
	private static BigInteger encryptedMsg = null;

	private static void displayMenu() {
		System.out.println("\t** Welcome **");
		System.out.println("\t\tAuthor: Sung Ho Kim");
		System.out.println("\t----------------------");
		System.out.println("\t1. Generate keys");
		System.out.println("\t2. Load keys");
		System.out.println("\t3. Save keys");
		System.out.println("\t4. View keys");
		System.out.println("\t5. Sign message");
		System.out.println("\t6. Verify message");
		System.out.println("\t7. Save the signed message and its properties");
		System.out.println("\t8. Quit");
		System.out.println("\t----------------------");

		System.out.print("Please enter the number: ");
	}
	
	public static void main(String[] arg) {
		input = new Scanner(System.in);
		int button;
		do {
			displayMenu();
			System.out.println("test: " + isProbablePrime(new BigInteger("11"), 20));
			button = input.nextInt();
			switch (button) {
			case 1: {
				// 1: Generate keys
				System.out.println("Key size for two prime numbers? (e.g 1024)");
				keySize = input.nextInt();
				System.out.println("Generating keys...");
				generateKeys(keySize);
			}
				break;
			case 2: {
				// 2: Load keys
				System.out.println("Load Keys...");
				loadKeys();
			}
				break;
			case 3: {
				// 3: Save keys
				System.out.println("Save Keys...");
				saveKeys(e, d, n);
			}
				break;

			case 4: {
				// 4: View keys
				System.out.println("View Keys...");
				viewKeys();
			}
				break;

			case 5: {
				// 5: Sign message
				System.out.println("Sign a message with private key...");
				signMsg();
			}
				break;

			case 6: {
				// 6: Verify message
				System.out.println("Verify a sign with a public key...");
				verifyMsg();

			}
				break;

			case 7: {
				// 7: Save the signed message and its properties
				System.out.println("Save the result and etc...");
				saveResult();
			}
				break;

			case 8: {
				// 8: Quit
				System.out.println("Bye!!");
			}
				break;

			default:
				System.out.println("case #" + button + " does not exist" + ", please type a better number.");
				break;
			}
		} while (button != QUIT);
		System.exit(0);
	}

	/**
	 * Saves the signed message in a file - original message, encoded message,
	 * and encrypted message are recorded.
	 */
	private static void saveResult() {
		if (e == null || d == null || n == null) {
			System.err.println("Please generate keys first.");
			return;
		}

		if (originalMsg == null || encryptedMsg == null) {
			System.err.println("Please sign any message first.");
			return;
		}

		File f = null;
		f = new File(SIGNED_PROPERTY_FILE_NAME);
		FileOutputStream fos;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(f);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("Entered message=" + originalMsg);
			bw.newLine();
			bw.write("Encoded message=" + encodeMessageToInt(originalMsg));
			bw.newLine();
			bw.write("encrypted message=" + encryptedMsg);
			bw.newLine();
		} catch (IOException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return;
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				return;
			}
		}
		System.out.println("Saving the result is completed.");
	}

	/**
	 * Verify a digital signature.
	 */
	private static void verifyMsg() {
		if (e == null || d == null || n == null) {
			System.err.println("Please generate keys first.");
			return;
		}

		BigInteger originalEncodedMsg;
		BigInteger dcrptMsg;

		System.out.println("Verifying the message...");
		System.out.println("Your message: " + originalMsg);

		originalEncodedMsg = encodeMessageToInt(originalMsg);
		System.out.println("Your encoded message: " + encodeMessageToInt(originalMsg));

		System.out.println("Your encrypted message: " + encryptedMsg);

		System.out.println("Decrypting the message...");
		dcrptMsg = decrypt(encryptedMsg);
		System.out.println("Your decrypted message: " + dcrptMsg);

		if (originalEncodedMsg.compareTo(dcrptMsg) == 0) {
			System.out.println("Verify succeeded...");
		} else {
			System.out.println("Verify failed...");
		}
	}

	/**
	 * Sign a message. It encodes the message into an integer. Then,
	 * the encoded message is encrypted with the private key and modulus.
	 */
	private static void signMsg() {
		if (e == null || d == null || n == null) {
			System.err.println("Please generate keys first.");
			return;
		}
		System.out.println("Signing the message...");
		System.out.println("Please enter a message to sign: ");
		input.nextLine();
		originalMsg = input.nextLine().trim();
		if (originalMsg.length() > 0) {
			System.out.println("Encrypting the message...");
			encryptedMsg = encrypt(encodeMessageToInt(originalMsg));
		}
		System.out.println("Your message: " + originalMsg);
		System.out.println("Your encrypted message: " + encryptedMsg);
	}

	/**
	 * Show private key, public key, and modulus
	 */
	private static void viewKeys() {
		if (e == null || d == null || n == null) {
			System.err.println("Please generate keys first.");
			return;
		}
		System.out.println("\t>> Information of keys <<");
		System.out.println("\t>> (Encryption key) Private key (" + e.bitLength() + " bits): " + e);
		System.out.println("\t>> (Decryption key) Public key (" + d.bitLength() + " bits): " + d);
		System.out.println("\t>> modulus (" + n.bitLength() + " bits): " + n);
	}

	/**
	 * Saves created keys in a file. They are private key, public key, and modulus.
	 * @param privateKey
	 * @param publicKey
	 * @param modulus
	 */
	private static void saveKeys(BigInteger privateKey, BigInteger publicKey, BigInteger modulus) {
		if (e == null || d == null || n == null) {
			System.err.println("Please generate keys first.");
			return;
		}

		File f = null;
		f = new File(KEY_FILE_NAME);
		FileOutputStream fos;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(f);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("PRIVATE=" + e);
			bw.newLine();
			bw.write("PUBLIC=" + d);
			bw.newLine();
			bw.write("MODULUS=" + n);
			bw.newLine();
		} catch (IOException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return;
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				return;
			}
		}
		System.out.println("Saving keys completed.");
	}

	/** 
	 * Loads keys from a file.
	 */
	private static void loadKeys() {
		File f = new File(KEY_FILE_NAME);
		if (!f.exists()) {
			System.err.println("File doesn't exist.");
			System.err.println("Please generate keys first and then save them.");
			return;
		}
		BufferedReader in = null;
		String line;
		String[] data;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			while ((line = in.readLine()) != null) {
				data = line.split("=");
				if (data[0].equals("PRIVATE")) {
					System.out.println("data[1_private]: " + data[1]);
					e = new BigInteger(data[1]);
				}
				if (data[0].equals("PUBLIC")) {
					System.out.println("data[1_[public]: " + data[1]);
					d = new BigInteger(data[1]);
				}
				if (data[0].equals("MODULUS")) {
					System.out.println("data[1_modulus]: " + data[1]);
					n = new BigInteger(data[1]);
				}
			}
		} catch (Exception e) {
			System.err.println("Can't read from the file.");
			return;
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
			}
		}
		System.out.println("Loading keys completed.");
	}

	/**
	 * Generates keys using two prime numbers.
	 * @param keySize the length of the key in binary
	 */
	private static void generateKeys(int keySize) {
		// Generate p and q
		BigInteger p = makePrime(keySize, CERTAINTY);
		BigInteger q = makePrime(keySize, CERTAINTY);
		// Compute n (modulus)
		n = p.multiply(q);

		// Compute f(n)=(p-1).(q-1)
		BigInteger phiN = (p.subtract(ONE)).multiply(q.subtract(ONE));

		do {
			// Create encryption exponent (for signing with private key)
			e = makePrime(keySize, CERTAINTY);
		} while ((e.compareTo(ONE) == 1) && (e.compareTo(phiN) == -1) && (e.gcd(phiN).compareTo(ONE) != 0));

		// Calculate decryption exponent (for public key)
		d = modInverse(e, phiN);
		System.out.println("Keys are generated...");
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
		while (!isProbablePrime(x, certainty)) {
			x = x.add(TWO);
		}
		return x;
	}

	/**
	 * Checks if x is probable prime.
	 * @param x
	 * @param certainty
	 * @return true when x is prime.
	 */
	private static boolean isProbablePrime(BigInteger x, int certainty) {
		// Not prime if n = 1
		if (x.compareTo(TWO) < 0)
			return false;

//		// Not prime if n is even
//		if (!x.testBit(0))
//			return false;
		if (x.mod(TWO).equals(BigInteger.ZERO))
	    {
	        return x.equals(TWO);
	    }

		// Avoid expensive tests (up to 4 bits)
		if (x.compareTo(new BigInteger("3")) == 0)
			return true;
		if (x.compareTo(new BigInteger("5")) == 0)
			return true;
		if (x.compareTo(new BigInteger("7")) == 0)
			return true;
		if (x.compareTo(new BigInteger("11")) == 0)
			return true;
		if (x.compareTo(new BigInteger("13")) == 0)
			return true;

		// Do Miller Rabin test
		return millerRabin(x, certainty);
	}

	/**
	 * Starts the Miller Rabin test.
	 * @param n
	 * @param certainty
	 * @return true when it it a prime
	 */
	private static boolean millerRabin(BigInteger n, int certainty) {
		BigInteger a;

		// Randomly selects a
		do {
			a = new BigInteger(n.bitLength(), new Random());
		} while (a.equals(BigInteger.ZERO));
		if (!millerRabinWitness(a, n)) {
			return false;
		}
		return true;
	}

	/**
	 * It is a witness function of Miller Rabin test
	 * @param a an integer to compare with
	 * @param n modulus
	 * @return 
	 */
	private static boolean millerRabinWitness(BigInteger a, BigInteger n) {
		BigInteger n_minus_one = n.subtract(BigInteger.ONE);
		BigInteger u = n_minus_one;

		// n-1 = (2^t)u
		// n - 1 is the binary representation of the odd integer u followed by
		// exactly t zeros.
		int t = u.getLowestSetBit();
		u = u.shiftRight(t);

		// MODULAR-EXPONENTIATION(a, u, n)
		BigInteger x = a.modPow(u, n);

		if (x.equals(BigInteger.ONE))
			return true;

		// Squares the result t times
		for (int i = 0; i < t - 1; i++) {
			if (x.equals(n_minus_one))
				return true;
			x = x.multiply(x).mod(n);
		}
		if (x.equals(n_minus_one))
			return true;
		return false;
	}

	// Returns the modular inverse of a mod n.
	/**
	 * Returns the modular inverse of a mod n. It uses extended Euclidean Algorithm.
	 * @param a	integer to compare with
	 * @param n modulus
	 * @return
	 * @throws ArithmeticException when there is no inverse
	 */
	private static BigInteger modInverse(BigInteger a, BigInteger n) throws ArithmeticException {

		// Initializes the parameters
		BigInteger a0 = a;
		BigInteger n0 = n;
		BigInteger t0 = ZERO;
		BigInteger t = ONE;
		BigInteger q = n0.divide(a0);
		BigInteger r = n0.subtract(q.multiply(a0));

		// Repeats until the remainder in the successive divisions is bigger
		// than zero
		while (r.compareTo(ZERO) > 0) {
			BigInteger temp = t0.subtract(q.multiply(t));

			// Converts temp to positive if it's negative.
			if (temp.compareTo(ZERO) > 0)
				temp = temp.mod(n);
			if (temp.compareTo(ZERO) < 0)
				temp = n.subtract(temp.negate().mod(n));

			// Update parameters
			t0 = t;
			t = temp;
			n0 = a0;
			a0 = r;
			q = n0.divide(a0);
			r = n0.subtract(q.multiply(a0));
		}

		// Throws ArithmeticException when there is no inverse.
		if (!a0.equals(ONE)) {
			throw new ArithmeticException();
		} else {
			return t.mod(n);
		}
	}

	/**
	 * Encodes a string message to int
	 * @param message
	 * @return an integer encoded
	 */
	private static BigInteger encodeMessageToInt(String message) {
		BigInteger x = ZERO;

		for (int i = 0; i < message.length(); i++) {
			char c = message.charAt(i);
			x = x.shiftLeft(8);
			x = x.xor(new BigInteger(new Integer((int) c).toString()));
		}

		return x;
	}

	private static BigInteger encrypt(BigInteger message) {
		return crypt(message, e);
	}

	private static BigInteger decrypt(BigInteger cypher) {
		return crypt(cypher, d);
	}

	/**
	 * encrypt or decrypt message
	 * 
	 * @param message
	 *            Message to process
	 * @param exponent
	 *            Exponent
	 * @return Processed Message
	 */
	private static BigInteger crypt(BigInteger message, BigInteger exponent) {
		return message.modPow(exponent, n);
	}

}
