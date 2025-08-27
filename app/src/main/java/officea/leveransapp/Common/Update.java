package officea.leveransapp.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamAdapter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Update extends AsyncTask<String, Void, Void> {

    private Context context;
    private Activity activity;

    private String user;
    private String pass;
    private String URL;
    private int port;
    private String fileName;
    private String dlDir;

    private float percent = 0.0f;

    boolean running = true;
    boolean success = false;

    public void setContext(Context context) {
        this.context = context;
    }
    public float getPercent() { return percent; }
    public boolean isRunning() {
        return running;
    }
    public boolean isUpdated() { return success; }

    public void setup(String user, String pass, String URL, int port, String dlDir, String fileName) {
        this.user = user;
        this.pass = pass;
        this.URL = URL;
        this.port = port;
        this.dlDir = dlDir;
        this.fileName = fileName;
    }

    @Override
    protected Void doInBackground(String... arg0) {

        running = true;

        FTPClient ftpClient = new FTPClient();

        try {

            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            file.mkdirs();
            File outputFile = new File(file, fileName);
            if (outputFile.exists()) {
                outputFile.delete();
            }

            // Logging in in FTP
            ftpClient.connect(URL, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // Get size of file for progress
            String remote = dlDir + fileName;
            FTPFile fileInfo = ftpClient.mlistFile(remote);
            final long sizeOfFile = fileInfo.getSize();

            CopyStreamAdapter streamListener = new CopyStreamAdapter() {
                @Override
                public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                    percent = (totalBytesTransferred * 100) / sizeOfFile;
                }
            };
            ftpClient.setCopyStreamListener(streamListener);

            // Copy file
            OutputStream output = new BufferedOutputStream(new FileOutputStream(outputFile));
            success = ftpClient.retrieveFile(remote, output);
            if (success) {
                Log.d("File " + fileName + " has been downloaded successfully.", "");

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } else {
                Log.d("Failed downloading:" + fileName, "");
            }
            output.close();

        } catch (Exception e) {
            Log.e("Update", "Update error! " + e.getMessage());
        }

        running = false;

        return null;
    }
}
