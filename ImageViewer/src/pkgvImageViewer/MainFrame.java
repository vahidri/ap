package pkgvImageViewer;

//Handling New Album

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
//import java.util.Vector;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	static final String appName = "vImage Viewer";

	static JFileChooser jfc = new JFileChooser();
	static JLabel picBox = new JLabel();

	JMenuBar myMenu= new JMenuBar();
	JMenu mnuFile= new JMenu("File");
		JMenuItem mnuItmFileNew = new JMenuItem("New");
		JMenuItem mnuItmFileExit = new JMenuItem("Exit");
	JMenu mnuHelp = new JMenu("Help");
		JMenuItem mnuItmHelpViewHelp = new JMenuItem("View Help");
		JMenuItem mnuItmHelpAbout = new JMenuItem("About");

	static AlbumPanel albumPanel = new AlbumPanel();
	static ControlPanel controlPanel = new ControlPanel();
	static SettingPanel settingPanel = new SettingPanel();
	
	public MainFrame()
	{
		this.setIconImage(new ImageIcon(ControlPanel.addr + "frame.png").getImage());
		this.setTitle(appName);
		myMenu.add(mnuFile);
			mnuFile.add(mnuItmFileNew);
			mnuFile.add(mnuItmFileExit);
		myMenu.add(mnuHelp);
			mnuHelp.add(mnuItmHelpViewHelp);
			mnuHelp.add(mnuItmHelpAbout);
		this.setJMenuBar(myMenu);
		
		this.setLayout(new BorderLayout());
		
//		might need to move this to Test
		this.add(controlPanel, BorderLayout.SOUTH);
		this.add(albumPanel, BorderLayout.WEST);
		this.add(picBox, BorderLayout.CENTER);
		this.add(settingPanel, BorderLayout.EAST);
		settingPanel.add(Gallery.lblImagePos);
		settingPanel.add(Gallery.lblImageInfo);
		
		Gallery.tglBtnPnP = controlPanel.tglbtnPlayPause;
		
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent arg0) {
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
				if(Gallery.isPlaying) Gallery.stop();
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
		mnuItmHelpViewHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = "\nSpeeds(seconds):\nSlow: " + Gallery.speedSlow/1000 +"\nNormal: " + Gallery.speedNormal/1000 + "\nFast: " + Gallery.speedFast/1000; 
				JOptionPane.showMessageDialog (null , "Right click on an Album to Rename it\n" + str, "Help: vImage Viewer", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnuItmHelpAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog (null , "By Vahid Ramazani", "About: vImage Viewer", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnuItmFileNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("MainFrame>FileNew");
				if(Gallery.isPlaying) Gallery.stop();
				makeAlbum();
			}
		});
		mnuItmFileExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
	}
	
	void makeAlbum(){
		System.out.println("MainFrame>makeAlbum:");
    	jfc.setCurrentDirectory(new File("."));
	    jfc.setDialogTitle("select the Directory containing the Pictures");
	    jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    jfc.setAcceptAllFileFilterUsed(false);
	    if(jfc.showDialog(null, "Select")==JFileChooser.APPROVE_OPTION){
			File [] myFileArray = jfc.getSelectedFile().listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".bmp") || name.endsWith(".png") || name.endsWith(".JPG") || name.endsWith(".JPEG") || name.endsWith(".BMP") || name.endsWith(".PNG") )
						return true;
					return false;
				}
			});
			
			if (0!= myFileArray.length)
			{
				//create a Gallery, and a button in AlbumPanel
				System.out.println("MainFrame>+File Count: " + myFileArray.length);
				makeGallery(myFileArray);

			}
			else
				JOptionPane.showMessageDialog (null , "No pictures(jpg,png,bmp) in selected directory; Please try another one.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
    }
	
	void makeGallery(File [] inpFiles){
		System.out.println("MainFrame>+makeGallery");
		Gallery aTmpGallery = new Gallery();
		aTmpGallery.myPics = new BufferedImage [inpFiles.length];
		aTmpGallery.setPicsList(inpFiles);
		Gallery.gals.addElement(aTmpGallery);
		System.out.println("MainFrame>++Pic Count: " + aTmpGallery.myPics.length);
		System.out.println("MainFrame>+Gallery Count: " + Gallery.gals.size());
		
		Gallery.setCurrentAlbum(Gallery.gals.size()-1);
		albumPanel.addAlbum(Gallery.gals.size()-1);
		albumPanel.albums.lastElement().setSelected(true);
		if(!controlPanel.btnNext.isEnabled()) //connoting: first time of adding an album
		{
			controlPanel.enableTheButtons();
		}
	}
	
}