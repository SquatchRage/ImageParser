

import javax.swing.DefaultListModel;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;


class MyParserCallbackTagHandler extends HTMLEditorKit.ParserCallback
{
    String baseDomain;
    Object imageURL;
    DefaultListModel<String> list;
  static  String passable_URL;
    
    
    public MyParserCallbackTagHandler (String baseDomain, DefaultListModel<String> listPointer)
    {
     this.baseDomain = baseDomain;
     this.list = listPointer;
    }
        
    @Override
    public void handleSimpleTag (HTML.Tag t, MutableAttributeSet a, int pos)
    {
        if (t == HTML.Tag.IMG)
        {
        	imageURL = a.getAttribute(HTML.Attribute.SRC);
        	passable_URL = imageURL.toString();
            System.out.println ("This is my image info: " + passable_URL);
            if (passable_URL != null)
            {        
                if (passable_URL.startsWith("HTML."))
                {
                	list.addElement(baseDomain + passable_URL);
                }
                    else 
                {         
                    list.addElement (passable_URL);
                }
            }
        }
    }
    
}