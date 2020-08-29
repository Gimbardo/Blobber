package graphic;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import splitters.FileLocation;

public class ChooseFrame extends JFrame{
	private LinkedList<FileLocation> l;
	private ChoosePanel j;
	/**
	 * Frame della scelta, necessario per usare i window listener
	 * 
	 * @param l lista dei file, per poterne rimuovere uno
	 * in caso di chiusura forzata
	 */
	public ChooseFrame(LinkedList<FileLocation> l)
	{
		super("Choose");
		this.l=l;
		setBounds(450,200,400,200);
		setResizable(false);
		addWindowListener(new RemoveWhenAltF4());
	}
	
	public void addPanelReference(ChoosePanel j) {
		this.j=j;
	}
	
	class RemoveWhenAltF4 implements WindowListener{
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
		}
		/**
		 * rimuoviamo un elemento dalla lista (quello appena inserito)
		 * quando chiudiamo il chooseframe senza cliccare "add"
		 */
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			/*if(!j.buttonClosing()) {
				l.remove();
				}*/
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
		}
		
	}
}
