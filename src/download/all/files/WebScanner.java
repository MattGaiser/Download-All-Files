/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package download.all.files;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import javafx.scene.text.Text;

public class WebScanner {

    /**
     * @param args the command line arguments
     */
    private List<String> links = new LinkedList<String>();
    private Document webPage;
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1";

    public int initiateSearch(String startURL, String toFind, File directory, Text message) {

        AtomicInteger numberOfFiles = new AtomicInteger();
        Connection get = Jsoup.connect(startURL).userAgent(userAgent);
        get.referrer("http://www.google.com");
        try {
            webPage = get.get();
        } catch (IOException ex) {
            Logger.getLogger(WebScanner.class.getName()).log(Level.SEVERE, null, ex);
        }
        Elements linked = webPage.select("a[href]");
        linked.stream().forEach((link) -> {
            if (urlCheck(link.absUrl("href"), toFind) == true) {
                //System.out.println(link.absUrl("href"));
                URL url = null;
                String name = extractName(link.absUrl("href"));
                //System.out.println(name);
                File save = new File(directory, name);
                try {
                    url = new URL(link.absUrl("href"));
                } catch (MalformedURLException ex) {
                    //System.out.println("Malformed URL Exception");
                }
                try {
                    FileUtils.copyURLToFile(url, save);
                    //message.setText("Downloading: " + url.toString());
                    numberOfFiles.incrementAndGet();
                } catch (IOException ex) {
                    //System.out.println("IOException");
                }

            }
        });

        return numberOfFiles.get();
    }

    private boolean urlCheck(String link, String toFind) {
        String[] pageCheck = {toFind};
        //System.out.println(link);
        for (int i = 0; i < pageCheck.length; i++) {

            if (link.toLowerCase().contains(pageCheck[i]) == true) {
                return true;
            }

        }
        return false;
    }

    private String extractName(String name) {

        String[] nameSplit;
        nameSplit = name.split("/");
        return nameSplit[nameSplit.length - 1];

    }

}
