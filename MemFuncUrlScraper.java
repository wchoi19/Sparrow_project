package xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;
import java.util.regex.Pattern;

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
            final Document document = Jsoup.connect(url).get();
            Queue<String> urlResults=new LinkedList<String>();

            //PARSE HREF LINKS OF NTH ELEMENT
            for (Element row : document.select("div.t-navbar-head:nth-child(3n) table.t-nv-begin tr.t-nv a")){
                String link = row.attr("href");
                System.out.println("link is : https://en.cppreference.com" + link);
                urlResults.add("https://en.cppreference.com" + link);
                return urlResults;
            }
        } catch (Exception e) {
            System.out.println("Error: Not a valid cppreference.com url");
        }
        return null;
    }
}