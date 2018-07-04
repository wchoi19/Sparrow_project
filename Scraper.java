package xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.util.Scanner;

public class Scraper {
    public static String scrap(){
        while(true) {

            //TAKE INPUT URL FROM CPPREFERENCE
            System.out.println("\nEnter cppreference.com url (enter 'exit' to end) :");
            Scanner in = new Scanner(System.in);
            String url = in.nextLine();
            if(url.equals("exit")) {
                System.out.println("terminated.");
                break;
            }
            System.out.println("You entered : " + url);

            //PARSE FUNCTION NAME AND ARGUMENTS FROM HTML
            try {
                final Document document = Jsoup.connect(url).get();
                String result = "";

                for (Element row : document.select("table.t-dcl-begin tr.t-dcl")) {
                    final String title = row.select("span.mw-geshi").text();
                    System.out.println(title);
                    result += title + "\n";
                }
                return result;
            } catch (Exception e) {
                System.out.println("Error: Not a valid cppreference.com url");
            }
        }
        return null;

    }
}