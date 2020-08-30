package splitters;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JProgressBar;
/**
 * Classe che espande NByteSplitter, e non FileLocation, per consentire di
 * splittare un file dato il numero di file finali
 * Il motivo per questa eredità è per mantenere la funzione join di NByteSplitter, che sarebbe stata
 * identica a quella di di questa classe
 * 
 * @author Gamberi Elia
 *
 */
public class NPartsSplitter extends NByteSplitter implements SplitterInterface{
	
	private int NParts;
	
	/**
	 * Costruttore che crea uno splitter
	 * @param FileLoc path del file
	 * @param NParts numero di parti per la divisione
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public NPartsSplitter(String FileLoc,int NParts,JProgressBar progress)
	{
		super(FileLoc,0,progress);
		/*
		 * Ho tentato di usare un NByteSplitter per creare questo splitter, dividendo il numero di byte disponibile
		 * per il numero di parti, ma mi sono reso conto in fase di testing che come metodo non risultava preciso, e spesso
		 * ci saremmo trovati con un numero di parti non uguale a quello richiesto
		 * Una soluzione utilizzando comunque NByteSplitter ci sarebbe stata, ma avrebbe inutilmente complicato il progetto,
		 * necessitando una modifica al sistema di split di NByteSplitter
		 * Ho comunque mantenuto NByte in quanto ci è utile nella funzione split*/
		try {
		FileInputStream fi = new FileInputStream(FileLoc);
		
		super.NByte=(int)Math.ceil(fi.available()/NParts);
		
		fi.close();
		
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
		this.NParts=NParts;
	}
	
	/**
	 * Costruttore utilizzato per il join
	 * superfluo, in quanto sarebbe stato sufficiente usare un NByteSplitter nella
	 * funzione che crea quest'oggetto, 
	 * ma ho preferito lasciarlo per completezza
	 * @param FileLoc path del file
	 * @param FinalName nome del file ricomposto
	 * @param progress JProgressBar, per incrementarla una volta finito il lavoro del thread
	 */
	public NPartsSplitter(String FileLoc,String FinalName,JProgressBar progress)
	{
		super(FileLoc,FinalName,progress);
	}
	
	/**
	 * Metodo che ritorna il numero di file che vogliamo ottenere
	 * @return N Byte
	 */
	public int getNParts()
	{
		return NParts;
	}
	
	/**
	 * Metodo che ritorna le info dello Splitter, utilizzato per la tabella
	 * @return una stringa con le info
	 */
	public String getInfo()
	{
		return "N Parti= "+NParts;
	}
	
	/**
	 * split ispirato a NByteSplitter, ma diverso in quando deve contare il numero di elementi che crea.
	 * In particolare conta il numero di byte per arrivare a nparti-1, e poi crea l'ultimo file, più pesante
	 * degli altri nella maggior parte dei casi
	 */
	public void split()
	{
		try {
			newfi();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		byte[] moment=new byte[NByte];
		
		try {
			int nByteMom = fi.read(moment,0,NByte);
			
			//for che crea NParts-1 elementi
			for(int i=1;i<NParts+1;i++)
			{
				fo = new FileOutputStream(getFolder()+"/"+i+getName()+".par"); //salvo tutti i file in una cartella col nome del padre, in ordine di divsione
				fo.write(moment,0,nByteMom);
				fo.close();
				nByteMom = fi.read(moment,0,NByte);
			}
			
			
			//Scrivo tutti i byte rimanenti in un unico file
			fo=new FileOutputStream(getFolder()+NParts+getName()+".par");
			
			nByteMom=fi.available();
			
			moment=new byte[nByteMom];
			fi.read(moment,0,nByteMom);
			fo.write(moment,0,nByteMom);
			
			//chiudo lo stream
			fi.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return;
	}
	
	/**
	 * join che va semplicemente a richiamare il join del padre (NByteSplitter), superfluo in quanto avremmo
	 * potuto creare un NByteSplitter ma ho preferito lasciarlo per completezza
	 */
	public void join()
	{
		super.join();
	}
}
