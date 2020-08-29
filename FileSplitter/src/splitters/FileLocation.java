package splitters;
/*
 * Classe FileLocation, padre di tutti gli splitter, che unisce
 * tutto ciò che questi hanno in comune
 */
public class FileLocation implements Runnable{
	
	protected String Extension;
	
	protected String FileLoc;
	
	protected String FinalName;
	
	private char TOD;
	/**
	 * Costruttore, in cui inserisco il path del file
	 * @param FileLoc path
	 */
	public FileLocation(String FileLoc)
	{
		this.FileLoc=FileLoc;
	}
	
	/**
	 * Costruttore, in cui inserisco anche il nome finale, usato nello join, e imposto l'estensione del file come
	 * ultima parte del nostro FinalName, per non dover inserire a mano l'estensione
	 * @param FileLoc path
	 */
	public FileLocation(String FileLoc, String FinalName)
	{
		this.FileLoc=FileLoc;
		Extension=FileLoc.substring(FileLoc.indexOf(".") + 1);
		Extension=Extension.substring(0,Extension.indexOf("."));
		this.FinalName=FinalName+"."+Extension;
	}
	
	/**
	 * Ritorna il path del file
	 * @return path
	 */
	public String getFileLocation()
	{
		return FileLoc;
	}
	
	/**
	 * Ritorna il nome del file senza estensione, per controllare che non sia vuoto
	 * 
	 */
	public String getFinalFileNameNoExtension()
	{
		int i = FinalName.lastIndexOf("/");
		int j = FinalName.indexOf(".");
		return FinalName.substring(i+1,j);
	}
	
	
	/**
	 * Ritorna il nome del file, senza path
	 * @return nome del file
	 */
	
	public String getName()
	{
		int i = FileLoc.lastIndexOf("/");
		return FileLoc.substring(i+1);
	}
	
	/**
	 * Ritorna il nome del file richiesto dopo la join
	 * @return nome file post join
	 */
	public String getFinalName()
	{
		return FinalName;
	}
	
	/**
	 * Ritorna il path della cartella che contiene il file
	 * @return cartella
	 */
	public String getFolder()
	{
		int i = FileLoc.lastIndexOf("/");
		return FileLoc.substring(0, i);
	}
	
	
	/**
	 * TOD : Type of Divisione
	 * possibili opzioni: 'b','c','z','n' o 'j'
	 * @return
	 */
	public char getTOD()
	{
		return TOD;
	}
	
	public void setTOD(char newTOD)
	{
		TOD=newTOD;
	}
	/**
	 * Ritorna le informazioni dell'oggetto FileLocation
	 * funzione poi che, grazie al polimorfismo, ritorna un valore diverso in base a su quale
	 * splitter la stiamo eseguendo
	 * @return Info dell'oggetto come stringa
	 */
	
	
	public String getInfo()
	{
		return "Location: "+FileLoc;
	}
	
	
	/**
	 * Ritorna vero se il file è da splittare, falso se è da dividere
	 * @return
	 */
	public boolean isSplit()
	{
		if(FileLoc.endsWith(".par"))
			return false;
		return true;
	}
	
	/**
	 * Funzione sostituita poi, tramite polimorfismo, dallo split di ogni figlio
	 */
	public void split()
	{
		return;
	}
	
	/**
	 * Funzione sostituita poi, tramite polimorfismo, dal join di ogni figlio
	 */
	public void join()
	{
		return;
	}
	
	/**
	 * Implementiamo run per i Thread:
	 * chiama split o join in base al TOD
	 */
	public void run()
	{
		if(getTOD()=='j')
			join();
		else
			split();
	}
}
