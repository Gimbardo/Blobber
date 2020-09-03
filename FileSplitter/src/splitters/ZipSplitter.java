package splitters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.JProgressBar;

/**
 * Classe che, estendendo NByteSplitter, implementa uno splitter in grado di
 * comprimere i file risultanti dalla divisione, e decomprimerli al momento
 * della ricomposizione
 * 
 * @author Gamberi Elia
 *
 */
public class ZipSplitter extends NByteSplitter implements SplitterInterface{
	/**
	 * Costruttore per la creazione di uno splitter che comprime i file risultanti
	 * @param FileLoc path del file
	 * @param NByte Dimensione dei file divisi alla fine
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public ZipSplitter(String FileLoc,int NByte,JProgressBar progress)
	{
		super(FileLoc,NByte,progress);
	}
	
	/**
	 * Costruttore per la join
	 * @param FileLoc path del file
	 * @param FinalName Nome del file risultante dalla merge
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public ZipSplitter(String FileLoc,String FinalName,JProgressBar progress)
	{
		super(FileLoc,FinalName,progress);
	}
	
	//Livello di compressione che vogliamo dalla nostra compressione
	private static int ZipLevel = 9;
	
	/**
	 *  Metodo che splitta il file memorizzato dentro FileLoc
	 *  in pezzi da NByte
	 */
	public void split()
	{
		int n=1;
		
		ZipOutputStream foz;
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
				
				String FileRis= getFolder()+File.separator+n+getName()+".zip.par";
				
				foz = new ZipOutputStream(new FileOutputStream(FileRis)); //salvo tutti i file in una cartella col nome del padre, in ordine di divsione
				
				System.out.println("Splitting into" + FileRis+" "+nByteMom+" bytes");
				
				foz.setLevel(ZipLevel);
				
				foz.putNextEntry(new ZipEntry(FileRis));
				
				foz.write(moment,0,nByteMom);
				n++;
				foz.closeEntry();
				foz.close();
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
	 * Metodo che effettua la join tra i vari pezzi splittati in precedenza da uno ZipSplitter
	 * Utilizzabile su uno qualunque dei file splittati: non è necessario utilizzarlo sul primo
	 */
	public void join()
	{
		int dim;
		
		ZipInputStream fiz;
		
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
				fiz = new ZipInputStream(new FileInputStream(FileInput));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
			
			
			
			try
			{
			fiz.getNextEntry();
			dim = fiz.available();
			System.out.println("Joining "+FileInput+" for "+dim+" bytes");
			byte[] b= new byte[dim];
		
			
				while(fiz.read(b,0,dim)>0)
				{
					fo.write(b,0,dim);
				}
			
			fiz.close();
			
		
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
