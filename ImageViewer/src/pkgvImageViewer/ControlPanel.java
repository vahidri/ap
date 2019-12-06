package pkgvImageViewer;

//#done

import javax.swing.*;
//import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public final static String slash = File.separator;
	public final static String addr= "icons" + slash ;
//	public final static String addr= "C:/Users/vahid/Documents/workspace/vImageViewer/icons/" ;
	
	JButton btnNext;
	JButton btnPrevious;
	ToggleButton tglbtnShuffle;
	ToggleButton tglbtnPlayPause;
	ToggleButton tglbtnRepeat;

	public ControlPanel()
	{
		btnNext = new JButton();
		btnPrevious = new JButton();
		setTheButtons();
		this.add(tglbtnShuffle);
		this.add(btnPrevious);
		this.add(tglbtnPlayPause);
		this.add(btnNext);
		this.add(tglbtnRepeat);
		
		
		tglbtnPlayPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tglbtnPlayPause.changeState();
				System.out.println("ControlPanel>PnP pressed => " + tglbtnPlayPause.getState() + "  , isPlaying: " + Gallery.isPlaying);
				if (tglbtnPlayPause.getState()) //was paused
					if(Gallery.isRandom)
						Gallery.playShuffle();
					else
						Gallery.play();
				else
					Gallery.stop();
			}
		});
		
		tglbtnShuffle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tglbtnShuffle.changeState();
				System.out.println("ControlPanel>ShufflePressed => " + Gallery.isRandom);
				Gallery.isRandom = tglbtnShuffle.getState();
				if(Gallery.isRandom)
					Gallery.randomizeOrder();
				else
					Gallery.normalizeOrder();
				if(Gallery.isPlaying)
				{
					if(Gallery.isRandom) Gallery.currentImageNo =Gallery.order[0] ;
					Gallery.setPic();
					Gallery.retime();
				}
			}
		});
		tglbtnRepeat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tglbtnRepeat.changeState();
				System.out.println("ControlPanel>RepeatPressed => " + Gallery.isLoop);
				Gallery.isLoop = tglbtnRepeat.getState();
			}
		});

		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("ControlPanel>PreviousPressed");
				Gallery.showPrevious();
				if(Gallery.isPlaying) Gallery.retime();
			}
		} );
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("ControlPanel>NextPressed");
				Gallery.showNext();
				if(Gallery.isPlaying) Gallery.retime();
			}
		});

	}

	public void enableTheButtons()
	{
		System.out.println("ControlPanel>enableTheButtons");
		
		btnPrevious.setEnabled(true);
		btnNext.setEnabled(true);

		tglbtnShuffle.setEnabled(true);
		tglbtnPlayPause.setEnabled(true);
		tglbtnRepeat.setEnabled(true);
		printState();
	}
	
	private void setTheButtons()
	{
		System.out.println("ControlPanel>setTheButtons");
		try {
			tglbtnShuffle = new ToggleButton(false, new ImageIcon(ImageIO.read(new File(addr + "shfT.png"))) , new ImageIcon(ImageIO.read(new File(addr + "shfF.png"))));
			tglbtnPlayPause= new ToggleButton(false, new ImageIcon(ImageIO.read(new File(addr + "pau.png"))) , new ImageIcon(ImageIO.read(new File(addr + "ply.png"))));
			tglbtnRepeat = new ToggleButton(false, new ImageIcon(ImageIO.read(new File(addr + "rptT.png"))) , new ImageIcon(ImageIO.read(new File(addr + "rptF.png"))));

			tglbtnPlayPause.setDisabledIcon(new ImageIcon(ImageIO.read(new File(addr + "pnpD.png"))));
			tglbtnPlayPause.setToolTipText("Play/Pause");
			tglbtnPlayPause.setEnabled(false);
			tglbtnPlayPause.setState(false);
			
			btnPrevious.setIcon(new ImageIcon(ImageIO.read(new File(addr + "prv.png"))));
			btnPrevious.setDisabledIcon(new ImageIcon(ImageIO.read(new File(addr + "prvD.png"))));
			btnPrevious.setPressedIcon(new ImageIcon(ImageIO.read(new File(addr + "prvP.png"))));
			btnPrevious.setToolTipText("Previous");
			btnPrevious.setEnabled(false);
			
			btnNext.setIcon(new ImageIcon(ImageIO.read(new File(addr + "nxt.png"))));
			btnNext.setDisabledIcon(new ImageIcon(ImageIO.read(new File(addr + "nxtD.png"))));
			btnNext.setPressedIcon(new ImageIcon(ImageIO.read(new File(addr + "nxtP.png"))));
			btnNext.setToolTipText("Next");
			btnNext.setEnabled(false);
			
			tglbtnRepeat.setDisabledIcon(new ImageIcon(ImageIO.read(new File(addr + "rptD.png"))));
			tglbtnRepeat.setPressedIcon(new ImageIcon(ImageIO.read(new File(addr + "rptP.png"))));
			tglbtnRepeat.setToolTipText("Repeat");
			tglbtnRepeat.setEnabled(false);
			tglbtnRepeat.setState(false);
			
			tglbtnShuffle.setDisabledIcon(new ImageIcon(ImageIO.read(new File(addr + "shfD.png"))));
			tglbtnShuffle.setPressedIcon(new ImageIcon(ImageIO.read(new File(addr + "shfP.png"))));
			tglbtnShuffle.setToolTipText("Random");
			tglbtnShuffle.setEnabled(false);
			tglbtnShuffle.setState(false);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printState() ///debug
	{
		System.out.println("ControlPanel>printState");
		System.out.println("tglBtnShuffle " + tglbtnShuffle.getState());
		System.out.println("tglBtnPnP  " + tglbtnPlayPause.getState());
		System.out.println("tglBtnRepeat " + tglbtnRepeat.getState());
	}
	
}
