import javax.crypto.KeyGenerator;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.io.ObjectOutputStream;
import java.lang.Object;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyStore;
import java.security.Key;

public class Decryption
{
	final private static int KEYSIZE = 128;
	final private static int DECRYPT_MODE = 2;
	final private static String FILENAME = "./textfile.txt.enc";
	final private static String DECRYPTED_FILEPATH = "./textfiledec.txt";
	final private static String IV_FILEPATH = "./iv.txt";
	final private static String KEY_FILEPATH = "./key.jceks";	
	final private static String ALGORITHM = "AES";	
	public static void main (String[] args)
	{	
		Decryption.startDecryption();	
	}
	public static void startDecryption() {
		try{
			//Get key
			SecretKey sk = loadKey();		
	
			byte[] iv = getFileByteArray(IV_FILEPATH);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");			
			cipher.init(DECRYPT_MODE, sk, new IvParameterSpec(iv));
			System.out.println("oh hey");
			System.out.println("hey");
			byte[] file = getFileByteArray(FILENAME);
			byte[] decryptedFile = cipher.doFinal(file);
			streamToFile(decryptedFile, DECRYPTED_FILEPATH);
		}
		catch (Exception e){
			System.out.println("1");
			System.out.println(e.getMessage());
			return;
		}
		
	}
	public static byte[] getFileByteArray(String filepath){
		try{	
			File file = new File(filepath);
			byte[] bytes = new byte[(int) file.length()];
			return Files.readAllBytes(new File(filepath).toPath());
		}catch(Exception e){
			
			System.out.println("2");
		}
	return null;
	}
	
	public static void streamToFile(byte[] file, String filepath){
		try{
			FileOutputStream fos = new FileOutputStream(filepath);
			fos.write(file);
			fos.close();	
		}catch (Exception e){

			System.out.println("3");
		}
		return;
	}
	public static SecretKey loadKey(){
		byte[] key = getFileByteArray(KEY_FILEPATH);
		return new SecretKeySpec(key, 0, key.length, ALGORITHM);
	}
}
