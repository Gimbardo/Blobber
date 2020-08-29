package graphic;

import javax.swing.JFrame;

public class StartFrame extends JFrame{
	/**
	 * Frame iniziale, creato dal main.
	 * Il nome Blobber deriva dal fatto che tratta
	 * i file come "blob", per poterli separare e riunire
	 */
	public StartFrame()
	{
		super("Blobber");
		setBounds(100,100,500,500);
		setResizable(false);
	}
}
