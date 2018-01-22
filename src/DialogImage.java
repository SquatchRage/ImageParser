import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class DialogImage {
	
	MyParserCallbackTagHandler tag;

	public void addImage(String IMAGE_URL){
		tag = new MyParserCallbackTagHandler(IMAGE_URL, null);
	   try
       {
           JDialog dialog = new JDialog();     
           dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
           dialog.setTitle("Image Loading Demo");

          // dialog.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(tag.imageURL.toString())))));
           dialog.add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResourceAsStream(IMAGE_URL)))));


           dialog.pack();
           dialog.setLocationByPlatform(true);
           dialog.setVisible(true);
       } 
       catch (IOException e) 
       {
           e.printStackTrace();
       }
	
	}
}
