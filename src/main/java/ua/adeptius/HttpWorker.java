package ua.adeptius;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ua.adeptius.model.HttpReport;
import ua.adeptius.model.HttpStatus;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class HttpWorker {

    public static void main(String[] args) throws IOException{
        String url = "http://www.e404.ho.ua";
        url = org.apache.commons.lang3.StringEscapeUtils.escapeJava(url);
        String wordToFind = "Бесплатная помощь";
        System.out.println(url);
        System.out.println(wordToFind);

        long t0 = System.nanoTime();
//        boolean b = HttpWorker.getResultViaStandartConnection(url, wordToFind);
        HttpReport report = HttpWorker.getResultViaJsoupConnect(url, wordToFind);
        System.out.println(report);
        System.out.println("Taken: "+TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - t0)+" ms");

//        System.out.println("Word " +(b ? "is FOUNDED!!!" : "not found"));
    }

//    public static boolean getResultViaStandartConnection(String url, String word) throws IOException {
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestProperty("User-Agent", "Mozilla");
//        con.setRequestProperty("Accept-Language", "en-US,en;q=0.8,ru;q=0.6,uk;q=0.4");
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        StringBuilder sb = new StringBuilder();
//        while (in.ready()){
//            String s = new String(in.readLine().getBytes());
//            sb.append(s);
//        }
//        in.close();
//
//        Document doc2 = Jsoup.parse(sb.toString());
//        String result = doc2.toString();
//        System.out.println(result);
//        return result.contains(word);
//    }

    public static HttpReport getResultViaJsoupConnect(@Nonnull String url, @Nonnull String word) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            int code = con.getResponseCode();
            Document doc2 = Jsoup.parse(obj, 10000);
            String result = doc2.toString();
//        System.out.println(result);
//            System.out.println("Responce code is: " + code);
            HttpReport report = new HttpReport(result.contains(word), code, HttpStatus.SUCCESS, url);
            return report;
        }catch (SocketTimeoutException e){
            return new HttpReport(false, 0, HttpStatus.TIMEOUT, url);
        }catch (IOException e){
            return new HttpReport(false, 0, HttpStatus.ERROR, url);
        }
    }
}
