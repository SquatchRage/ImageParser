
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class Frame extends JFrame implements ActionListener, Printable 
{

	private static final long serialVersionUID = 8417262671424751407L;
 JButton goButton;
 JButton printButton;
 JTextField urlField;
 JLabel	urlFieldLabel;
 JList<Object>  url_List;
 JPanel actionPanel;
 JPanel listPanel;
 DefaultListModel model;
 FindTags tags;
 MyParserCallbackTagHandler image_url;
 DialogImage di;
 Print p;
 String url_To_Load;
 Container cp;
 
 //https://www.fairmontstate.edu/collegeofscitech/about-us/faculty-staff
 
 @SuppressWarnings("unchecked")
public Frame(){
	 //creation of buttons, labels, textfields, lists and panels. 
	 goButton = new JButton("Go");
	 goButton.addActionListener(this);
	 goButton.setActionCommand("Go");
	 
	 printButton = new JButton("Print");
	 printButton.addActionListener(this);
	 printButton.setActionCommand("Print");
	 
	 urlFieldLabel = new JLabel("URL:");
	 
	 urlField = new JTextField(30);
	 urlField.setEditable(true);
     urlField.setPreferredSize( new Dimension( 200, 24 ) );

	 
	 model = new DefaultListModel();
	 //for (int i = 0; i < 15; i)
	   //   model.addElement("THIS IS MY ELEMENT, THERE ARE MANY LIKE IT "  i);
	 
	 actionPanel = new JPanel(new FlowLayout());
	 listPanel = new JPanel(new FlowLayout());
	 
	 url_List = new JList<Object>(model);
	 url_List.setFixedCellWidth(700);
	 url_List.setFixedCellHeight(45);
	 JScrollPane sp = new JScrollPane(url_List);
	 
	 //-----------------------------------------------

	 // on double mouse click, pop open dialog with the selected image in it. 
	 //If there are 2 left mouse clicks, call url from taghandler and use it with a DialogImage class arguement to make connection
	 url_List.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList<?> list = (JList<?>)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	
		        	 url_To_Load = (String) url_List.getSelectedValue();
		        	
		        	try {
						DialogImage.initUI(url_To_Load);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	System.out.println("\n" + "I am the clicked on URL " + url_To_Load + "\n");
		         
		            int index = list.locationToIndex(evt.getPoint());
		            System.out.println("I am index " + index);
		        } 
		    }
		});
			
	 
	 
	 listPanel.add(sp);
	 
	 actionPanel.add(urlFieldLabel);
	 actionPanel.add(urlField);
	 actionPanel.add(goButton);
	 actionPanel.add(printButton);

	 cp = getContentPane();
	 cp.add(listPanel, BorderLayout.NORTH);
	 cp.add(actionPanel, BorderLayout.SOUTH);
	 
	 
	 setUp();
 }
 
 @Override
 public void actionPerformed(ActionEvent AE) 
 {     
	 if(AE.getActionCommand().equals("Go")){
		 
		model.removeAllElements(); // If user selects go, it clears the list before re-populating with new URLS
		tags = new FindTags (urlField.getText().trim(), model);
	 }
	 
	 if(AE.getActionCommand().equals("Print")){
		 
		 PrinterJob job = PrinterJob.getPrinterJob();
		 job.setPrintable(this);
		 boolean ok = job.printDialog();
		 if (ok) {
		 try {
		 job.print();
		 } catch (PrinterException ex) {
			 System.out.println("error");	}}}	 
			 
	 
 }
 
 public int print(Graphics g, PageFormat pf, int page) throws
 PrinterException {

if (page > 0) { /* We have only one page, and 'page' is zero-based */
return NO_SUCH_PAGE;
}

/* User (0,0) is typically outside the imageable area, so we must
* translate by the X and Y values in the PageFormat to avoid clipping
*/
Graphics2D g2d = (Graphics2D)g;
g2d.translate(pf.getImageableX(), pf.getImageableY());

/* Now print the window and its visible contents */
url_List.printAll(g);

/* tell the caller that this page is part of the printed document */
return PAGE_EXISTS;
}






 
 void setUp ()
 {
     Toolkit tk;
     Dimension d;
     
     setDefaultCloseOperation (EXIT_ON_CLOSE);
     
     tk = Toolkit.getDefaultToolkit ();
     d = tk.getScreenSize ();
     
     setSize (d.width/3, d.height/3);
     setLocation (d.width/4, d.height/4);
     setTitle ("Image Finder");
     setVisible (true);
 	}
 
 

}