import javax.crypto.KeyGenerator;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.io.ObjectOutputStream;
import java.lang.Object;
import java.security.KeyStore;

public class Encryption
{
	final private static int KEYSIZE = 128;
	final private static int ENCRYPT_MODE = 1;
	final private static String FILENAME = "./textfile.txt";
	final private static String ENCRYPTED_FILEPATH = "./textfile.txt.enc";
	final private static String IV_FILEPATH = "./iv.txt";
	final private static String KEY_FILEPATH = "./key.jceks";
	final private static String ALGORITHM = "AES";		

	public static void main (String[] args)
	{	
		Encryption.startEncryption();	
	}
	public static void startEncryption() {
		try{
			//Generate key
			KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
			//System.out.println(kg.getAlgorithm());
			kg.init(KEYSIZE);
			SecretKey sk = kg.generateKey();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");			
			cipher.init(ENCRYPT_MODE, sk);
			byte[] iv = cipher.getIV();
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			
			byte[] file = getFileByteArray(FILENAME);
			byte[] encryptedFile = cipher.doFinal(file);
			streamToFile(encryptedFile, ENCRYPTED_FILEPATH);
			streamToFile(iv, IV_FILEPATH);
			streamToFile(sk.getEncoded(), KEY_FILEPATH);	
		}
		catch (Exception e){
			System.out.println("1");
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

			System.out.println("1");
		}
		return;
	}	
}
