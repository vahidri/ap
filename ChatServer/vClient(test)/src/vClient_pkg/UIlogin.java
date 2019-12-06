package vClient_pkg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("deprecation")
public class UIlogin extends JFrame {
	private static final long serialVersionUID = 1L;

	//north
	JPanel pnlNorth = new JPanel();
	JLabel lblInf = new JLabel("Enter Username and Password");
	
	//west
	JPanel pnlWest = new JPanel();
	JLabel lblU = new JLabel("ID");
	JLabel lblP = new JLabel("Pass");

	//center
	JPanel pnlCenter = new JPanel();
	JTextField txtUser = new JTextField();
	JPasswordField tpfPass = new JPasswordField();
	
	//east
	JPanel pnlEast= new JPanel();
	
	//south
	JPanel pnlSouth = new JPanel();
	JButton btnLogin = new JButton("Login");

	public UIlogin(){
		System.out.println("UIlogin>Constructor");
		this.setLayout(new BorderLayout());
		this.setTitle("Login");
		
		pnlNorth.add(lblInf);
		this.add(pnlNorth, BorderLayout.NORTH);
		
		pnlWest.setLayout(new GridLayout(2, 1));
		pnlWest.add(lblU);
		pnlWest.add(lblP);
		this.add(pnlWest, BorderLayout.WEST);
		

		pnlCenter.setLayout(new GridLayout(2, 1));
		pnlCenter.add(txtUser);
		pnlCenter.add(tpfPass);
		this.add(pnlCenter, BorderLayout.CENTER);

		pnlSouth.add(btnLogin);
		this.add(pnlSouth, BorderLayout.SOUTH);

		addListeners();
	}

	private void addListeners() {
		System.out.println("UIlogin>addListeners");

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch (checkLoginFields() )
				{
				case 0: //good to send
					System.err.println("U: " + txtUser.getText().trim());
					System.err.println("P: " + tpfPass.getText());
					String resp = Txs.login(txtUser.getText(), tpfPass.getText() );
					System.out.println("UIlogin > Login answered: " + resp);
					switch(resp)
					{
					case Client.valAnsPass:
						lblInf.setText("Success");
						Client.myUsername = txtUser.getText();
						Client.myPass = tpfPass.getText();
						Client.myStatus = Client.valStatusIn;
						UImsg.timer.scheduleAtFixedRate(UImsg.timerTask, 5000, 5000);
						Client.uiLogin.setVisible(false);
						Client.showMsg();
//						Client.showFile();
						break;
					case Client.valAnsAlreadyOn:
						lblInf.setText("Account in Use");
						break;
					case Client.valAnsFail:
						lblInf.setText("Err; check User and Pass"); 
						break;
					case Client.valAnsUnknown:
						lblInf.setText("unknwn: This isn't working");
						break;
					default:
						lblInf.setText(resp);
						break;
					}
					break;
				case -100:
					lblInf.setText("check server connection");
					break;
				case 3:
					lblInf.setText("username doesn't Exist");
					break;
				case -1:
					lblInf.setText("empty field");
					break;
				default:
					lblInf.setText("something is wrong");
					break;
				}
			}
		});

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private int checkLoginFields()
	{
//		System.err.println(txtUser.getText().trim());
//		System.err.println(tpfPass.getText());
		if (txtUser.getText().trim().isEmpty() || tpfPass.getText().isEmpty())
			return -1;
		try {
			if (Txs.usercheck(txtUser.getText()))
				return 3;
		} catch (Exception e) {
			return -100;
		}
		return 0;
	}
	
}