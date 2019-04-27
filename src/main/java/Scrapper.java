import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;


public class Scrapper {
    private String listofSecurities_url = "https://www.hkex.com.hk/eng/services/trading/securities/securitieslists/ListOfSecurities.xlsx";

    public void getListofSecurities() throws IOException {
        File f = new File("listofSecurities.xlsx");
        if(!f.exists()) {
            URL website = new URL(listofSecurities_url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("listofSecurities.xlsx");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.println("List of Securities is downloaded");
        }else{
            System.out.println("List of Securities already exists");
        }
    }
}