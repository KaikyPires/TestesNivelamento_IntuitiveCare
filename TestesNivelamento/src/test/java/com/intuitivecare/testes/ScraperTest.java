package com.intuitivecare.testes;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScraperTest {

    private static final String URL = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
    private static final String TEST_FOLDER = "TestesNivelamento/";
    private static final String ZIP_FILE = TEST_FOLDER + "ArquivosCompactados.zip";

    @Before
    public void setUp() {
        File dir = new File(TEST_FOLDER);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @After
    public void tearDown() {
        File[] arquivos = new File(TEST_FOLDER).listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (!arquivo.getName().equals("ArquivosCompactados.zip")) { 
                    arquivo.delete();
                }
            }
        }

        // Excluir arquivo ZIP após o teste
        File zipFile = new File(ZIP_FILE);
        if (zipFile.exists()) {
            zipFile.delete();
        }
    }

    @Test
    public void testBaixarArquivosDesejados() throws IOException {

        Scraper.baixarArquivos(Scraper.conectarUrl(URL));

        String[] arquivosEsperados = {
            "Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf",
            "Anexo_II_DUT_2021_RN_465.2021_RN628.2025_RN629.2025.pdf"
        };

        for (String nomeArquivo : arquivosEsperados) {
            File arquivo = new File(TEST_FOLDER + nomeArquivo);
            assertTrue("O arquivo não foi baixado: " + nomeArquivo, arquivo.exists());
            assertTrue("O arquivo está vazio: " + nomeArquivo, arquivo.length() > 0);
        }
    }

    @Test
    public void testCompactarArquivos() throws IOException {
        Scraper.baixarArquivos(Scraper.conectarUrl(URL));
        Scraper.compactarArquivos(ZIP_FILE);

        File arquivoZip = new File(ZIP_FILE);
        assertTrue("O arquivo ZIP não foi criado.", arquivoZip.exists());
        assertTrue("O arquivo ZIP está vazio.", arquivoZip.length() > 0);
    }
}
