package splitters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.Key;

import javax.swing.JProgressBar;

/**
 * Classe che implementa un File Splitter Criptato, che quindi necessita di una chiave
 * libera in fase di split e specifica in fase di join
 * Il tipo di chiave e' una stringa, che per comodita'  convertiamo in un arrray di byte
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
		
		FileOutputStream fo;
		FileInputStream fi;
		
		Key momkey = null;
		
		try {
			fi = new FileInputStream(FileLoc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		
		byte[] moment=new byte[NByte];
		
		try {
			int nByteMom = fi.read(moment,0,NByte);
			while(nByteMom >= 0)
			{
				fo = new FileOutputStream(getFolder()+"/"+n+getName()+".par"); 
				fo.write(moment,0,nByteMom);
				n++;
				fo.close();
				nByteMom = fi.read(moment,0,NByte);
			}
			fi.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return;
	}
	
	/**
	 * Metodo utilizzato per riunire i file splittati, tramite una chiave
	 */
	public void join()
	{
		return;
	}
}
