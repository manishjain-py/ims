package arihant_ims;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CopyDB {

    public static void main(String[] args) {

        InputStream inStream = null;
        OutputStream outStream = null;

        try {
            Calendar currentDate = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateNow = formatter.format(currentDate.getTime());

            File afile = new File(".//db//IMS_DB.sqlite");
            File bfile = new File(".//backup//"+dateNow+".sqlite");

            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();

            System.out.println("File is copied successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
