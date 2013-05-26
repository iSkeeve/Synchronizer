package synchronizer;

import java.io.IOException;
import java.util.*;

// Referenced classes of package synchronizer:
//            DirSynchronizer, SuncFolder, FolderList, Log
/**
 *  ласс, управл€ющий сбором информации, а также, потоками исполнени€ синхронизации.
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
     * ћетод провер€ющий, окончена ли работа всех потоков синхронизации. »спользуетс€ в методе 
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
     * getInstance метод класса Synchronizer. ≈сли экземл€ра класса не существует, создает его. 
     * Ёкземпл€р класса может быть только один.
     * */
    public static Synchronizer getInstance()
        throws IOException, InterruptedException
    {
        if(instance == null)
            instance = new Synchronizer();
        return instance;
    }
    /**
     *  онструктор класса. ¬ нем создаетс€ лог, заполн€етс€ коллекци€ всех пар папок-источников 
     * и папок-назначени€, а также запускаютс€ потоки исполнени€ синхронизации, 
     * по одному на каждую из ранее перечисленных пар, все потоки также помещаютс€ в 
     * св€занный список. 
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
     *—писок вех потоков синхронизации. 
     **/
    List ThreadList;
}