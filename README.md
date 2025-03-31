# Testes de Nivelamento - IntuitiveCare

Este projeto é um scraper desenvolvido em Java para baixar arquivos PDF específicos de uma URL fornecida e compactá-los em um arquivo ZIP. O projeto inclui testes unitários para garantir o funcionamento adequado das funcionalidades principais.

## 📌 Funcionalidades

-  Download de arquivos PDF específicos (`Anexo_I` e `Anexo_II`).
-  Compactação dos arquivos baixados em um arquivo ZIP.
-  Testes unitários para verificar o download e a compactação dos arquivos.


---

##  Tecnologias Utilizadas

- Java 17
- Jsoup (para web scraping)
- Maven (para gerenciamento de dependências e execução de testes)

##  Dependências

No arquivo `pom.xml` estão especificadas as dependências necessárias. As principais são:

- Jsoup - Para realizar o scraping da página e extrair links de arquivos PDF.
- JUnit - Para testes unitários.
- Apache Commons IO - Para manipulação de arquivos.

