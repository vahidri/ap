package pkgvImageViewer;

/*
Pros:
	Handling almost all exceptions!
	
	Extra Features:
		Seamless Playing; Can Handle any input of the Buttons
			ReTime: Logical action when user Presses next/prev, Restart timer
			Shuffle/Normalize while playing!
			Add an album while playing(auto pause)
		Logical Order of Playing and Shuffle
		Shuffle, not Random!
		Showing the Place of Picture in Order
		Showing the File Name(ImageInfo)
		Pausing the Play, when Minimized
		Scaling the Image
		Naming Albums after their folder
		Rename Album by right clicking on it
		

*/


public class Test {	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
//		frame.setSize(972,600);
//		frame.setSize(810,500);
		frame.setSize(600,400);
		
		frame.setVisible(true);
	}

}
