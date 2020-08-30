package graphic;

import javax.swing.JFrame;

public class StartFrame extends JFrame{
	/**
	 * Frame iniziale, creato dal main.
	 * Il nome Blobber deriva dal fatto che tratta
	 * i file come "blob", per poterli separare e riunire
	 * come se fossero fatti di gelatina
	 */
	public StartFrame()
	{
		super("Blobber");
		setBounds(100,100,500,500);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
