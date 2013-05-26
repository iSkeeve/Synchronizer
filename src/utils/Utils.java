package utils;

import java.io.UnsupportedEncodingException;

public class Utils
{

    public Utils()
    {
    }

    public static synchronized String DOSString(String str)
    {
        try {
			return new String(str.getBytes("Cp866"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
}