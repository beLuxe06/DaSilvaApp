package mi.ur.de.dasilvaapp;

/**
 * Created by blu on 03.09.2015.
 */
public interface DownloadListener {

    public void onDownloadFinished();

    public void onDownloadStarted();

    public void onDownloadInProgress();
}
