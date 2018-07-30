package xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

public class MemFuncUrlScraper {
    public static Queue<String> MemFuncUrlScrape(){

        System.out.println("\nEnter cppreference.com url (enter 'exit' to end) :");
        Scanner in = new Scanner(System.in);
        String url = in.nextLine();
        if(url.equals("exit")) {
            System.out.println("terminated.");
            System.exit(0);
        }
        System.out.println("You entered : " + url);

        //PARSE FUNCTION NAME AND ARGUMENTS FROM HTML
        try {
            System.out.println("memfuncurlscrape starting...");
            final Document document = Jsoup.connect(url).get();
            Queue<String> urlResults=new LinkedList<String>();
            String currHeader = "default";

            //PARSE HREF LINKS OF NTH ELEMENT
            for (Element row : document.select("div.t-navbar-head:nth-child(3n) table.t-nv-begin tr")){
                if(row.hasClass("t-nv-h2")) currHeader=row.select("td").text(); // if it is a header row, set the current header to the text of the row
                else if(!(currHeader.equals("Element access")||currHeader.equals("default")) && row.hasClass("t-nv")) {
                    String link = row.select("a").attr("href");
                    System.out.println("link is : https://en.cppreference.com" + link);
                    urlResults.add("https://en.cppreference.com" + link);
                }
            }
            System.out.println("memfuncurlscrape ended...");
            return urlResults;
        } catch (Exception e) {
            System.out.println("Error: Not a valid cppreference.com url");
        }
        return null;
    }
}