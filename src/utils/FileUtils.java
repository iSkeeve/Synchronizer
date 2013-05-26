package utils;

import java.io.*;
import java.util.List;
import org.apache.log4j.Logger;
import synchronizer.Log;

// Referenced classes of package utils:
//            MyFile

public class FileUtils
{

    public static FileUtils getInstance()
    {
        if(instance == null)
            instance = new FileUtils();
        return instance;
    }

    public FileUtils()
    {
    }

    public boolean isFile(String s)
    {
        File file = new File(s);
        return file.isFile();
    }

    public void deleteFile(String s)
    {
        File file = new File(s);
        if(file.isFile())
            file.delete();
    }
    
    /**
     * ћетод создает список всех элементов файловой системы, €вл€ющихс€ детьми файла с путем, 
     * заданным строкой dirItem и создает из них коллекцию fileList
     * */
    
    public void recurseInDirFrom(String dirItem, List fileList)
    {
        File file = null;
        try
        {
            file = new File(dirItem);
            if(file != null && file.isDirectory())
            {
                String list[] = file.list();
                for(int i = 0; i < list.length; i++)
                    recurseInDirFrom(dirItem + File.separatorChar + list[i], fileList);

            }
        }
        catch(NullPointerException e)
        {
            Log.getInstance().LogFile(dirItem, "Error");
        }
        MyFile f = new MyFile(dirItem);
        fileList.add(f);
    }

    public boolean copyFile(String s, String s1)
        throws IOException
    {
        boolean flag = !s.equals(s1);
        if(flag)
        {
            long l = 0L;
            if(logger.isDebugEnabled())
                l = System.currentTimeMillis();
            File file = new File(s1);
            if(file.isFile())
                file.delete();
            FileInputStream fileinputstream = null;
            try
            {
                fileinputstream = new FileInputStream(s);
            }
            catch(FileNotFoundException e)
            {
                Log.getInstance().LogFile(s, "Error");
                return false;
            }
            File file1 = File.createTempFile("ors", ".tmp", new File(file.getParent()));
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            int i = 0;
            byte abyte0[] = new byte[4096];
            while((i = fileinputstream.read(abyte0)) > 0) 
                fileoutputstream.write(abyte0, 0, i);
            fileinputstream.close();
            fileoutputstream.close();
            file1.renameTo(file);
            if(logger.isDebugEnabled())
                logger.debug("copying time = " + (System.currentTimeMillis() - l));
        }
        return flag;
    }

    static Class _mthclass$(String x0)
    {
    	try {
            return Class.forName(x0);
    	}
    	catch( ClassNotFoundException x1){
    		throw new NoClassDefFoundError(x1.getMessage());
    	}
    }

    private static final Logger logger;
    protected static final int BUFFER_LENGTH = 4096;
    protected static final String TMP_FILE_PREFIX = "ors";
    protected static final String TMP_FILE_SUFFIX = ".tmp";
    protected static FileUtils instance = null;

    static 
    {
        logger = Logger.getLogger(utils.FileUtils.class);
    }
}