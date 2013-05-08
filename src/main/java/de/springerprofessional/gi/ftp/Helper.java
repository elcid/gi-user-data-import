package de.springerprofessional.gi.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Helper {


    public  static String remoteFileDirectory= "Daten";
    private static String fileType = "*csv";
    private static String pattern = "ddMMyyyy";
    private static DateTime cleanupCutoffdate = DateTime.now();

    private static DateTime parseDateFromFileName(String fileName) {
        Date date = null;
        try {
            date = new SimpleDateFormat(pattern, Locale.ENGLISH).parse(fileName);    //TODO filename must be parsed too
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return new DateTime(date.getTime());
    }

    public static void clean() throws JSchException {
        com.jcraft.jsch.ChannelSftp channel = null;
        try {
            channel = Helper.openNewTLSftpChannel();
            channel.connect();
            channel.cd(remoteFileDirectory);

            List<ChannelSftp.LsEntry> list = channel.ls("*." + fileType);
            for (ChannelSftp.LsEntry file : list) {
                String fileName = file.getFilename();
                DateTime fileDate = new DateTime(parseDateFromFileName(fileName));

                //if this file is older than the cutoff date, delete from the SFTP share
                if (fileDate.compareTo(cleanupCutoffdate) < 0) {
                    channel.rm(fileName);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (channel != null) {
                Session session = channel.getSession();
                channel.disconnect();
                session.disconnect();
                System.out.println(channel.isConnected());
            }
        }
    }

    public static ChannelSftp openNewTLSftpChannel() throws JSchException {
        String HOST = "217.69.92.90";   //gi.de
        int    PORT = 22;
        String USER = "sfp";
        String PASS = "Ahc6eineim";

        JSch jsch = new JSch();
        Session session = jsch.getSession(USER, HOST, PORT);
        session.setPassword(PASS);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        return (ChannelSftp) session.openChannel("sftp");
    }
}
