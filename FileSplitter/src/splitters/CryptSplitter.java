package splitters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.Key;

public class CryptSplitter extends NByteSplitter{
	
	private byte[] key;
	
	/**
	 * Costruttore utilizzato per lo splitter
	 * @param FileLoc Path del file
	 * @param NByte Quantità di byte della divisione
	 * @param key Chiave di cifratura
	 */
	public CryptSplitter(String FileLoc,int NByte,String key)
	{
		super(FileLoc,NByte);
		this.key=key.getBytes();
	}
	
	/**
	 * Costruttore utilizzato per la join
	 * @param FileLoc Path del file
	 * @param FinalName Nome del file ricomposto
	 * @param key Chiave di cifratura
	 */
	public CryptSplitter(String FileLoc,String FinalName,String key)
	{
		super(FileLoc,FinalName);
		this.key=key;
	}
	
	/**
	 * Metodo che ritorna il valore di chiave
	 * @return chiave
	 */
	public String getKey()
	{
		return key;
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
				fo = new FileOutputStream(getFolder()+"/"+n+getName()+".par"); //salvo tutti i file in una cartella col nome del padre, in ordine di divsione
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
