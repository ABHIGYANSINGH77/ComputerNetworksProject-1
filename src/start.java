
import java.math.BigInteger;
import java.util.Scanner;

public class start {
	static boolean flag = true;
	static String filename = "", dirname = "", key = "";
	static int chk = 0, ch = 0;
	// static byte [] bkey;

	public static void main(String[] args) {
		EncDec obj = new EncDec();
		Scanner chscanner = new Scanner(System.in);

		while (flag) {
			introPattern();
			System.out.println(
					"\n===============================================================\n 1.ENCRYPT a file \t 2.DECRYPT a file \t 3.Open a File\n 4.Delete a File \t 5.Exit Program\n===============================================================");
			System.out.println("Enter Your Choice: ");
			if (chscanner.hasNextInt())
				ch = chscanner.nextInt();
			switch (ch) {
				case 1:
					do {
						System.out.println("Enter file/directory path: ");
						filename = chscanner.next();
						filename = filename.replaceAll("\\\\", "/"); // for windows dir scheme
						chk = FunctionSet.check(filename);
					} while (chk != 1);

					do {
						System.out.println("Enter path to store encrypted file: ");
						dirname = chscanner.next();
						dirname = dirname.replaceAll("\\\\", "/");
						chk = FunctionSet.check(dirname);
					} while (chk != 2);

					do {
						System.out.println(
								"*** NOTE: The key follows \'Use and Forget\' technology. No need for storage. ***\n");
						System.out.println("Enter Your Private Key (length>10)");
						chscanner.nextLine();
						key = chscanner.nextLine();
						if (key.length() < 10)
							System.out.println("\t\t *** Private Key Size should be > 10 in size ***");
					} while (key.length() < 10);

					key = FunctionSet.KeyGen(key);

					BigInteger m = new BigInteger(key); // convert to BI
					BigInteger Enkey = RsaFunctionClass.EncDec(m, RsaFunctionClass.e, RsaFunctionClass.n); // RSA-Encrypt
																											// the key.
																											// This key
																											// is
																											// generated
																											// from RSA
																											// algo.
					String keyloc = RsaFunctionClass.WriteEncKey(Enkey, dirname, filename); // write encrypted key to
																							// file for further use

					obj.encrypt(filename, dirname, key);

					System.out.println("\nFile ENCRYPTED Successfully as 'enc.end', Stored at" + "'" + dirname + "'");
					System.out.println("ATTENTION! NOW Your Encrypted Private Key is:" + Enkey
							+ "\n\tIt is Saved for You at '" + keyloc + "'");
					break;

				case 2:
					do {
						System.out.println("Enter path of the Encrypted File: ");
						filename = chscanner.next();
						filename = filename.replaceAll("\\\\", "/");
						chk = FunctionSet.check(filename);
					} while (chk != 1);

					// Get Original Extension for Decryption
					System.out.println("Enter EXTENSION for decrypted file (txt,pdf,jpg,mp3,mp4,etc):");
					String extname = chscanner.next();
					extname = extname.substring(extname.lastIndexOf(".") + 1); // if user provided a '.' with extension

					do {
						System.out.println("Enter path of Directory to store decrypted file:");
						dirname = chscanner.next();
						dirname = dirname.replaceAll("\\\\", "/");
						chk = FunctionSet.check(dirname);
					} while (chk != 2);

					String regex = "[0-9]+"; // Regular Expression for string to make sure key contains only numbers
					do {
						System.out.println("Enter the Encrypted Private Key for the file:");
						key = chscanner.next();
						if (key.length() < 500 || !(key.matches(regex)))
							System.out.println(
									"\t\t *** Encrypted-Key Size must be >500 with only contain Numeric Values! ***");
					} while ((key.length() < 500) || !(key.matches(regex)));

					BigInteger c = new BigInteger(key); // convert to BI
					BigInteger Deckey = RsaFunctionClass.EncDec(c, RsaFunctionClass.d, RsaFunctionClass.n);

					key = Deckey.toString();

					obj.decrypt(filename, extname, dirname, key);

					System.out.println(
							"\nFile DECRYPTED Successfully as 'dec." + extname + ",' Stored at " + "'" + dirname + "'");
					break;

				case 3:
					do {
						System.out.println("Enter Name of the File to be opened:");
						filename = chscanner.next();
						filename = filename.replaceAll("\\\\", "/");
						chk = FunctionSet.check(filename);
					} while (chk != 1);

					FunctionSet.openfile(filename); // Static Call
					break;

				case 4:
					System.out.println("Enter the name of the file you want to delete:");
					String fname = chscanner.next();
					fname = fname.replaceAll("\\\\", "/");
					System.out.println(
							"Do you want to delete the " + fname + " file! \nEnter 1 to Confirm and 2 to Abort:");
					int opt = 0;
					if (chscanner.hasNextInt())
						opt = chscanner.nextInt();
					FunctionSet.delencf(fname, opt);
					break;

				case 5:
					flag = false;
					System.out.println("Good Bye!");
					chscanner.close();
					break;

				default:
					flag = false;
					System.out.println("No Such Option... Good Bye!");
					chscanner.close();
			}
		}
	}

	private static void introPattern() {

		int rows = 5;
		System.out.println("");

		// Top design
		for (int i = 1; i <= rows; i++) {
			for (int j = i; j < rows; j++) {
				System.out.print(" ");
			}
			for (int k = 1; k <= (2 * i - 1); k++) {
				if (k == 1 || i == rows || k == (2 * i - 1)) {
					System.out.print("\t* *");
				} else {
					System.out.print("   ");
				}
			}
			System.out.println("");
		}

		System.out.print(
				"\n\t\t\tCOMPUTER NETWORKS AND SECURITY \n\n\t\t\t\t Usn & Names \n\n1MV19CS001-> AAKASH TYAGI\n1MV19CS002-> ABHIGYAN SINGH\n1MV19CS005-> ADARSHA SV\n1MV19CS007-> ADITYA\n1MV19CS023-> ARJUN GARG\n\n");

		// Bottom design
		for (int i = rows; i >= 1; i--) {
			for (int j = i; j < rows; j++) {
				System.out.print(" ");
			}
			for (int k = 1; k <= (2 * i - 1); k++) {
				if (k == 1 || i == rows || k == (2 * i - 1)) {
					System.out.print("\t* *");
				} else {
					System.out.print("   ");
				}
			}
			System.out.println("");
		}

	}
}
