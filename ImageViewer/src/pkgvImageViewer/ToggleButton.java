package pkgvImageViewer;

//#done

import javax.swing.*;
//import java.awt.event.*;

public class ToggleButton extends JButton {
	private static final long serialVersionUID = 1L;
	private boolean state = false;
	ImageIcon picTrue, picFalse;

	public ToggleButton(boolean inpState, ImageIcon inpPicTrue , ImageIcon inpPicFalse)
	{
		System.out.println("ToggleButton>Constructor");
		picTrue = inpPicTrue;
		picFalse = inpPicFalse;
		
		state = inpState;
		if (inpState)
			this.setIcon(inpPicTrue);
		else
			this.setIcon(inpPicFalse);

		/*
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeState();
			}
		});
		*/
		
	}
	
	public boolean getState()
	{
		return state;
	}
	
	public void setState(boolean inpState)
	{
		state = inpState;
		if(inpState)
			setIcon(picTrue);
		else
			setIcon(picFalse);
	}
	
	public void changeState()
	{
		state=!state;
		setState(state);
	}
}
