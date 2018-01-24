

import javax.swing.DefaultListModel;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;


class MyParserCallbackTagHandler extends HTMLEditorKit.ParserCallback
{
    String baseDomain;
    Object imageURL;
    DefaultListModel listPointer;
    String passable_URL;
    
    
    public MyParserCallbackTagHandler (String baseDomain, DefaultListModel listPointer)
    {
     this.baseDomain = baseDomain;
     this.listPointer = listPointer;
    }
        
    @Override
    public void handleSimpleTag (HTML.Tag t, MutableAttributeSet a, int pos)
    {
        if (t == HTML.Tag.IMG)
        {
        	imageURL = a.getAttribute(HTML.Attribute.SRC);
        	passable_URL = (String)imageURL;
            System.out.println ("This is my image info: " + imageURL.toString());
            if (imageURL != null)
            {        
                String temp = imageURL.toString();
                if (temp.startsWith("html."))
                {
                    listPointer.addElement(baseDomain + imageURL.toString());
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