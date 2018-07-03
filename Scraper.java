package xml;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import sun.reflect.annotation.ExceptionProxy;

import java.io.BufferedWriter;
import java.io.FileWriter;
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




                //EXPORT RESULT INTO TXT FILE (XML 생성기와 합치면 필요 없음)

             /*
                BufferedWriter writer = new BufferedWriter(new FileWriter(".\\test.txt"));
                writer.write(result);
                writer.close();
                System.out.println("\nSucessfully parsed. Saved as test.txt");

            } catch (Exception e) {
                System.out.println("Error: Not a valid cppreference.com url");
            }

            */
        }
        return null;

    }
}