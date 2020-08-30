package graphic;

import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

import splitters.FileLocation;

/**
 * Modello della Tabella dentro alla prima finestra del Blobber,
 * contenente la lista di oggetti figli di FileLocation, e le loro informazioni
 * 
 * @author Gamberi Elia
 *
 */
public class FileTabMod extends DefaultTableModel{
	
	LinkedList<FileLocation> l;
	
	/*
	 * nome delle colonne
	 */
	private static String[] nomCol= {"Type","Details","Nome"};
	
	/**
	 * Costruttore del nostro Table Model
	 * @param e 
	 * @param l Lista dei File
	 * @param row_count Numero di colonne
	 */
	public FileTabMod(LinkedList<FileLocation> l) {
		super();
		this.l=l;
	}
	
	/**
	 * Ritorna il numero di righe
	 */
	public int getRowCount() {
		if(l==null)
			return 0;
		return l.size();
	}
	
	/**
	 * Rtorna il numero di colonne: 3
	 */
	public int getColumnCount() {
		return 3;
	}
	/**
	 * Dentro l'Array di Stringe nomCol troviamo il nome 
	 * di ogni colonna, questa funzione ne ritorna il nome
	 */
	public String getColumnName(int col) {
        return nomCol[col];
    }
	
	/**
	 * Il nome di ogni riga è il suo numero di riga stesso,
	 * e questa funzione ritorna questo valore come stringa
	 */
	public String getRowName(int row) {
		return String.valueOf(row+1);
	}
	
	/**
	 * Implementiamo il metodo che ci ritorna il valore
	 * in base alla riga e alla colonna in cui ci troviamo
	 */
	public Object getValueAt(int row,int col) {
		switch(col) {
		case 0:
			switch(l.get(row).getTOD()) {
			case 'b':
				return "NByte";
			case 'c':
				return "Cript";
			case 'z':
				return "Zipped";
			case 'n':
				return "NParts";
			case 'j':
				return "Join";
			default:
				return null;}
		case 1:
			return l.get(row).getInfo();
		case 2:
			return l.get(row).getName();
		default: return null;
		}
	}
	/**
	 * Impedisco di poter modificare le celle, se non usando i bottoni proposti
	 */
	public boolean isCellEditable(int row, int col) {
		return false;
		}
}
