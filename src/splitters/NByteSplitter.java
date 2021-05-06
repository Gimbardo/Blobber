package splitters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JProgressBar;

/**
 * Oggetto che implementa uno splitter che divide un file in pezzi da NByte, e li ricompone
 * sequenzialmente in base al nome
 * 
 * Come input della join possiamo avere uno qualunque dei primi 9 file splittati
 * 
 * @author Gamberi Elia
 *
 */
public class NByteSplitter extends FileLocation implements SplitterInterface{
	protected int NByte;
	
	/**
	 * Costruttore per lo splitter
	 * @param FileLoc Contiene il path del file
	 * @param NByte Contiene la dimensione dei file risultanti dallo split
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public NByteSplitter(String FileLoc,int NByte, JProgressBar progress)
	{
		super(FileLoc,progress);
		this.NByte=NByte;
	}
	
	/**
	 * Costruttore soprattutto per il join, in cui calcoliamo la dimensione del file e
	 * prendiamo in input il nome del file finale
	 * @param FileLoc path del file
	 * @param FinalName Nome del file risultante dalla join
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public NByteSplitter(String FileLoc,String FinalName,JProgressBar progress)
	{
		super(FileLoc,FinalName,progress);
		try {
			FileInputStream fi = new FileInputStream(FileLoc);
			NByte=fi.available();
			fi.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo che ritorna la quantita'� di byte di grandezza dei file risultanti dallo split, o
	 * la grandezza del file che abbiamo preso per il join
	 * @return N Byte
	 */
	public int getNByte()
	{
		return NByte; 
	}
	
	/**
	 * Metodo che ritorna le info dello Splitter, utilizzato per la tabella
	 * @return una stringa con le info
	 */
	public String getInfo()
	{
		return "NByte= "+NByte;
	}
	

	/**
	 *  Metodo che splitta il file memorizzato dentro FileLoc
	 *  in pezzi da NByte
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
		
		
		byte[] moment=new byte[NByte];
		
		try {
			int nByteMom = fi.read(moment,0,NByte);
			while(nByteMom >= 0)
			{
				System.out.println("Splitting into" + getFolder()+File.separator+n+getName()+".par"+" "+nByteMom+" bytes");
				//salvo tutti i file in un file col nome del padre, aggiungendo un numero all'inizio
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
			return;
		}
		return;
	}
	/**
	 * Metodo che effettua il join tra i vari pezzi splittati in precedenza da un NByteSplitter
	 * Utilizzabile su uno qualunque dei file splittati: non e' necessario utilizzarlo sul primo,
	 * ma e' sufficiente su uno dei primi 9
	 */
	public void join()
	{
		int dim;
		
		
		
		try {
		newfo();
		}catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
		for(int i=1;new File(getFolder()+"/"+i+getName().substring(1)).isFile();i++)
		{
			
			String FileInput=getFolder()+File.separator+i+getName().substring(1);
			
			try {
				fi = new FileInputStream(FileInput);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
			
			
			
			try
			{
				
			dim = fi.available();
			System.out.println("Joining "+FileInput+" for "+dim+" bytes");
			
			byte[] b = new byte[dim];
			
				if(fi.read(b,0,dim)>=0)
				{
					fo.write(b,0,dim);
				}
			
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