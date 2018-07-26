package xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Scraper {
    public static String[] scrap(String url){
        //while(true) {

        //TAKE INPUT URL FROM CPPREFERENCE

        System.out.println("URL INPUT : " + url);

        //PARSE FUNCTION NAME AND ARGUMENTS FROM HTML
        try {
            final Document document = Jsoup.connect(url).get();
            Map<String, String> results = new HashMap<String, String>();

            String result_func_name=document.select("h1#firstHeading").text();
            String result_functions = "";

            for (Element row : document.select("table.t-dcl-begin tr.t-dcl")) {
                final String title = row.select("span.mw-geshi").text();
                //System.out.println(title);
                result_functions += title + "\n";
            }




            return new String[] {result_func_name,result_functions};
        } catch (Exception e) {
            System.out.println("Error: Not a valid cppreference.com url");
        }
        //}
        return new String[] {"ERROR", "ERROR"};

    }
}