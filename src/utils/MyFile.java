package utils;

import java.io.File;

public class MyFile extends File
{

    public boolean fileEquals(File file)
    {
        return (file.lastModified() == lastModified()) & getName().equals(file.getName()) & (length() == file.length());
    }

    public MyFile(String file)
    {
        super(file);
    }
}