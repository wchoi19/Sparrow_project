package xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/*
 * member function 항목 하나의 URL을 받아 해당 함수의 이름과 함수 포맷들을 리턴하는 클래스.
 */

public class Scraper {
    public static String[] scrap(String url){
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