package de.springerprofessional.gi.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Vector;

public class SFTPExample {

    public static void main(String args[]) throws Exception {
        String HOST = "217.69.92.90";   //gi.de
        int    PORT = 22;
        String USER = "sfp";
        String PASS = "Ahc6eineim";
        String WORKINGDIR = "Daten";
        String destinationPath = "/tmp/";

        JSch jsch = new JSch();
        Session session = jsch.getSession(USER, HOST, PORT);
        session.setPassword(PASS);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
        sftpChannel.connect();
        sftpChannel.cd(WORKINGDIR);

        Vector<ChannelSftp.LsEntry> list = sftpChannel.ls("*.csv");
        for(ChannelSftp.LsEntry lsEntry: list) {
            System.out.println("Downloading test files");
            sftpChannel.get(lsEntry.getFilename(), destinationPath + lsEntry.getFilename());
        }


        sftpChannel.exit();
        session.disconnect();
    }
}