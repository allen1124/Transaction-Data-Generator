import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Scrapper {
    private String listofSecurities_url = "https://www.hkex.com.hk/eng/services/trading/securities/securitieslists/ListOfSecurities.xlsx";
    private String finalCrumb;

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

    public void getListHistoricalPriceData(String stock_code) throws IOException {
        String mainURL = "https://finance.yahoo.com/quote/"+stock_code+".HK/history?p="+stock_code+".HK";
        Map<String, List<String>> setCookies = setCookies(mainURL);

        String historicalPrice_url = "https://query1.finance.yahoo.com/v7/finance/download/"+stock_code+".HK?period1=1525363200&period2=1556899200&interval=1d&events=history&crumb="+finalCrumb;
        URL website = new URL(historicalPrice_url);
        URLConnection data = website.openConnection();

        // get the list of Set-Cookie cookies from response headers
        List<String> cookies = setCookies.get("Set-Cookie");
        if (cookies != null) {
            for (String c : cookies)
                data.setRequestProperty("Cookie", c);
        }
        Scanner input = new Scanner(data.getInputStream());
        if (input.hasNext())
            input.nextLine();
        // start reading data
        while (input.hasNextLine()) {
            String line = input.nextLine();
            System.out.println(line);
        }
        input.close();
    }

    private String searchCrumb(URLConnection con) throws IOException {
        String crumb = null;
        InputStream inStream = con.getInputStream();
        InputStreamReader irdr = new InputStreamReader(inStream);
        BufferedReader rsv = new BufferedReader(irdr);

        Pattern crumbPattern = Pattern.compile(".*\"CrumbStore\":\\{\"crumb\":\"([^\"]+)\"\\}.*");

        String line = null;
        while (crumb == null && (line = rsv.readLine()) != null) {
            Matcher matcher = crumbPattern.matcher(line);
            if (matcher.matches())
                crumb = matcher.group(1);
        }
        rsv.close();
        System.out.println("Crumb is : "+crumb);
        return crumb;
    }

    private Map<String, List<String>> setCookies(String mainUrl) throws IOException {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        URL url = new URL(mainUrl);
        URLConnection con = url.openConnection();
        finalCrumb = searchCrumb(con);
        for (Map.Entry<String, List<String>> entry : con.getHeaderFields().entrySet()) {
            if (entry.getKey() == null || !entry.getKey().equals("Set-Cookie"))
                continue;
            for (String s : entry.getValue()) {
                map.put(entry.getKey(), entry.getValue());
                System.out.println(map);
            }
        }
        return map;
    }
}
