package synchronizer;

/** �����, ����������� ������, ���������� ���������� � �����-��������� � ����� ����������.
 * @param	resourse -	 ���� � �����-���������.
 * @param	target - ���� � ����� ����������. 
 **/
public class SuncFolder 
{
	
	public String toString(){
		String string = "Resource: " + resource + "\n" +  "Target: " + target +"\n";
		return  string;
	}
	
    public String getResource()
    {
        return resource;
    }

    public void setResource(String resource)
    {
        this.resource = resource;
    }

    public String getTarget()
    {
        return target;
    }

    public void setTarget(String target)
    {
        this.target = target;
    }

    /*public SuncFolder()
    {
    }*/

    public SuncFolder(String resource, String target)
    {
        this.resource = resource;
        this.target = target;
    }

    private String resource;
    private String target;
}