package xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.*;

/*
 * cppreference.com에서 컨테이너의 URL을 Scanner로 입력받아 해당 컨테이너 member function들의 URL들을 Queue로 리턴하는 클래스
 */

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

            //크롤링 할 카테고리 이름들(member function 메뉴의 헤더들)
            List<String> HeaderstoScrape = new ArrayList<>();
            HeaderstoScrape.add("Iterators");
            HeaderstoScrape.add("Capacity");
            HeaderstoScrape.add("Modifiers");
            HeaderstoScrape.add("Lookup");
            HeaderstoScrape.add("Bucket Interface");

            //PARSE HREF LINKS OF NTH ELEMENT
            for (Element row : document.select("div.t-navbar-head:nth-child(3n) table.t-nv-begin tr")){
                if(row.hasClass("t-nv-h2")||row.hasClass("t-nv-h1")) currHeader=row.select("td").text(); // 헤더가 있는 줄이면, 현재 헤더(currHeader)를 그것으로 설정.
                else if(HeaderstoScrape.contains(currHeader) && row.hasClass("t-nv")) {
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