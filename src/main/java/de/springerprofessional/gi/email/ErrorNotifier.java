package de.springerprofessional.gi.email;

public interface ErrorNotifier {
    public void notifyCopyError(String srcDir, String destDir, String filename);
}
