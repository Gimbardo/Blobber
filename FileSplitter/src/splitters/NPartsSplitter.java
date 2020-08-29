package splitters;

import java.io.FileInputStream;
import java.io.IOException;

public class NPartsSplitter extends NByteSplitter{
	
	private int NParts;
	
	/**
	 * Costruttore che calcola la dimensione del file da splittare, e in base a questa crea
	 * l'equivalente di un NByteSplitter, con NByte calcolato per rendere la quantità di file
	 * finali uguale a quella richiesta
	 * @param FileLoc path del file
	 * @param NParts numero di parti per la divisione
	 */
	public NPartsSplitter(String FileLoc,int NParts)
	{
		super(FileLoc,0);
		try {
		FileInputStream fi = new FileInputStream(FileLoc);
		
		super.NByte=(int) Math.ceil((double)(fi.available()/NParts));
		
		fi.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
		this.NParts=NParts;
	}
	
	/**
	 * Costruttore utilizzato per il join
	 * superfluo, in quanto sarebbe stato sufficiente usare un NByteSplitter, 
	 * ma ho preferito lasciarlo per completezza
	 * @param FileLoc path del file
	 * @param FinalName nome del file ricomposto
	 */
	public NPartsSplitter(String FileLoc,String FinalName)
	{
		super(FileLoc,FinalName);
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
	 * split che va semplicemente a richiamare lo split del padre (NbyteSplitter) grazie alle
	 * operazioni che abbiamo effettuato nel costruttore
	 */
	public void split()
	{
		super.split();
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
