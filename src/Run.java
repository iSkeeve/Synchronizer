import java.io.IOException;
import synchronizer.Log;
import synchronizer.Synchronizer;

public class Run
{

    public Run()
    {
    }
    /** 
     * Ìועמה main ןנמדנאללû.
     * */
    public static void main(String args[])  {
    	try{
    	   	Synchronizer.getInstance();
        	for(; Synchronizer.getInstance().isEnd(); Thread.sleep(5000L))
        	Log.getInstance().end();
    	} 
    	catch(IOException e){
    		e.printStackTrace();
    	}
    	catch(InterruptedException e1){
    		e1.printStackTrace();
    	}
    	
    }
}