package vServer_pkg;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class ServerUI extends JFrame {
	private static final long serialVersionUID = 1L;
	static final String appName = "vServer";

	JMenuBar menubar = new JMenuBar();
	JMenu mnuHelp = new JMenu("Help");
		JMenuItem mnuItmHelpViewHelp = new JMenuItem("View Help");
		JMenuItem mnuItmHelpAbout = new JMenuItem("About");

	JPanel pnlWest = new JPanel();
	JLabel lblServerRxcPort = new JLabel("Rxc Port: " + Server.theServerRxcPort);
	JLabel lblServerTxcPort = new JLabel("Txc Port: " + Server.theServerTxcPort);
	JLabel lblFileServerPort = new JLabel("FileServer Port: " + Server.theFileServerPort);
	JLabel lblConnection = new JLabel("Connects: ");
	JLabel lblLogin = new JLabel("Logins: ");
	JLabel lblLastOnline = new JLabel("last On: ");
	JLabel lblLogout = new JLabel("Logouts: ");
	JLabel lblIncoming = new JLabel("Total In: ");
	JLabel lblTotalMsg = new JLabel("Total Msg: ");
	JLabel lblTimeOn = new JLabel("TimeOn: 0");

	JButton btnUpdate = new JButton("update stats");
	
//	JLabel lblFile
	
	Timer timer;
	TimerTask timerTask;
	
	public ServerUI()
	{
		this.setTitle(appName);
		this.setLayout(new BorderLayout());
		
		pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));

		pnlWest.add(lblServerRxcPort);
		pnlWest.add(lblServerTxcPort);
		pnlWest.add(lblFileServerPort);
		pnlWest.add(lblConnection);
		pnlWest.add(lblLogin);
		pnlWest.add(lblLastOnline);
		pnlWest.add(lblLogout);
		pnlWest.add(lblIncoming);
		pnlWest.add(lblTotalMsg);
		pnlWest.add(lblTimeOn);
		this.add(pnlWest, BorderLayout.WEST);
		
		this.add(btnUpdate, BorderLayout.SOUTH);
		
		menubar.add(mnuHelp);
			mnuHelp.add(mnuItmHelpViewHelp);
			mnuHelp.add(mnuItmHelpAbout);
		
		this.setJMenuBar(menubar);
		
		addListeners();
		timer.scheduleAtFixedRate(timerTask, 1000, 1000);
	}
	
	private void addListeners()
	{
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblConnection.setText("Connects: " + Server.countOfConnection);
				lblLogin.setText("Logins: " + Server.countOfLogin);
				lblLastOnline.setText("last On: " + Server.countOflastOnline);
				lblLogout.setText("Logouts: " + Server.countOfLogout);
				lblIncoming.setText("Total In: " + Server.countOfTotalIncoming);
				lblTotalMsg.setText("Total Msg: " + Server.countOfTotalMsg);
			}
		});
		
		timer = new Timer();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				btnUpdate.doClick();
				lblTimeOn.setText("TimeOn: " + (Integer.parseInt(lblTimeOn.getText().split(" ", 0)[1]) + 1) );
			}
		};
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent arg0) {
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				Server.isRunning = false;
				System.out.println("ServerUI > windowClosing");
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}
			@Override
			public void windowActivated(WindowEvent arg0) {
			}
		});

	}
}
