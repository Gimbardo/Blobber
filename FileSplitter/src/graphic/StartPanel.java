package graphic;


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import splitters.FileLocation;

public class StartPanel extends JPanel implements ActionListener{
	
	/**
	 * Lista che contiene gli splitter, con le posizioni dei file da splittare 
	 */
	private LinkedList<FileLocation> filelist= new LinkedList<FileLocation>();
	
	private JFrame f;
	
	private FileTabMod TabMod;
	
	private JTable tab;
	
	JProgressBar progress;
	
	/**
	 * Costruttore del pannello nella pagina iniziale
	 * contiene la tabella e i 3 bottoni fondamentali:
	 * ADD, REMOVE e START
	 * @param f Frame in cui inseriamo il panel
	 */
	public StartPanel(JFrame f)
	{
		
		this.f=f;
		
		String[] colName= {"Processo","File"};
		TabMod=new FileTabMod(colName,filelist,0);
		
		tab=new JTable(TabMod);
		tab.setSize(400,400);
		
		
		add(tab);
		add(new JScrollPane(tab));
		
		
		JButton file= new JButton("FILE");
		file.addActionListener(this);
		add(file);
		
		JButton remove= new JButton("REMOVE");
		remove.addActionListener(new RemoveListener());
		add(remove);
		
		JButton start= new JButton("START");
		start.addActionListener(new StartListener());
		add(start);
		
		progress = new JProgressBar(0,0);
		add(progress);
		
	}
	/**
	 * Listener del bottone "File"
	 * apre una finestra che consente di scegliere un file, e successivamente apre
	 * un choose frame per avere informazioni su che azione si desidera
	 * applicare al file scelto
	 */
	public void actionPerformed(ActionEvent e)
	{
		
		String fn = "";
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			fn=""+fileChooser.getSelectedFile();}
		
		if(fn!="") {
			ChooseFrame f=new ChooseFrame(filelist);
		
			ChoosePanel cp=new ChoosePanel(filelist,f,TabMod,fn,progress);
			f.addPanelReference(cp);
			f.add(cp);
			f.setVisible(true);
			}
	}
	/**
	 * Listener del bottone "START"
	 * Avvia il processo di split e ricomposizione, creando e facendo partire i thread
	 * gli elementi vengono rimossi dalla lista nel momento in cui facciamo partire i thread, mentre
	 * la barra di caricamento si aggiorna solo quando il thread ha finito il suo lavoro
	 *
	 */
	public class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			LinkedList<Thread> ThreadList=new LinkedList<Thread>();
			
			
			for(int i=0;i<filelist.size();i++)
				ThreadList.add(new Thread(filelist.get(i)));
			
	
			
			while(ThreadList.size()!=0)
			{
				ThreadList.getFirst().run();
				ThreadList.removeFirst();
				
				filelist.removeFirst();
				TabMod.fireTableDataChanged();
			}
		}
	}
	/**
	 * Listener del bottone "REMOVE"
	 * Rimuove l'elemento selezionato nella tabella, e non fa nulla
	 * se non ci sono elementi selezionati
	 *
	 */
	public class RemoveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(tab.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(f,"Scegliere una riga prima di rimuove degli elementi",
						"Nessun Elemento Selezionato",
					    JOptionPane.ERROR_MESSAGE);
				return;}
				filelist.remove(tab.getSelectedRow());
				TabMod.fireTableDataChanged();
		}
	}
}
