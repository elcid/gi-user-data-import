package de.springerprofessional.gi.ftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;

import java.util.Vector;

public class SFTPExample {

    public static void main(String args[]) throws Exception {

        String destinationPath = "/tmp/";


        ChannelSftp sftpChannel = Helper.openNewTLSftpChannel();
        sftpChannel.connect();
        sftpChannel.cd(Helper.remoteFileDirectory);

        Vector<ChannelSftp.LsEntry> list = sftpChannel.ls("*.csv");
        for(ChannelSftp.LsEntry lsEntry: list) {
            System.out.println("Downloading test files");
            final String fileName = lsEntry.getFilename();
            sftpChannel.get(fileName, destinationPath + fileName);
        }

        Session session = sftpChannel.getSession();
        sftpChannel.exit();
        session.disconnect();
    }
}