package graphic;

import java.util.LinkedList;

import javax.swing.table.DefaultTableModel;

import splitters.FileLocation;

public class FileTabMod extends DefaultTableModel{
	
	LinkedList<FileLocation> l;
	
	private String[] nomCol= {"Type","Details","Nome"};
	
	public FileTabMod(Object[] e,LinkedList<FileLocation> l,int row_count) {
		super(e,row_count);
		this.l=l;
	}
	
	public int getRowCount() {
		if(l==null)
			return 0;
		return l.size();
	}
	
	public int getColumnCount() {
		return 3;
	}
	@Override
	public String getColumnName(int col) {
        return nomCol[col];
    }
	
	
	public String getRowName(int row) {
		return String.valueOf(row+1);
	}
	
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
