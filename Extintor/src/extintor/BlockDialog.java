package extintor;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * Create a new block panel to a main frame
 * @author gbflores 
 * 
 */
public class BlockDialog {

	private static float opacity = 0.3f;
	private static Color color = Color.BLACK;
	private static ModalityType TYPE  = ModalityType.DOCUMENT_MODAL;
	
	private JDialog blockDialog;
	private JFrame mainFrame;
	private JFrame frame;
	private JDialog dialog;
	private WindowListener windowListener = new WindowAdapter() {

		public void windowOpened(WindowEvent e) {
			openBlackDialog();}
		public void windowClosing(WindowEvent e) {
			coloseBlackDialog();}
		public void windowClosed(WindowEvent e) {
			coloseBlackDialog();}
	};
	
	/**
	 * Create a new block panel to the main frame
	 * @param mainFrame 
	 * @param secondFrame
	 */
	public BlockDialog(JFrame mainFrame, JFrame secondFrame){
		this.mainFrame = mainFrame;
		frame = secondFrame;
		blockDialog = new JDialog(mainFrame, TYPE);
		frame.addWindowListener(windowListener);
	}

	/**
	 * Create a new block panel to the main frame
	 * @param mainFrame 
	 * @param secondFrame
	 */
	public BlockDialog(JFrame mainFrame, JDialog secondFrame){
		this.mainFrame = mainFrame;
		blockDialog = new JDialog(mainFrame, TYPE);
		dialog = secondFrame;
		dialog.addWindowListener(windowListener);
	}
	
	private void openBlackDialog(){
		blockDialog.setUndecorated(true);
		blockDialog.getContentPane().setBackground(color);
		blockDialog.setOpacity(opacity);
		blockDialog.setBounds(mainFrame.getContentPane().getBounds());
		blockDialog.setLocationRelativeTo(mainFrame.getContentPane());
		blockDialog.setVisible(true);
	}
	
	private void coloseBlackDialog(){
		blockDialog.setVisible(false);
		blockDialog.dispose();
	}

	/**
	 * @return the opacity
	 */
	public static float getOpacity() {
		return opacity;
	}
	/**
	 * @param opacity the opacity to set
	 * Default = 0.3f
	 */
	public static void setOpacity(float opacity) {
		BlockDialog.opacity = opacity;
	}

	/**
	 * @return the color
	 */
	public static Color getColor() {
		return color;
	}
	
	/**
	 * @param color the color to set
	 * Default = BLACK
	 */
	public static void setColor(Color color) {
		BlockDialog.color = color;
	}
}
