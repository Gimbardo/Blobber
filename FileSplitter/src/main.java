import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import graphic.*;


public class main {

	public static void main(String[] args){
		StartFrame f1= new StartFrame();
		f1.add(new StartPanel(f1));
		f1.setVisible(true);
	}
}
