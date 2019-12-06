package vClient_pkg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UInew extends JFrame {
	private static final long serialVersionUID = 1L;

	//north
	JPanel pnlNorth = new JPanel();
	JLabel lblInf = new JLabel("Info");
	
	//west
	JPanel pnlWest = new JPanel();
	JLabel lblU = new JLabel("ID");
	JLabel lblP = new JLabel("Password");
	JLabel lblPC = new JLabel("Confirm Pass");

	//center
	JPanel pnlCenter = new JPanel();
	JTextField txtUser = new JTextField();
	JLabel lblUserCk = new JLabel("Username check");
	JPasswordField tpfPass = new JPasswordField();
	JPasswordField tpfPassConf = new JPasswordField();
	
	//east
	
	//south
	JButton btnGo = new JButton("SignUp then Login");

	public UInew(){
		System.out.println("UInew>Constructor");
		this.setTitle("Register");
		this.setLayout(new BorderLayout());
		pnlNorth.add(lblInf);
		this.add(pnlNorth, BorderLayout.NORTH);
		
		pnlWest.setLayout(new GridLayout(3, 1));
		pnlWest.add(lblU);
		pnlWest.add(lblP);
		pnlWest.add(lblPC);
		this.add(pnlWest, BorderLayout.WEST);
		

		pnlCenter.setLayout(new GridLayout(4, 1));
		pnlCenter.add(txtUser);
		pnlCenter.add(lblUserCk);
		pnlCenter.add(tpfPass);
		pnlCenter.add(tpfPassConf);
		this.add(pnlCenter, BorderLayout.CENTER);

		this.add(btnGo, BorderLayout.SOUTH);
		addListeners();
	}

	private void addListeners() {
		
		txtUser.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent arg0) {
				try {
					if (Comm.usercheck(txtUser.getText()))
						lblUserCk.setText("Available");
					else
						lblUserCk.setText("username is Taken");
				} catch (Exception e) {
					System.out.println("UInew > usrck failed; check server connection");
					lblUserCk.setText(" check server connection");
				}
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		
		btnGo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int cf = checkFields();
				lblInf.setText(String.valueOf(cf) );
				switch(cf)
				{
				case 0:
					lblInf.setText("if XOR else");
					if( Comm.newuser(txtUser.getText(), tpfPass.getText()) )
					{
						lblInf.setText("Logging in...");
						String inAns = Comm.login(txtUser.getText(), tpfPass.getText());
						switch(inAns)
						{
						case "pass":
							Comm.myUsername = txtUser.getText();
							Test.myUInew.setVisible(false);
							Test.showMsg();
							//#Test.myUImsg. //introducing the user to UImsg	
							break;
						default:
							lblInf.setText(inAns + ": something is wrong");
							break;
						}

					}
					else
					{
						lblInf.setText("attmept 'new' Failed");
					}
					break;
				case -100:
					lblInf.setText("check Server Connection");
					break;
				case -1:
					lblInf.setText("empty field");					
					break;
				case 2:
					lblInf.setText("pass and confirm don't match");
					break;
				case 22:
					lblInf.setText("username is taken");
					break;
				default:
					lblInf.setText("something is wrong");
				}
			}
		});
		
		
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
	
	private int checkFields()
	{
		if (txtUser.getText().isEmpty() || tpfPass.getText().isEmpty() || tpfPassConf.getText().isEmpty())
			return -1;
/*
		System.out.println("P: " + tpfPass.getText());
		System.out.println("PC:" + tpfPassConf.getText());
		System.out.println(tpfPass.getText().length());
		System.out.println(tpfPassConf.getText().length());
		
		System.out.println("P: " + tpfPass.getText());
		System.out.println("PC:" + tpfPassConf.getText());
		System.out.println(tpfPass.getPassword().length);
		System.out.println(tpfPassConf.getPassword().length);
*/		
		if (!tpfPass.getText().equals(tpfPassConf.getText()))
			return 2;
		
		try {
			if (! Comm.usercheck(txtUser.getText()))
				return 22;			
		} catch (Exception e) {
			return -100;
		}
		return 0;
	}

}
