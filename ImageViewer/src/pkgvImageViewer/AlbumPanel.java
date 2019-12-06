package pkgvImageViewer;

//#done

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.*;

public class AlbumPanel extends JPanel {
	private static final long serialVersionUID = 1L;
//	public static JRadioButton [] albums;
	static Vector<JRadioButton> albums = new Vector<JRadioButton>();
	static ButtonGroup albumsButtonGroup = new ButtonGroup();

	//album count: Gallery.numOfAlbums
	
	public AlbumPanel(){
		this.setLayout(new GridLayout(10,1));
	}
	
	public void addAlbum(int ind)
	{
		System.out.println("AlbumPanel>addAlbum");
//		int ind= inpGallery.myIndex;
//		albums[ind].setText(inpGallery.myTitle);
		albums.addElement(new JRadioButton());
		if(albums.indexOf(albums.lastElement()) != ind )
			System.out.println("Logic Error: addAlbum: albums.indexOf(albums.lastElement()) != ind ");
		albumsButtonGroup.add(albums.lastElement());
		this.add(albums.lastElement());
		albums.elementAt(ind).setText((ind+1) + ". " + Gallery.gals.elementAt(ind).myFiles[0].getParent().substring( Gallery.gals.elementAt(ind).myFiles[0].getParent().lastIndexOf(ControlPanel.slash)+1 ) );
		albums.elementAt(ind).addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getButton() == MouseEvent.BUTTON3) //right click
				{
//					MainFrame.controlPanel.printState(); ///debug
					String nuname= JOptionPane.showInputDialog(null, (Object)"Enter the new name for \"" + albums.elementAt(ind).getText() + "\"", "Album Rename" , JOptionPane.QUESTION_MESSAGE ,null, null, albums.elementAt(ind).getText()).toString();
					if(nuname != null)
						albums.elementAt(ind).setText(nuname);
				}
				else if (arg0.getButton() == MouseEvent.BUTTON1) //left click
				{
					Gallery.setCurrentAlbum(ind);
				}
			}
		});
		this.revalidate();
		this.repaint();
	}
}
