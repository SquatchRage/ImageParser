import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.DefaultListModel;
import javax.swing.text.html.parser.*;


public class FindTags
{
    URL url;
    URLConnection connection;
    InputStreamReader streamReader;
    MyParserCallbackTagHandler tagH;
    String domain;
    DefaultListModel<?> listPointer;

    
    FindTags (String webURL, DefaultListModel<?> listPointer)
    {   
        this.listPointer = listPointer;
        try 
        {
            url = new URL (webURL); 
            connection = url.openConnection(); //opening connection to website
            streamReader = new InputStreamReader (connection.getInputStream()); //reading in html
            
            tagH = new MyParserCallbackTagHandler (url.toString(), listPointer);
            new ParserDelegator().parse(streamReader, tagH, true);
            
        } 
        catch (MalformedURLException mue) 
        {
            System.out.println ("TKL: BAD URL");
            
            mue.printStackTrace();
        } catch (IOException ex) 
        {
            System.out.println ("ERROR IN HTMLPARSER CONSTRUCTOR IOException");
            ex.printStackTrace();
        }
        
        
    }   

}




