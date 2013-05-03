package de.springerprofessional.gi.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class FTPDemo {

    private static InetAddress host;
    private static String user = "sfp";
    private static int port = 22;
    private static String pwd = "Ahc6eineim";

    public static void main(String[] args)
    {
        FTPSClient client = new FTPSClient("SSL");
        client.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());

        try {
            host = InetAddress.getByName("gi.de");
            client.connect(host, port);
            client.login(user, pwd);
            client.setFileType(FTP.ASCII_FILE_TYPE);
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
