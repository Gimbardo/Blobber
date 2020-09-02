package splitters;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JProgressBar;


/**
 * Classe che implementa un File Splitter Criptato, che quindi necessita di una chiave
 * libera in fase di split e specifica in fase di join
 * Il tipo di chiave e' una stringa, che per comodita'� convertiamo in un arrray di byte
 * 
 * @author Gamberi Elia
 *
 */
public class CryptSplitter extends NByteSplitter implements SplitterInterface{
	
	private byte[] key;
	
	/**
	 * Costruttore utilizzato per lo splitter
	 * @param FileLoc Path del file
	 * @param NByte Quantita' di byte della divisione
	 * @param key Chiave di cifratura
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public CryptSplitter(String FileLoc,int NByte,String key,JProgressBar progress)
	{
		super(FileLoc,NByte,progress);
		this.key=key.getBytes();
	}
	
	/**
	 * Costruttore utilizzato per la join
	 * @param FileLoc Path del file
	 * @param FinalName Nome del file ricomposto
	 * @param key Chiave di cifratura
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public CryptSplitter(String FileLoc,String FinalName,String key,JProgressBar progress)
	{
		super(FileLoc,FinalName,progress);
		this.key=key.getBytes();
	}
	
	
	private Cipher cipherGenerator()
	{
		//creo un key generator
		KeyGenerator generator = null;
		try {
			generator = KeyGenerator.getInstance("AES");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
			return null;
		}
		
		//lo inizializzo con la chiave
		generator.init(new SecureRandom(this.key));
		
		//genero una chiave segreta
		SecretKey secretkey = generator.generateKey();
		
		//creo un cipher
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
			e1.printStackTrace();
			return null;
		}
		
		//lo inizializzo
		try {
			if(getTOD()=='c') 
				cipher.init(Cipher.ENCRYPT_MODE, secretkey);
			else if(getTOD()=='j') 
				cipher.init(Cipher.DECRYPT_MODE, secretkey);
			else {
				System.err.println("Tentativo di creazione di un cipher su un oggetto non considerato CryptSplitter");
				return null;}
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
			return null;
		}
		return cipher;
	}
	
	
	
	/**
	 * Metodo che ritorna il valore di chiave
	 * @return chiave
	 */
	public String getKey()
	{
		return key.toString();
	}
	
	/**
	 * Metodo che ritorna le info utilizzate nella tabella
	 */
	public String getInfo()
	{
		return super.getInfo()+" Key = "+key;
	}
	
	
	/**
	 * Metodo utilizzato per splittare utilizzando la chiave inserita
	 */
	public void split()
	{
		int n=1;
		
		try {
			newfi();
			}catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
		
		CipherOutputStream foc;
		
		Cipher cipher=cipherGenerator();
		
		byte[] moment=new byte[NByte];
		
		
		try {
			int nByteMom = fi.read(moment,0,NByte);
			while(nByteMom >= 0)
			{
				System.out.println("Splitting into "+getFolder()+"/"+n+getName()+".crypt.par");
				fo = new FileOutputStream(getFolder()+"/"+n+getName()+".crypt.par");
				
				foc = new CipherOutputStream(fo,cipher);
				
				foc.write(moment,0,nByteMom);
				n++;
				foc.close();
				fo.close();
				nByteMom = fi.read(moment,0,NByte);
			}
			fi.close();
		}
		catch (IOException e){
			e.printStackTrace();
			return;
		}
		return;
	}
	
	/**
	 * Metodo utilizzato per riunire i file splittati, tramite una chiave
	 */
	public void join()
	{
		int dim;
		
		try {
			newfo();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Cipher cipher=cipherGenerator();
		
		CipherInputStream fic;
		
		for(int i=1;new File(getFolder()+"/"+i+getName().substring(1)).isFile();i++)
		{
			try {
				System.out.println("Joining "+getFolder()+"/"+i+getName().substring(1));
				fi = new FileInputStream(getFolder()+"/"+i+getName().substring(1));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
			
			fic = new CipherInputStream(fi,cipher);
			
			try
			{
				
			dim = fi.available();
			byte[] b= new byte[dim];
		
			
			fic.read(b,0,dim);
			
			fo.write(b,0,dim);
			
			fic.close();
			fi.close();
		
			}catch(IOException e) {
				e.printStackTrace();
				return;
			}
			
		}
		try {
			fo.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
