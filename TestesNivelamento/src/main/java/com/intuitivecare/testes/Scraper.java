package com.intuitivecare.testes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scraper {

    private static final Logger logger = Logger.getLogger(Scraper.class.getName());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a URL da página que deseja fazer scraping:");
        String url = scanner.nextLine();
        scanner.close();

        if (url.isEmpty()) {
            logger.severe("A URL não foi fornecida. Encerrando o programa.");
            return;
        }

        logger.info("Iniciando o scraping da URL: " + url);

        try {
            Document document = conectarUrl(url);
            Elements links = extrairLinks(document);

            if (links.isEmpty()) {
                logger.warning("Nenhum link de PDF encontrado na URL fornecida.");
            } else {
                logger.info("Total de PDFs encontrados: " + links.size());
                baixarArquivos(links);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,
                    "Erro ao conectar na URL ou baixar os arquivos. Verifique a URL e a sua conexão com a internet.",
                    e);
        }
    }

    private static Document conectarUrl(String url) throws IOException {
        logger.info("Conectando na URL: " + url);
        return Jsoup.connect(url).get();
    }

    private static Elements extrairLinks(Document document) {
        logger.info("Extraindo links de arquivos PDF...");
        return document.select("a[href$=.pdf]");
    }

    private static void baixarArquivos(Elements links) {
        for (Element link : links) {
            String fileUrl = link.absUrl("href");
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            // Filtra os arquivos que você deseja baixar
            if (fileName.contains("Anexo_I") || fileName.contains("Anexo_II")) {

                logger.info("Iniciando download do arquivo: " + fileName);

                try {
                    downloadFile(fileUrl, fileName);
                    logger.info("Download concluído: " + fileName);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Erro ao baixar o arquivo: " + fileName, e);
                }
            } else {
                logger.info("Arquivo ignorado: " + fileName);
            }
        }
    }

    private static void downloadFile(String fileUrl, String fileName) throws IOException {
        String caminhoDestino = "TestesNivelamento/";
        File diretorio = new File(caminhoDestino);
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // Cria a pasta se não existir
        }

        File destino = new File(caminhoDestino + fileName);
        FileOutputStream outputStream = new FileOutputStream(destino);

    }
}
