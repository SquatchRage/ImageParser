
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class Frame extends JFrame implements ActionListener 
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
 Object urlToken;
 Container cp;
 String k;
 
 @SuppressWarnings("unchecked")
public Frame(){
	 
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

	 
	 model = new DefaultListModel<Object>();
	 //for (int i = 0; i < 15; i)
	   //   model.addElement("THIS IS MY ELEMENT, THERE ARE MANY LIKE IT "  i);
	 
	 actionPanel = new JPanel(new FlowLayout());
	 listPanel = new JPanel(new FlowLayout());
	 
	 url_List = new JList(model);
	 url_List.setFixedCellWidth(700);
	 url_List.setFixedCellHeight(45);
	 JScrollPane sp = new JScrollPane(url_List);
	 //-----------------------------------------------
	 
	 	
	 
	 // on double mouse click, pop open dialog with the selected image in it. 
	 url_List.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	
		        	String clickedOnURL = MyParserCallbackTagHandler.passable_URL;
		        	
		        	try {
						DialogImage.initUI(clickedOnURL);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	System.out.println(clickedOnURL + "\n");
		         
		            int index = list.locationToIndex(evt.getPoint());
		            System.out.println("I am index" + index);
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
		 
		model.removeAllElements(); // If user input another URL and selects go, it clears the list before re-populating with new URLS
		tags = new FindTags (urlField.getText().trim(), model);
	 }
	 
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