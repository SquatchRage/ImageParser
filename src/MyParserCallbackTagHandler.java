

import javax.swing.DefaultListModel;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;


class MyParserCallbackTagHandler extends HTMLEditorKit.ParserCallback
{
    String baseDomain;
    Object imageURL;
    DefaultListModel<String> listPointer;
  static  String passable_URL;
    
    
    public MyParserCallbackTagHandler (String baseDomain, DefaultListModel<String> listPointer)
    {
     this.baseDomain = baseDomain;
     this.listPointer = listPointer;
    }
        
    @Override
    public void handleSimpleTag (HTML.Tag t, MutableAttributeSet img, int pos)
    {
        if (t == HTML.Tag.IMG)
        {
        	imageURL = img.getAttribute(HTML.Attribute.SRC);
        	passable_URL = imageURL.toString();
            System.out.println ("This is my image info: " + passable_URL);
            if (imageURL != null)
            {        
                String temp = imageURL.toString();
                if (temp.startsWith("html."))
                {
                    listPointer.addElement(baseDomain + passable_URL);
                    System.out.println ("ELEMENT ADDED TO LIST");
                }
                    else 
                {         
                    
                    listPointer.addElement (passable_URL);
                    System.out.println ("ELEMENT ADDED TO LIST 2");
                }
            }
        }
    }
    
}