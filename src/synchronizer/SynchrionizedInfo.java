package synchronizer;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package synchronizer:
//            DirSynchronizer, Synchronizer

public class SynchrionizedInfo
{

    public long getResourceSize()
    {
        return ResourceSize;
    }

    public long getBegoreTargerSize()
    {
        return begoreTargerSize;
    }

    public long getAlterTargetSize()
    {
        return alterTargetSize;
    }

    private void init()
    {
        for(Iterator i = list.iterator(); i.hasNext();)
        {
            DirSynchronizer ds = (DirSynchronizer)i.next();
            ResourceSize += ds.getResourceSize();
            begoreTargerSize += ds.getBeforetargetSize();
            alterTargetSize += ds.getAltertargetSize();
        }

    }

    public SynchrionizedInfo()
        throws IOException, InterruptedException
    {
        ResourceSize = 0L;
        begoreTargerSize = 0L;
        alterTargetSize = 0L;
        list = Synchronizer.getInstance().getThreadList();
        init();
    }

    private List list;
    private long ResourceSize;
    private long begoreTargerSize;
    private long alterTargetSize;
}