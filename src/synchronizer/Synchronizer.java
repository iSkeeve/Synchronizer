package synchronizer;

import java.io.IOException;
import java.util.*;

// Referenced classes of package synchronizer:
//            DirSynchronizer, SuncFolder, FolderList, Log
/**
 * �����, ����������� ������ ����������, � �����, �������� ���������� �������������.
 * @see ThreadList
 * @see DirSynchronizer
 **/
public class Synchronizer
{

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public List getThreadList()
    {
        return ThreadList;
    }
    
    /**
     * ����� �����������, �������� �� ������ ���� ������� �������������. ������������ � ������ 
     * {@link main}.
     * */
    public boolean isEnd()
    {
        for(Iterator i = ThreadList.iterator(); i.hasNext();)
        {
            DirSynchronizer ds = (DirSynchronizer)i.next();
            if(ds.getThread().isAlive())
                return true;
        }

        endDate = new Date();
        return false;
    }
    /**
     * getInstance ����� ������ Synchronizer. ���� ��������� ������ �� ����������, ������� ���. 
     * ��������� ������ ����� ���� ������ ����.
     * */
    public static Synchronizer getInstance()
        throws IOException, InterruptedException
    {
        if(instance == null)
            instance = new Synchronizer();
        return instance;
    }
    /**
     * ����������� ������. � ��� ��������� ���, ����������� ��������� ���� ��� �����-���������� 
     * � �����-����������, � ����� ����������� ������ ���������� �������������, 
     * �� ������ �� ������ �� ����� ������������� ���, ��� ������ ����� ���������� � 
     * ��������� ������. 
     * @see Log
     * @see FolderList
     * @see SuncFolder
     * @see DirSynchronizer
     * */
    private Synchronizer()
        throws IOException, InterruptedException
    {
        startDate = null;
        endDate = null;
        ThreadList = new LinkedList();
        startDate = new Date();
        Iterator i = FolderList.getInstance().getFolderList().iterator();
        Log.getInstance();
        SuncFolder sf;
        for(; i.hasNext(); ThreadList.add(new DirSynchronizer(sf.getResource(), sf.getTarget())))
            sf = (SuncFolder)i.next();

    }

    private static Synchronizer instance;
    private Date startDate;
    private Date endDate;
    /**
     *������ ��� ������� �������������. 
     **/
    List ThreadList;
}