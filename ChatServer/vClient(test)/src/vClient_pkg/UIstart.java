package vClient_pkg;

import java.awt.*;
import java.awt.event.*;
//import java.io.*;
//import java.net.*;
import javax.swing.*;

public class UIstart extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public boolean isConnected = false;
	public boolean isEchoOK = false;
	
	//north
	JPanel pnlNorth = new JPanel();
	JLabel lblInf = new JLabel("info");
	
	//center
	JPanel pnlCenter = new JPanel();
	JLabel lblServer = new JLabel("Server(addr,Rxc,Txc):");
	JTextField txtServerAddr = new JTextField();
	JTextField txtServerRxcPort = new JTextField();
	JTextField txtServerTxcPort = new JTextField();
	
	//south
	JPanel pnlSouth = new JPanel();
	JButton btnConnect = new JButton("Connect");
	JButton btnSignup = new JButton("Sign Up");
	JButton btnLogin = new JButton("Login");
	
	public UIstart()
	{
		System.out.println("UIstart>Constructor");
		this.setTitle("Connection");
		this.setLayout(new BorderLayout());
		
		pnlNorth.add(lblInf);
		this.add(pnlNorth, BorderLayout.NORTH);
		
		pnlCenter.setLayout(new GridLayout(4, 1));
		pnlCenter.add(lblServer);
		pnlCenter.add(txtServerAddr);
		pnlCenter.add(txtServerRxcPort);
		pnlCenter.add(txtServerTxcPort);
		this.add(pnlCenter, BorderLayout.CENTER);
		
		
		pnlSouth.setLayout(new GridLayout(1,3));
		pnlSouth.add(btnConnect);
		btnSignup.setEnabled(false);
		btnLogin.setEnabled(false);
		pnlSouth.add(btnSignup);
		pnlSouth.add(btnLogin);
		this.add(pnlSouth, BorderLayout.SOUTH);
		
		txtServerAddr.setText(Client.theServerAddr);
		txtServerRxcPort.setText(String.valueOf(Client.theServerRxcPort) );
		txtServerTxcPort.setText(String.valueOf(Client.theServerTxcPort) );
		addListeners();
	}

	private void addListeners() {
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.print("UIstart > btnConnect");
				if (checkFields())
				{
					Client.theServerAddr = txtServerAddr.getText();
					Client.theServerRxcPort = Integer.parseInt(txtServerRxcPort.getText());
					Client.theServerTxcPort = Integer.parseInt(txtServerTxcPort.getText());
					System.out.println(":" + Client.theServerAddr + ":" + Client.theServerRxcPort +" ("+ Client.theServerTxcPort +")");

						Client.createSockets();
						Client.createIO();
						
						System.out.println("UIstart > btnConnect > socket info");
						System.out.println("mySocketRxs");
						SM.printSocketInfo(Client.mySocketRxs);
						System.out.println();
						System.out.println("mySocketTxs");
						SM.printSocketInfo(Client.mySocketTxs);
						
						isConnected = true;
						Client.myStatus = Client.valStatusConnection;
						
						lblInf.setText("Connected, echo(5s)...");
						System.out.println("Connected, echo(5s)...");
						if (Txs.echo())
						{
							lblInf.setText("Connected. ECHO Success");
							System.out.println("Connected. ECHO Success");
							isEchoOK = true;
						}
						else
						{
							lblInf.setText("echo failed but connected");
							System.out.println("echo failed but connected");
							isEchoOK = false;
						}
						btnConnect.setEnabled(false);
						btnSignup.setEnabled(true);
						btnLogin.setEnabled(true);
						
				}
				else
					lblInf.setText("check input fields");
			}
		});
		
		btnSignup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("UIstart > Signup pressed");
				Client.uiStart.setVisible(false);
				Client.showNew();
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("UIstart > Login pressed");
				Client.uiStart.setVisible(false);
				Client.showLogin();
			}
		});
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


	private boolean checkFields()
	{
		if (txtServerAddr.getText().trim().isEmpty())
			return false;
		if (0 == Integer.parseInt(txtServerRxcPort.getText()))
			return false;
		if (0 == Integer.parseInt(txtServerTxcPort.getText()))
			return false;
		return true;
	}
	
}
