
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yiu
 */
public class UploadPPt {

    File file;
    String url;

    public UploadPPt(File file, String url) {
        this.file = file;
        this.url = url;

    }

    public String execute() {

        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection connection = null;
        String fileName = file.getName();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            String boundary = "---------------------------boundary";
            String tail = "\r\n--" + boundary + "--\r\n";
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            connection.setDoOutput(true);

            String metadataPart = "--"
                    + boundary
                    + "\r\n"
                    + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
                    + "" + "\r\n";

            String fileHeader1 = "--"
                    + boundary
                    + "\r\n"
                    + "Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
                    + fileName + "\"\r\n"
                    + "Content-Type: application/octet-stream\r\n"
                    + "Content-Transfer-Encoding: binary\r\n";

            long fileLength = file.length() + tail.length();
            String fileHeader2 = "Content-length: " + fileLength + "\r\n";
            String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
            String stringData = metadataPart + fileHeader;

            long requestLength = stringData.length() + fileLength;
            connection.setRequestProperty("Content-length", ""
                    + requestLength);
            connection.setFixedLengthStreamingMode((int) requestLength);
            connection.connect();

            DataOutputStream out3 = new DataOutputStream(
                    connection.getOutputStream());
            out3.writeBytes(stringData);
            out3.flush();

            int progress = 0;
            int bytesRead = 0;
            byte buf[] = new byte[1024];
            BufferedInputStream bufInput = new BufferedInputStream(
                    new FileInputStream(file));
            while ((bytesRead = bufInput.read(buf)) != -1) {
                // write output
                out3.write(buf, 0, bytesRead);
                out3.flush();
                progress += bytesRead;
                // update progress bar

            }

            // Write closing boundary and close stream
            out3.writeBytes(tail);
            out3.flush();
            out3.close();

            // Get server response
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

        } catch (Exception e) {
            // Exception
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "/ppt/"+file.getName();


    }
}
