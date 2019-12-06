package pkgvImageViewer;
/*playing
	timer
	random
	loop
	random + loop (is an easy concept!)
	next n prev
	
*/

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

public class Gallery {
	final static int speedSlow = 5000;
	final static int speedNormal = 3000;
	final static int speedFast= 1000;

	public static JLabel lblImagePos = new JLabel("ImagePos");
	public static JLabel lblImageInfo= new JLabel("ImageInfo");
	public static ToggleButton tglBtnPnP; //a pointer to the controlPanel's Pnp Btn (for stopping when isLoop == false)
	
	
	static Timer timer;
	static TimerTask timerTask;
	
	static Vector<Gallery> gals = new Vector<Gallery>();
	static int currentAlbumNo = -1; //starting from 0 (array like)
	static int currentImageNo=-1;
	static int currentSpeed = speedFast;
	static boolean isPlaying = MainFrame.controlPanel.tglbtnPlayPause.getState();
	static boolean isRandom =  MainFrame.controlPanel.tglbtnShuffle.getState();
	static boolean isLoop =  MainFrame.controlPanel.tglbtnRepeat.getState();
	static int [] order;
	
	File [] myFiles;
	BufferedImage [] myPics;
	
	public Gallery()
	{
		renewTimerAndTask();
		System.out.println("Gallery>Constructor, size of gal [was]: " + gals.size());
	}
	
	public void setPicsList(File [] inpFiles)
	{
		System.out.println("Gallery>setPics");
		myFiles = new File[inpFiles.length];
		myPics = new BufferedImage[inpFiles.length];
		for(int i=0; i<inpFiles.length ; i++)
			try {
				myFiles[i] = inpFiles[i];
				myPics[i] = ImageIO.read(inpFiles[i]);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}
	
//Motion Parts!
	public static void play()
	{
		System.out.println("Gallery>Play:NormalOrder");
		if(isPlaying) System.out.println("LogicError@Gallery: What?! AlreadyPlaying?");
		isPlaying = true;
		isRandom = false;
		normalizeOrder();
		renewTimerAndTask();
		timer.schedule(timerTask, currentSpeed, currentSpeed);
	}
	
	static void playShuffle()
	{
		System.out.println("Gallery>Play:ShuffleOrder");
		if(isPlaying) System.out.println("LogicError@Gallery: What?! AlreadyPlaying?");
		isPlaying=true;
		if(!isRandom) System.out.println("LogicError@Gallery: What?! isRandom==false");
		isRandom=true;
		randomizeOrder();
		renewTimerAndTask();
		currentImageNo = order[0];
		timer.scheduleAtFixedRate(timerTask, currentSpeed, currentSpeed);
	}
	
	static void stop()
	{
		System.out.println("Gallery>Stop");
		isPlaying = false;
		timer.cancel();
		timer.purge();
		tglBtnPnP.setState(false);
	}
	
	static void showNext()
	{
//		System.out.println("Gallery>ShowNext()");
		if( findInOrder(currentImageNo) == order.length-1 ) //the last pic in order
		{
			System.out.println("Gallery>ShowNext => Last Pic in Order");
			if(isPlaying)
			{
					if(!isLoop)
						stop();
					else
						if(isRandom)
							randomizeOrder();
						else
							normalizeOrder();
					
					currentImageNo = order[0];
			}
			else
				currentImageNo=0;
		}
		else
		{
			if(isPlaying)
				currentImageNo = order[1 + findInOrder(currentImageNo)];
			else
				currentImageNo++;
		}
		setPic();
	}
	
	public static void showPrevious()
	{
//		System.out.println("Gallery>ShowPrevious()");
		if( findInOrder(currentImageNo) == 0 ) //the first pic in order
		{
			System.out.println("Gallery>ShowPrevious => First Pic in Order");
			if(isPlaying)
				currentImageNo = order[order.length-1];
			else
				currentImageNo= order.length-1;
		}
		else
		{
			if(isPlaying)
				currentImageNo = order[findInOrder(currentImageNo) - 1];
			else
				currentImageNo--;
		}
		setPic();
	}
	
	public static void setCurrentAlbum(int inpAlbumNo)
	{
		System.out.println("Gallery>setCurrentAlbum");
		currentAlbumNo = inpAlbumNo;
		normalizeOrder(); //intentionally; by default, make it normalized.
		currentImageNo = 0;
		setPic();
	
		//If changed while playing, retime(it's logical!), Plus if isPlaying && isRandom, RANDOMIZE!  
		if(isPlaying)
		{
			retime();
			if(isRandom)
				randomizeOrder();
			currentImageNo = 0;
			setPic();
		}
	}
	
	
//setting pic
	public static void setPic(){
		BufferedImage tmpImg = gals.elementAt(currentAlbumNo).myPics[currentImageNo];
		if(MainFrame.settingPanel.chkScaled.isSelected())
		{
			Image tmpImgScaled = tmpImg.getScaledInstance(MainFrame.picBox.getWidth(), MainFrame.picBox.getHeight(), Image.SCALE_SMOOTH);
			MainFrame.picBox.setIcon(new ImageIcon(tmpImgScaled));	
		}
		else
			MainFrame.picBox.setIcon(new ImageIcon(tmpImg));
		
		lblImagePos.setText( ( findInOrder(currentImageNo) +1) + "["+(currentImageNo+1)+"] / " + order.length );
		lblImageInfo.setText(gals.elementAt(currentAlbumNo).myFiles[currentImageNo].getName());
		MainFrame.picBox.setToolTipText(gals.elementAt(currentAlbumNo).myFiles[currentImageNo].getAbsolutePath());
	}
	
//Order Functions
	static void normalizeOrder() {
		System.out.println("Gallery>normalizeOrder");
		order = new int[gals.elementAt(currentAlbumNo).myPics.length];
		for(int i=0;i<order.length;i++)
			order[i]=i;
	}
	
	static void randomizeOrder(){ //interesting way of making a random array! (human like!!!)
		System.out.println("Gallery>randomizeOrder");
		order = new int[gals.elementAt(currentAlbumNo).myPics.length];
		for(int i=0;i<order.length;i++)
			order[i]=-1;
		int c=0;
		int justChecking = 0;
		while(c<order.length)
		{
			justChecking++;
			int rnd = (int)(Math.random()*order.length);
			if(order[rnd] == -1){
				order[rnd] = c;
				c++;
			}
		}
		System.out.println("randomized" + order.length + "pieces of data, in " + justChecking + " steps");
	}
	
	static int findInOrder(int inpNo)
	{
		int i;
		for(i = 0; i<order.length ; i++)
			if(order[i] == inpNo)
				break;
		if(i == order.length) //error: not found
			return -1;
		return i;			
	}
	

	public static void retime() //sets the timer to start from time=0, not present time
	{
		System.out.println("Gallery>retime");
		timer.cancel();
		timer.purge();
		renewTimerAndTask();
		timer.schedule(timerTask, currentSpeed, currentSpeed);
	}

	public static void renewTimerAndTask()
	{
		timer = new Timer();
		 timerTask = new TimerTask() {
				@Override
				public void run() {
					System.out.print("Gallery>timerTask(album: " + currentAlbumNo + "[" + (currentAlbumNo+1) + "] , img: " + (currentImageNo+1) + "[" + currentImageNo+ "] -> " );
					showNext();
					System.out.print((currentImageNo+1) + "[" + currentImageNo + "] )\n");
				}
			};
	}

}
