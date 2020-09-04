package splitters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JProgressBar;

/**
 * Classe che, estendendo NByteSplitter, implementa uno splitter in grado di
 * comprimere i file risultanti dalla divisione, e decomprimerli al momento
 * della ricomposizione
 * 
 * @author Gamberi Elia
 *
 */
public class CryptoSplitter extends NByteSplitter implements SplitterInterface{
	
	//chiave super segreta
	private static byte[] key = "ABChiaveSegretaD".getBytes();
	
	/**
	 * Costruttore per la creazione di uno splitter che crypta i risultati
	 * @param FileLoc path del file
	 * @param NByte Dimensione dei file divisi alla fine
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public CryptoSplitter(String FileLoc,int NByte,JProgressBar progress)
	{
		super(FileLoc,NByte,progress);
	}
	
	/**
	 * Costruttore per la join
	 * @param FileLoc path del file
	 * @param FinalName Nome del file risultante dalla merge
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public CryptoSplitter(String FileLoc,String FinalName,JProgressBar progress)
	{
		super(FileLoc,FinalName,progress);
	}
	
	
	private Cipher createCipher()
	{
		Key secretkey = new SecretKeySpec(key,0,key.length, "AES");
		
		
		//uso la key per generare l'iv, utilizzandola, di fatto, due volte
		IvParameterSpec iv=new IvParameterSpec(key);
		
		Cipher ciph = null;
		try {
			ciph = Cipher.getInstance("AES/CBC/PKCS5Padding");
			if(getTOD()=='c')
				ciph.init(Cipher.ENCRYPT_MODE, secretkey, iv);
			else if(getTOD()=='j')
				ciph.init(Cipher.DECRYPT_MODE, secretkey, iv);
			else {
				System.err.println("BadTOD inserted");
				return null;}
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			
			e.printStackTrace();
			return null;
		}
		
		
		
        
        
        return ciph;
	}
	
	/**
	 *  Metodo che splitta il file memorizzato dentro FileLoc
	 *  in pezzi da NByte cryptati
	 */
	public void split()
	{
		int n=1;
		
		Cipher ciph=createCipher();
		
		CipherOutputStream foc;
		try {
			newfi();
			}catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
		
		
		byte[] moment=new byte[NByte];
		
		
		try {
			int nByteMom = fi.read(moment,0,NByte);
			while(nByteMom >= 0)
			{
				
				String FileRis= getFolder()+File.separator+n+getName()+".crypt.par";
				
				foc = new CipherOutputStream(new FileOutputStream(FileRis),ciph); //salvo tutti i file in una cartella col nome del padre, in ordine di divsione
				
				System.out.println("Splitting into" + FileRis+" "+nByteMom+" bytes");
				
				
				
				foc.write(moment,0,nByteMom);
				n++;
				foc.close();
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
	 * Metodo che effettua la join tra i vari pezzi splittati in precedenza da un CryptSplitter
	 * Utilizzabile su uno qualunque dei primi 9 file splittati: non è necessario utilizzarlo sul primo
	 */
	public void join()
	{
		
		CipherInputStream fic = null;
		
		Cipher ciph = createCipher();
		
		try {
			newfo();
			}catch(Exception e)
			{
				e.printStackTrace();
				return;
			}
		
		for(int i=1;new File(getFolder()+File.separator+i+getName().substring(1)).isFile();i++)
		{
			
			
			String FileInput= getFolder()+File.separator+i+getName().substring(1);
			
			
			try {
				fi= new FileInputStream(FileInput);
				fic = new CipherInputStream(fi,ciph);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			
			
			try
			{
			int mom;
			System.out.println("Joining "+FileInput+" for "+fi.available()+" bytes");
		
			
				while((mom = fic.read())>0)
				{
					fo.write(mom);
				}
			
			fic.close();
			
		
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
