package splitters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JProgressBar;

/**
 * Classe FileLocation, padre di tutti gli splitter, che unisce
 * tutto cio' che questi hanno in comune
 * 
 * @author Gamberi Elia
 */
public class FileLocation implements Runnable, SplitterInterface{
	
	/**
	 * Stringa che contiene l'estensione del file
	 */
	protected String Extension;
	
	/**
	 * File che contiene il File da splittare/joinare
	 */
	protected File FileLoc;
	
	/**
	 * File che contiene il nome del file dopo l'operazione di join
	 */
	protected File FinalName;
	
	/**
	 * ProgressBar, passata per essere aggiornata nel metodo run
	 */
	protected JProgressBar progress;
	
	/**
	 * FileInputStream e FileOutputStream
	 */
	protected FileInputStream fi;
	protected FileOutputStream fo;
	
	/*
	 * Type Of Divisione
	 */
	private char TOD;
	
	/**
	 * Costruttore, in cui inserisco il path del file
	 * @param FileLoc path
	 * @param progress ProgressBar, aggiornata col metodo run
	 */
	public FileLocation(String FileLoc,JProgressBar progress)
	{
		this.FileLoc = new File(FileLoc);
		this.progress=progress;
	}
	
	/**
	 * Costruttore, in cui inserisco anche il nome finale, usato nello join, e imposto l'estensione del file come
	 * ultima parte del nostro FinalName, per non dover inserire a mano l'estensione
	 * @param FileLoc path
	 * @param FinalName nome del file risultante dalla join
	 * @param progress ProgressBar, aggiornata col metodo run
	 */
	public FileLocation(String FileLoc, String FinalName,JProgressBar progress)
	{
		this.FileLoc= new File(FileLoc);
		
		Extension= FileLoc.substring(FileLoc.indexOf(".") + 1);
		Extension=Extension.substring(0,Extension.indexOf("."));
		this.FinalName=new File(FinalName+"."+Extension);
		
		this.progress=progress;
	}
	
	/**
	 * Ritorna il path del file
	 * @return path
	 */
	public String getFileLocation()
	{
		return FileLoc.getPath();
	}
	
	/**
	 * Ritorna il nome del file senza estensione, per controllare che non sia vuoto
	 * @return Nome dell'output senza estensione
	 */
	public String getFinalFileNameNoExtension()
	{
		int j = FinalName.getName().indexOf(".");
		return FinalName.getName().substring(0,j);
	}
	
	
	/**
	 * Ritorna il nome del file, senza path
	 * @return nome del file
	 */
	
	public String getName()
	{
		return FileLoc.getName();
	}
	
	/**
	 * Ritorna il nome del file richiesto dopo la join
	 * @return nome file post join
	 */
	public String getFinalName()
	{
		return FinalName.getName();
	}
	
	/**
	 * Ritorna il path della cartella che contiene il file
	 * @return cartella
	 */
	public String getFolder()
	{
		return FileLoc.getParent();
	}
	
	
	/**
	 * TOD : Type of Divisione
	 * possibili opzioni: 'b','c','z','n' o 'j'
	 * @return TOD
	 */
	public char getTOD()
	{
		return TOD;
	}
	
	/**
	 * Cambia il valore del TypeOfDivision
	 * @param newTOD nuovo valore di TOD
	 */
	public void setTOD(char newTOD)
	{
		TOD=newTOD;
	}
	
	/**
	 * Ritorna le informazioni dell'oggetto FileLocation
	 * funzione poi che, grazie al polimorfismo, ritorna un valore diverso in base quale
	 * splitter la sta eseguendo
	 * @return Info dell'oggetto come stringa
	 */
	public String getInfo()
	{
		return "Location: "+FileLoc;
	}
	
	
	/**
	 * Ritorna vero se il file e' da splittare, falso se e' da unire
	 * @return true se split, false se join
	 */
	public boolean isSplit()
	{
		if(FileLoc.getName().endsWith(".par"))
			return false;
		return true;
	}
	
	/**
	 * Funzione sostituita poi, tramite polimorfismo, dallo split di ogni figlio
	 */
	public void split()
	{
		System.err.println("Errore, split non definito");
	}
	
	/**
	 * Funzione sostituita poi, tramite polimorfismo, dal join di ogni figlio
	 */
	public void join()
	{
		System.err.println("Errore, join non definito");
	}
	
	/**
	 * Inizializza fi per prendere il file con path in FileLoc
	 * @throws Exception se non trova il file
	 */
	protected void newfi() throws Exception
	{
		try {
			fi = new FileInputStream(FileLoc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("File Not Found in split");
		}
	}
	
	/**
	 * Inizializza fo per prendere il file nella cartella con path indicato
	 * da getFolder(), e con nome uguale a FinalName
	 * @throws Exception se non trova il file
	 */
	protected void newfo() throws Exception
	{
		try {
			fo = new FileOutputStream(getFolder()+"/"+getFinalName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new Exception("File Not Found in join");
		}
	}
	/**
	 * Implementiamo run per i Thread:
	 * chiama split o join in base al TOD, e incrementa il
	 * valore contenuto dentro la ProgressBar "progress"
	 */
	public void run()
	{
		try {
		if(getTOD()=='j')
			join();
		else
			split();
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		progress.setValue(progress.getValue()+1);
	}
}
