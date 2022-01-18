
/*This Class Contains 02 Functions:
 * Encryption Function
 * Decryption Function
 */
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class EncDec {
	int flag = 0, shiftby = 2;
	String key = "";
	double percent;
	Scanner inputscanner = new Scanner(System.in);

	void encrypt(String filename, String dirname, String key) // encrypt function
	{
		try {
			File dir = new File("TempFiles"); // dir reffers destination path.
			if (!dir.exists())
				dir.mkdir(); // make a folder(if donot exist) for temporary files which will b deleted at end
								// of prg

			RandomAccessFile fn = new RandomAccessFile(filename, "rw");
			RandomAccessFile in = new RandomAccessFile("TempFiles/cp-temp.end", "rw"); // heads is the extension given
																						// for file
			RandomAccessFile outTemp = new RandomAccessFile("TempFiles/enc-T.end", "rw");
			RandomAccessFile out = new RandomAccessFile(dirname + "/enc.end", "rw");

			FunctionSet.copyFile(filename, "TempFiles/cp-temp.end");// Faster FileCopy using File Channels

			FunctionSet.rounds(in, outTemp, key, shiftby, "Encrypting"); // xor

			FunctionSet.shuffle(outTemp, out); // shuffle

			File f1 = new File("TempFiles/cp-temp.end");
			File f2 = new File("TempFiles/enc-T.end");
			f1.delete();
			f2.delete();

			fn.close();
			in.close();
			out.close(); // Release Resources
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	void decrypt(String filename, String extname, String dirname, String key) // decrypt function
	{
		try {
			File dir = new File("TempFiles");
			if (!dir.exists())
				dir.mkdir(); // make a folder(if donot exist) for temporary files which will b deleted at end
								// of prg

			RandomAccessFile fn = new RandomAccessFile(filename, "rw");
			RandomAccessFile in = new RandomAccessFile("TempFiles/cp-temp.end", "rw");
			RandomAccessFile out = new RandomAccessFile(dirname + "/dec." + extname, "rw");

			FunctionSet.shuffle(fn, in); // deshuffle
			FunctionSet.rounds(in, out, key, shiftby, "Decrypting"); // xor

			File f = new File("TempFiles/cp-temp.end");
			f.delete();

			System.out.println("Do you want to delete " + filename + "?\nEnter 1 for yes and 2 for No:");
			int opt = 0;
			if (inputscanner.hasNextInt()) {
				opt = inputscanner.nextInt();
				FunctionSet.delencf(filename, opt);
			} else
				System.out.println("Wrong Option!");
			// release resources
			in.close();
			out.close();
			fn.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
