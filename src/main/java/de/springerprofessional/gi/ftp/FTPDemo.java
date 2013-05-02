package de.springerprofessional.gi.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class FTPDemo {

    private static InetAddress host;
    private static String user;
    private static String pwd;

    public static void main(String[] args)
    {
        FTPClient client = new FTPClient();
        try
        {
            client.connect(host);
            client.login(user, pwd);
            FTPFile[] files = client.listFiles();
            FTPFile lastFile = getMaxLastModified(files);
            System.out.println(lastFile.getName());
            client.disconnect();
        }
        catch(SocketException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch(IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static FTPFile getMaxLastModified(FTPFile[] ftpFiles) {
        return Collections.max(Arrays.asList(ftpFiles), new LastModifiedComparator());
    }

    static class LastModifiedComparator implements Comparator<FTPFile> {

        public int compare(FTPFile f1, FTPFile f2) {
            return f1.getTimestamp().compareTo(f2.getTimestamp());
        }
    }
}
