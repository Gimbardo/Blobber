package graphic;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import splitters.FileLocation;

public class ChooseFrame extends JFrame{
	
	private ChoosePanel j;
	
	/**
	 * Frame per quando scegliamo che tipo di divisione/join vogliamo eseguire
	 * 
	 * @param l lista dei file, per poterne rimuovere uno
	 * in caso di chiusura forzata
	 */
	public ChooseFrame()
	{
		super("Choose");
		setBounds(450,200,400,200);
		setResizable(false);
	}
	
	public void addPanelReference(ChoosePanel j) {
		this.j=j;
	}
}
