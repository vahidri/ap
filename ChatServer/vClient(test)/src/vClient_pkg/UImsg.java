package vClient_pkg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class UImsg extends JFrame {
	private static final long serialVersionUID = 1L;
	
	JComboBox<String> jcb = new JComboBox<>();
	JPanel pnlHist = new JPanel(new GridLayout(1, 1));
	JTextArea txtaHist = new JTextArea();
	
	JPanel pnlSouth = new JPanel() ;
	JTextField txtMsgToSend = new JTextField();
	JButton btnSend = new JButton("Send");
	
	JMenuBar menubar = new JMenuBar();
	JMenu mnuFileShow = new JMenu("Show Files");
	JMenu mnuFileHide = new JMenu("Hide Files");
	JMenu mnuHelp = new JMenu("Help");
	JMenu mnuExit = new JMenu("Logout then Exit");
	
	public static Timer timer;
	public static TimerTask timerTask;
	
	public UImsg(){
		System.out.println("UImsg>Constructor");
		this.setTitle("Message");
		this.setLayout(new BorderLayout());
		this.add(jcb, BorderLayout.NORTH);
		
		pnlHist.add(new JScrollPane(txtaHist));
		this.add(pnlHist, BorderLayout.CENTER);
		
		pnlSouth.setLayout(new BorderLayout());
		pnlSouth.add(txtMsgToSend, BorderLayout.CENTER);
		pnlSouth.add(btnSend, BorderLayout.EAST);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		menubar.add(mnuFileShow);
		menubar.add(mnuFileHide);
		menubar.add(mnuHelp);
		menubar.add(mnuExit);
		this.setJMenuBar(menubar);
	
		addListeners();
		
	}

	private void addListeners() {
		
		timerTask = new TimerTask() {
			@Override
			public void run() {
				makeUserList();
			}
		};
		
		mnuExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Txs.logout();
				System.exit(0);
			}
		});
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void makeUserList()
	{
		String ons = Txs.geton();
		int onCount = Integer.parseInt(SM.col(ons, 3));
//		if (SM.col(ons, 1).equals(Client.valRespDone))
		{
//			String wasSelected = jcb.getSelectedItem().toString();
			jcb.removeAll();
			for(int i=0; i<onCount; i++)
			{
				//4th seg is the first name
				jcb.addItem(SM.col(ons, i+4) );
			}
		}
//		else
//		{
//			System.err.println(ons);
//			this.setTitle("err: can't get Online List");
//		}
	}

}
