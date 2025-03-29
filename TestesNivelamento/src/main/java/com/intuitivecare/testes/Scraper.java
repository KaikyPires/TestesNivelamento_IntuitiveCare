package com.intuitivecare.testes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;

public class Scraper {

    public static void main(String[] args) {
        String URL = "https://example.com"; // Insira a URL que deseja fazer scraping

        try {
            Document doc = Jsoup.connect(URL).get();
            Elements links = doc.select("a[href$=.pdf]");

            for (Element link : links) {
                String fileUrl = link.absUrl("href");
                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                System.out.println("Baixando: " + fileName);
                downloadFile(fileUrl, fileName);
            }

            System.out.println("Download concluído!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String fileUrl, String fileName) {
        try {
            FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
