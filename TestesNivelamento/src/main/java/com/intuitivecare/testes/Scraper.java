package com.intuitivecare.testes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
            baixarArquivos(document);
            
            // Compactar os arquivos baixados
            compactarArquivos("TestesNivelamento/ArquivosCompactados.zip");
        } catch (IOException e) {
            logger.log(Level.SEVERE,
                    "Erro ao conectar na URL ou baixar os arquivos. Verifique a URL e a sua conexão com a internet.",
                    e);
        }
    }

    public static Document conectarUrl(String url) throws IOException {
        logger.info("Conectando na URL: " + url);
        return Jsoup.connect(url).get();
    }

    public static void baixarArquivos(Document document) {
        List<String> arquivosDesejados = Arrays.asList(
                "Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf",
                "Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf"
        );

        Elements links = document.select("a[href$=.pdf]");
        String caminhoDestino = "TestesNivelamento/";
        File diretorio = new File(caminhoDestino);
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // Cria a pasta se não existir
        }

        for (Element link : links) {
            String urlArquivo = link.absUrl("href");
            String nomeArquivo = urlArquivo.substring(urlArquivo.lastIndexOf("/") + 1);

            if (arquivosDesejados.contains(nomeArquivo)) {
                System.out.println("Iniciando download do arquivo: " + nomeArquivo);
                try {
                    URL website = new URL(urlArquivo);
                    HttpURLConnection connection = (HttpURLConnection) website.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    connection.setDoInput(true);
                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        try (InputStream inputStream = connection.getInputStream();
                             FileOutputStream outputStream = new FileOutputStream(caminhoDestino + nomeArquivo)) {

                            byte[] buffer = new byte[8192];
                            int bytesRead;
                            long totalBytesRead = 0;

                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                                totalBytesRead += bytesRead;
                            }

                            outputStream.flush();

                            if (totalBytesRead > 0) {
                                System.out.println("Download concluído com sucesso: " + nomeArquivo + " (Tamanho: " + totalBytesRead + " bytes)");
                            } else {
                                System.out.println("Download falhou: O arquivo está vazio.");
                            }
                        }
                    } else {
                        System.out.println("Erro ao baixar o arquivo: " + nomeArquivo + ". Código de resposta HTTP: " + connection.getResponseCode());
                    }

                    connection.disconnect();
                } catch (IOException e) {
                    System.err.println("Erro ao baixar o arquivo: " + e.getMessage());
                }
            } else {
                System.out.println("Arquivo ignorado: " + nomeArquivo);
            }
        }
    }

    public static void compactarArquivos(String nomeArquivoZip) {
        File diretorio = new File("TestesNivelamento/");
        File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".pdf") && new File(dir, name).isFile());

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo PDF encontrado na pasta TestesNivelamento para compactar.");
            return;
        }

        try {
            FileOutputStream fos = new FileOutputStream(nomeArquivoZip);
            ZipOutputStream zipOut = new ZipOutputStream(fos);

            for (File fileToZip : arquivos) {
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }

                    System.out.println("Arquivo adicionado ao zip: " + fileToZip.getName());
                }
            }

            zipOut.close();
            fos.close();
            System.out.println("Compactação concluída com sucesso: " + nomeArquivoZip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
