
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.undo.UndoManager;

public class Frame extends JFrame implements ActionListener, Printable 
{

	private static final long serialVersionUID = 8417262671424751407L;
 JButton goButton;
 JButton printButton;
 JTextField urlField;
 JLabel	urlFieldLabel;
 JList  url_List;
 JPanel actionPanel;
 JPanel listPanel;
 DefaultListModel model;
 FindTags tags;
 MyParserCallbackTagHandler image_url;
 DialogImage di;
 String url_To_Load;
 Container cp;
 int count = 0;
 
 //---------------------TEST WEBISTE------------------------------------
 //https://www.fairmontstate.edu/collegeofscitech/about-us/faculty-staff
 //---------------------------------------------------------------------
 
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
    // urlField.setText("Hey Ted!");
     //Set focus to txtfield
     addWindowListener( new WindowAdapter() {
    	    public void windowOpened( WindowEvent e ){
    	        urlField.requestFocus();
    	    }
    	}); 
     
	 model = new DefaultListModel();
	 //for (int i = 0; i < 15; i)
	   //   model.addElement("THIS IS MY ELEMENT, THERE ARE MANY LIKE IT "  i);
	 
	 actionPanel = new JPanel(new FlowLayout());
	 listPanel = new JPanel(new FlowLayout());
	 url_List = new JList(model);
	 JScrollPane sp = new JScrollPane(url_List);
	 
	 //-----------------------------------------------

	 // on double mouse click, pop open dialog with the selected image in it. 
	 //If there are 2 left mouse clicks, call url from taghandler and use it with a DialogImage class arguement to make connection
	 url_List.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
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
			
	 rightClick( urlField); 
	 
	 listPanel.add(sp);
	 
	 actionPanel.add(urlFieldLabel);
	 actionPanel.add(urlField);
	 actionPanel.add(goButton);
	 actionPanel.add(printButton);
	 
	 cp = getContentPane();
	 cp.setSize(900, 900);

	 cp.add(listPanel, BorderLayout.NORTH);
	 cp.add(sp, BorderLayout.CENTER);
	 cp.add(actionPanel, BorderLayout.SOUTH);
	 
     urlField.requestFocusInWindow();

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
 
 private int linesPerPage = 45;


 public int print(Graphics g, PageFormat pf, int pageIndex) {
   if (pageIndex * linesPerPage >= model.getSize())
     return NO_SUCH_PAGE;
   Graphics2D g2 = (Graphics2D) g;
   g2.setFont(new Font("Serif", Font.PLAIN, 16));
   g2.setPaint(Color.black);
   int x = 100;
   int y = 100;
   for (int i = linesPerPage * pageIndex; i < model.getSize()
       && i < linesPerPage * (pageIndex + 1); i++) {
     g2.drawString(( model).get(i).toString(), x, y);
     y += 45;
   }
   return PAGE_EXISTS;
 }

// This method just gives you the Right Click functionality instead of having to use control [Button] options
 public static void rightClick(JTextField txtField) 
 {
     JPopupMenu popup = new JPopupMenu();
     UndoManager undoManager = new UndoManager();
     txtField.getDocument().addUndoableEditListener(undoManager);

     Action undoAction = new AbstractAction("Undo") {
         @Override
         public void actionPerformed(ActionEvent ae) {
             if (undoManager.canUndo()) {
                 undoManager.undo();
             }
             else {
                 JOptionPane.showMessageDialog(null,
                         "Undoable: " + undoManager.canUndo() ,
                         "Undo Status", 
                         JOptionPane.INFORMATION_MESSAGE);
             }
         }
     };

    Action copyAction = new AbstractAction("Copy") {
         @Override
         public void actionPerformed(ActionEvent ae) {
             txtField.copy();
         }
     };

     Action cutAction = new AbstractAction("Cut") {
         @Override
         public void actionPerformed(ActionEvent ae) {
             txtField.cut();
         }
     };

     Action pasteAction = new AbstractAction("Paste") {
         @Override
         public void actionPerformed(ActionEvent ae) {
             txtField.paste();
         }
     };

     Action selectAllAction = new AbstractAction("Select All") {
         @Override
         public void actionPerformed(ActionEvent ae) {
             txtField.selectAll();
         }
     };

     popup.add (undoAction);
     popup.addSeparator();
     popup.add (cutAction);
     popup.add (copyAction);
     popup.add (pasteAction);
     popup.addSeparator();
     popup.add (selectAllAction);

    txtField.setComponentPopupMenu(popup);
 }

 
 void setUp ()
 {
     Toolkit tk;
     Dimension d;
     
     setDefaultCloseOperation (EXIT_ON_CLOSE);
     
     tk = Toolkit.getDefaultToolkit ();
     d = tk.getScreenSize ();
     
     setSize (d.width/2, d.height/2);
     setLocation (d.width/4, d.height/4);
     setTitle ("Image Finder");
     setVisible (true);
 	}
 
 

}