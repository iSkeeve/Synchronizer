package synchronizer;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import utils.FileUtils;
import utils.MyFile;

// Referenced classes of package synchronizer:
//            Log
/**
 * Объект класса - поток исполнения синхронизации. 
 * */
public class DirSynchronizer
    implements Runnable
{

    public Thread getThread()
    {
        return t;
    }
    
    public void run()
    {
        try
        {
            synchronizeDir();
            if(verification())
                System.out.println("Synchronization of the catalogue " + resource + "  is successfully completed");
            else
                System.out.println("Synchronization of the catalogue " + resource + " with mistakes");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     **/
    
    public boolean verification()
    {
        List resultList = new LinkedList();
        FileUtils.getInstance().recurseInDirFrom(target, resultList);
        long resultSize = 0L;
        for(Iterator itr = resourceList.iterator(); itr.hasNext();)
        {
            MyFile file = (MyFile)itr.next();
            resultSize += file.length();
        }

        altertargetSize = resultSize;
        return resultSize == resourceSize;
    }
    
    /**Данный метод определяет, существуют ли директории, представленные объектом {@link SuncFolder}, если директория  назначения не существует - создает ее.
     * @return true - если обе директории существуют (или директориря назначения удачно создана), false если нет директории источника или попытка
     *  создания директории назначения оказалась неудачной.
     * 			
     **/
    
    private boolean checkPaths()
    {
        File res = new File(resource);
        if(res.exists())
        {
            File targ = new File(target);
            if(targ.exists())
                return true;
            else
                return targ.mkdir();
        } else
        {
            return false;
        }
    }
    
    
    
    private String getTargerFilePath(MyFile file)
    {
    	String r = null;
        if(file.getPath().equals(resource))
        {
            return "";
        } else
        {
            try{
        	r = file.getPath().substring(resource.length() + 1); //error
            }
            catch(java.lang.StringIndexOutOfBoundsException e){
            	JOptionPane.showMessageDialog(null, "Произошла критическая ошибка при " +
            			"определении пути файла в папке назначения file.getPath() = "+
            			file.getPath()+", resource.length() = "+resource.length(), "Error", 
            			JOptionPane.ERROR_MESSAGE);
            }
            return target + "\\" + r;
        }
    }
    
    /**
     * Синхронизация отдельно взятого файла из папки из точника с папкой назаначения. 
     * Если объекту класса File соответствует папка, и она отсутствует в папке назанчения, 
     * папка создается. Если это файл, отсутствующий в папке назначения он копируется туда пакетами по 4 КБ.
     * @param Путь к файлу в папк-источнике.
     * @see FileUtils.copyFile 
     * */
    
    private boolean synchronizeFile(MyFile resfile)
        throws IOException
    {
        String targetPath = getTargerFilePath(resfile);
        if(resfile.isDirectory())
        {
            MyFile tarfile = new MyFile(targetPath);
            if(tarfile.exists())
                return true;
            if(tarfile.getPath().equals(""))
                return true;
            if(tarfile.mkdir())
            {
                Log.getInstance().LogFile(resfile, "Ok");
                return true;
            } else
            {
                Log.getInstance().LogFile(resfile, "Error");
                return false;
            }
        }
        if(resfile.isFile())
        {
            MyFile targetFile = new MyFile(targetPath);
            if(!resfile.fileEquals(targetFile))
                if(resfile.canRead())
                {
                    if(FileUtils.getInstance().copyFile(resfile.getPath(), getTargerFilePath(resfile)))
                    {
                        Log.getInstance().LogFile(resfile, "Ok");
                        targetFile.setLastModified(resfile.lastModified());
                    } else
                    {
                        Log.getInstance().LogFile(resfile, "Error");
                    }
                } else
                {
                    Log.getInstance().LogFile(resfile, "Error");
                }
        }
        return false;
    }
    
    /**
     * Метод записывает размеры папок {@link SuncFolder} в соответствующие переменные.
     * */
    
    private void initSize()
    {
        for(Iterator itr = resourceList.iterator(); itr.hasNext();)
        {
            MyFile file = (MyFile)itr.next();
            resourceSize += file.length();
        }

        for(Iterator itrt = targetList.iterator(); itrt.hasNext();)
        {
            MyFile file = (MyFile)itrt.next();
            beforetargetSize += file.length();
        }

    }

    public long getResourceSize()
    {
        return resourceSize;
    }
    
    /**
     * Если папки источника и назанчения существуют, получает содержание обеих папок, 
     * далее синхронизирует последовательно каждый файл.
     * @see recurseInDirFrom
     * @see MyFile
     * @see initSize
     * @see synchronizeFile
     **/
    
    public void synchronizeDir()
        throws IOException
    {
        if(!checkPaths())
            return;
        FileUtils.getInstance().recurseInDirFrom(resource, resourceList);
        FileUtils.getInstance().recurseInDirFrom(target, targetList);
        initSize();
        Object res[] = resourceList.toArray();
        for(int i = res.length - 1; i >= 0; i--)
        {
            MyFile resfile = (MyFile)res[i];
            synchronizeFile(resfile);
        }

    }

    public long getBeforetargetSize()
    {
        return beforetargetSize;
    }

    public long getAltertargetSize()
    {
        return altertargetSize;
    }
    /**
     * Констурктор класса, инициализирует списки файлов в папке-назанчения и папке-источнике,
     * запускает поток на исполнение. 
     **/
    public DirSynchronizer(String resource, String target)
    {
        this.resource = "";
        this.target = "";
        resourceSize = 0L;
        beforetargetSize = 0L;
        altertargetSize = 0L;
        resourceList = new LinkedList();
        targetList = new LinkedList();
        this.resource = resource;
        this.target = target;
        t = new Thread(this, "DirSync");
        t.start();
    }

    private Thread t;
    private String resource;
    private String target;
    private long resourceSize;
    private long beforetargetSize;
    private long altertargetSize;
    private List resourceList;
    private List targetList;
}