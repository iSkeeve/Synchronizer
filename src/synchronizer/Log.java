package synchronizer;

import java.io.*;
import java.util.Date;
import javax.swing.JOptionPane;

// Referenced classes of package synchronizer:
//            SynchrionizedInfo, Synchronizer

public class Log
{

    private void saveFile(String path, String type, String filename, String status, String size)
        throws IOException
    {
        path = path.substring(0, path.length() - filename.length());
        String str = "           <file path=\"" + path + "\" type=\"" + type + "\" filename=\"" + filename + "\" status=\"" + status + "\" size=\"" + size + "\"></file>\n";
        log.write(str.getBytes());
    }

    public void SaveSyncInto()
        throws IOException, InterruptedException
    {
        SynchrionizedInfo si = new SynchrionizedInfo();
        log.write((new String("      <info>\n")).getBytes());
        log.write((new String("                 <attredute atrName=\"\u0414\u0430\u0442\u0430 \u043D\u0430\u0447\u0430\u043B\u0430\" value=\"" + Synchronizer.getInstance().getStartDate() + "\"></attredute>\n")).getBytes());
        log.write((new String("                 <attredute atrName=\"\u0414\u0430\u0442\u0430 \u043E\u043A\u043E\u043D\u0447\u0430\u043D\u0438\u044F\" value=\"" + Synchronizer.getInstance().getEndDate() + "\"></attredute>\n")).getBytes());
        log.write((new String("                 <attredute atrName=\"\u0420\u0430\u0437\u043C\u0435\u0440 \u0440\u0435\u0441\u0443\u0440\u0441\u0430\" value=\"" + si.getResourceSize() + "\"></attredute>\n")).getBytes());
        log.write((new String("                 <attredute atrName=\"\u0420\u0430\u0437\u043C\u0435\u0440 \u0446\u0435\u043B\u0438 \u0434\u043E \u0441\u0438\u043D\u0445\u0440\u043E\u043D\u0438\u0437\u0430\u0446\u0438\u0438\" value=\"" + si.getBegoreTargerSize() + "\"></attredute>\n")).getBytes());
        log.write((new String("                 <attredute atrName=\"\u0420\u0430\u0437\u043C\u0435\u0440 \u0446\u0435\u043B\u0438 \u043F\u043E\u0441\u043B\u0435 \u0441\u0438\u043D\u0445\u0440\u043E\u043D\u0438\u0437\u0430\u0446\u0438\u0438\" value=\"" + si.getAlterTargetSize() + "\"></attredute\n>")).getBytes());
        log.write((new String("      </info>\n")).getBytes());
    }

    public synchronized void LogFile(File file, String status)
    {
        String type = "";
        if(file.isDirectory())
            type = "Folder";
        else
        if(file.isFile())
            type = "File";
        else
            type = "Unknow";
        try
        {
            saveFile(file.getPath(), type, file.getName(), status, String.valueOf(file.length()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
            		"\u041D\u0435 \u043C\u043E\u0433\u0443 " +
            		"\u0437\u0430\u043F\u0438\u0441\u0430\u0442\u044C " +
            		"\u0432 \u0444\u0430\u0439\u043B \u043B\u043E\u0433\u0430.\n " +
            		"\u0420\u0430\u0431\u043E\u0442\u0430 \u043D\u0435 " +
            		"\u0432\u043E\u0437\u043C\u043E\u0436\u043D\u0430", 
            		"\u041E\u0448\u0438\u0431\u043A\u0430", 0);
        }
    }

    public synchronized void LogFile(String file, String status)
    {
        String type = "";
        try
        {
            saveFile(file, type, "", status, String.valueOf(0));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "\u041D\u0435 \u043C\u043E\u0433\u0443 " +
            		"\u0437\u0430\u043F\u0438\u0441\u0430\u0442\u044C \u0432 " +
            		"\u0444\u0430\u0439\u043B \u043B\u043E\u0433\u0430.\n " +
            		"\u0420\u0430\u0431\u043E\u0442\u0430 \u043D\u0435 " +
            		"\u0432\u043E\u0437\u043C\u043E\u0436\u043D\u0430", 
            		"\u041E\u0448\u0438\u0431\u043A\u0430", 0);
        }
    }

    public void end()
        throws IOException, InterruptedException
    {
        log.write((new String("      </files>\n")).getBytes());
        SaveSyncInto();
        log.write((new String("</sync>\n")).getBytes());
    }

    private void start()
        throws IOException
    {
        log.write((new String("<?xml version=\"1.0\" encoding=\"windows-1251\"?>\n")).getBytes());
        log.write((new String("<sync>\n")).getBytes());
        log.write((new String("      <files>\n")).getBytes());
    }

    public static Log getInstance()
    {
        if(instance == null)
            try
            {
                instance = new Log();
            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "\u041D\u0435 \u043C\u043E\u0433\u0443 " +
                		"\u043D\u0430\u0439\u0442\u0438 \u0444\u0430\u0439\u043B \u0437\u0430 " +
                		"\u0437\u0430\u043F\u0438\u0441\u0438 \u043B\u043E\u0433\u0430.\n " +
                		"\u0420\u0430\u0431\u043E\u0442\u0430 \u043D\u0435 \u0432\u043E\u0437" +
                		"\u043C\u043E\u0436\u043D\u0430", "\u041E\u0448\u0438\u0431\u043A\u0430",
                		0);
            }
            catch(IOException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "\u041D\u0435 \u043C\u043E\u0433\u0443 " +
                		"\u0437\u0430\u043F\u0438\u0441\u0430\u0442\u044C \u0432 \u0444\u0430" +
                		"\u0439\u043B \u043B\u043E\u0433\u0430.\n \u0420\u0430\u0431\u043E\u0442" +
                		"\u0430 \u043D\u0435 \u0432\u043E\u0437\u043C\u043E\u0436\u043D\u0430", 
                		"\u041E\u0448\u0438\u0431\u043A\u0430", 0);
            }
        return instance;
    }

    private String getZerro(int val)
    {
        if(val < 10)
            return "0" + String.valueOf(val);
        else
            return String.valueOf(val);
    }

    private File getFile(Date date, int idx)
    {
        File file = new File("..\\\u041B\u043E\u0433\\" + String.valueOf(date.getYear() + 1900) + "\\" + getZerro(date.getMonth() + 1) + "\\" + getZerro(date.getDate()) + "." + getZerro(idx));
        if(file.exists())
        {
            idx++;
            return getFile(date, idx);
        } else
        {
            return file;
        }
    }

    private String getLogPath()
        throws IOException
    {
        Date date = new Date();
        /*File year = new File("..\\\u041B\u043E\u0433\\" + String.valueOf(date.getYear() + 
         * 1900));
        if(!year.exists())
            year.mkdirs();*/
        File month = new File(".\\\u041B\u043E\u0433\\" 
            + String.valueOf(date.getYear() + 1900) + "\\" + getZerro(date.getMonth() + 1));
        if(!month.exists())
            month.mkdirs();
        System.out.println(month.getCanonicalPath());
        File file = getFile(date, 1);
        file.createNewFile();
        return file.getPath();
    }

    public Log()
        throws IOException
    {
        String path = getLogPath();
        log = new FileOutputStream(path);
        start();
    }

    private static Log instance;
    private FileOutputStream log;
}