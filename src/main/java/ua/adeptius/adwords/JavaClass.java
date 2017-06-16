package ua.adeptius.adwords;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.adeptius.adwords.model.Adword;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class JavaClass {

    public static void main(String[] args) throws Exception{
        JavaClass javaClass = new JavaClass();
        javaClass.getAdwordsByQuery();

    }


    private ArrayList<Adword> getAdwordsByQuery() throws Exception{
        String googleurlUrl = "https://www.google.com/search?q=";
        String query = "квартира в киеве";
        googleurlUrl = googleurlUrl + URLEncoder.encode(query);

        URL obj = new URL(googleurlUrl);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        while (in.ready()){
            String s = new String(in.readLine().getBytes());
            sb.append(s);
        }
        in.close();

//        System.out.println(sb.toString());

//        System.out.println("Запрос: " + query);
//        System.out.println("Реклама:");

        Document doc2 = Jsoup.parse(sb.toString());
        System.out.println(doc2);
        ArrayList<Adword> adwords = new ArrayList<>();
        Elements li = doc2.getElementsByClass("ads-ad");
        for (Element liClass : li) {
            Element hrefAndName = liClass.getElementsByClass("ellip").first().getElementsByTag("a").first();
            String name = hrefAndName.text();
            String href = hrefAndName.attr("href");
            String cite = liClass.getElementsByTag("cite").first().text();
            String description = liClass.getElementsByClass("ads-creative").first().text();
            Adword adword = new Adword(name,cite,href,description);
            adwords.add(adword);
        }
        return adwords;
    }
}
