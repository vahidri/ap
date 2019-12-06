package pkgvImageViewer;

import java.awt.GridLayout;

//#done

//import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

public class SettingPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	JLabel lblCaptionGeneral;
	JLabel lblCaptionSpeed;
	JRadioButton radioSpeedSlow;
	JRadioButton radioSpeedNormal;
	JRadioButton radioSpeedFast;
	JRadioButton radioSpeedCustom;
	ButtonGroup radioSpeeds;
	JCheckBox chkScaled = new JCheckBox("Scale the Image");
//	JLabel lblCurrentImage= new JLabel("ImageNo");
	
	
	public SettingPanel(){
		this.setLayout(new GridLayout(5, 1));
		chkScaled.setSelected(true);
		lblCaptionGeneral= new JLabel("Settings");
		lblCaptionSpeed= new JLabel("speed:");
		radioSpeedSlow = new JRadioButton("Slow"); //5
		radioSpeedNormal = new JRadioButton("Normal"); //3
		radioSpeedFast = new JRadioButton("Fast"); //1
		radioSpeedCustom = new JRadioButton("Custom");
		radioSpeeds = new ButtonGroup();
		
		radioSpeeds.add(radioSpeedSlow);
		radioSpeeds.add(radioSpeedNormal);
		radioSpeeds.add(radioSpeedFast);
		radioSpeeds.add(radioSpeedCustom);
	
		JPanel pnlSpeed = new JPanel();
		pnlSpeed.setLayout(new BoxLayout(pnlSpeed, BoxLayout.Y_AXIS));
		pnlSpeed.setToolTipText("Set the Speed of Viewing Pictures");

		this.add(lblCaptionGeneral);
		pnlSpeed.add(lblCaptionSpeed);
		pnlSpeed.add(radioSpeedSlow);
		pnlSpeed.add(radioSpeedNormal);
		pnlSpeed.add(radioSpeedFast);
		pnlSpeed.add(radioSpeedCustom);		
		
		this.add(pnlSpeed);

		this.add(chkScaled);
		
		radioSpeedSlow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setCurrentSpeed(Gallery.speedSlow);
			}
		});
		radioSpeedNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setCurrentSpeed(Gallery.speedNormal);
			}
		});
		radioSpeedFast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setCurrentSpeed(Gallery.speedFast);
			}
		});
		radioSpeedCustom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double in = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter speed in seconds", "Custom Speed" , JOptionPane.QUESTION_MESSAGE ,null, null, Gallery.speedNormal/1000).toString() );
				if(0<in)
					switch((int)(in*1000))
					{
					case Gallery.speedFast:
						radioSpeedFast.doClick();
						break;
					case Gallery.speedNormal:
						radioSpeedNormal.doClick();
						break;
					case Gallery.speedSlow:
						radioSpeedSlow.doClick();
						break;
					default:
						setCurrentSpeed((int)(in*1000));
						break;
					}
				else
					switch(Gallery.currentSpeed)
					{
					case Gallery.speedSlow:
						radioSpeedSlow.doClick();
						break;
					case Gallery.speedNormal:
						radioSpeedNormal.doClick();
						break;
					case Gallery.speedFast:
						radioSpeedFast.doClick();
						break;
					}
			}
		});
		
		radioSpeedFast.doClick();

	}

	public void setCurrentSpeed(int inpSpeed)
	{
		System.out.println("SettingPanel>setCurrentSpeed: " + inpSpeed);
		Gallery.currentSpeed = inpSpeed;
		if(Gallery.isPlaying) Gallery.retime();
	}

}
