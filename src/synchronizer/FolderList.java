package synchronizer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import utils.Parser;

// Referenced classes of package synchronizer:
//            SuncFolder
/**
 * Список объектов {@link SuncFolder} и инструменты для заполнения этого списка из .xml файла.
 */
public class FolderList{ 


    public List getFolderList()
    {
        return list;
    }
    
    /**
     *  getInstance метод для класса. Экземпляр класса может быть только один.
     */
  
    public static FolderList getInstance()
    {
        if(instance == null)
            try
            {
                instance = new FolderList();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            catch(ParserConfigurationException e)
            {
                e.printStackTrace();
            }
            catch(SAXException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "\u041D\u0435 \u043C\u043E\u0433\u0443 " +
                		"\u0440\u0430\u0437\u043E\u0431\u0440\u0430\u0442\u044C " +
                		"\u0444\u0430\u0439\u043B synchronize.xml.\n \u0420\u0430\u0431\u043E" +
                		"\u0442\u0430 \u043D\u0435 \u0432\u043E\u0437\u043C\u043E\u0436\u043D" +
                		"\u0430", "\u041E\u0448\u0438\u0431\u043A\u0430", 0);
                System.exit(1);
            }
        return instance;
    }
    
  /**
   * Заполнение связонного списка объектов SuncFolder из .xml файла.
   **/
    
    private void initList() 
    {
        NodeList nl = parser.getDocument().getElementsByTagName("dir");
        for(int i = 0; i < nl.getLength(); i++)
        {
            NamedNodeMap atr = nl.item(i).getAttributes();
            SuncFolder sf = new SuncFolder(atr.item(0).getNodeValue(), atr.item(1).getNodeValue());
            list.add(sf);
        }
        Iterator i = list.iterator();
        for (;i.hasNext();){
        	System.out.println(i.next());
        }
    }
    	/**
    	 * Конструктор класса. Создание экземпляра класса {@link Parser}. Если отсутствует 
    	 * xml файл происходит выход из системы с оповещением пользователя.
       	 **/
    private FolderList()	

        throws IOException, ParserConfigurationException, SAXException
    {
        list = new LinkedList();
        if(!(new File("synchronize.xml")).exists())
        {
            JOptionPane.showMessageDialog(null, "\u0424\u0430\u0439\u043B synchronize.xml \u043D\u0435 \u043D\u0430\u0439\u0434\u0435\u043D.\n \u0420\u0430\u0431\u043E\u0442\u0430 \u043D\u0435 \u0432\u043E\u0437\u043C\u043E\u0436\u043D\u0430", "\u041E\u0448\u0438\u0431\u043A\u0430", 0);
            System.exit(1);
        }
        parser = new Parser("synchronize.xml");
        initList();
    }

    private List list;
    private Parser parser;
    private static FolderList instance;
}