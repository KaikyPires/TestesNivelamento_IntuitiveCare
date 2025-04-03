# Testes de Nivelamento - IntuitiveCare

Este repositório contém as soluções para **Testes 1 e 2**:  
- **Teste 1**: Criação de um scraper em Java para baixar arquivos PDF (Anexo I e II) e compactar em ZIP.  
- **Teste 2**: Extração de dados do Anexo I em Python, transformando em CSV e compactando em ZIP.

## Teste: Web Scraping

- **Download de arquivos PDF específicos** (ex.: `Anexo_I` e `Anexo_II`).
- **Compactação** dos arquivos baixados em um arquivo ZIP.
- **Testes unitários** para verificar o download e a compactação.
- **Script Python** (opcional) que extrai dados do `Anexo_I` e salva em CSV.

---

## Teste: Transformação de Dados (Python)

Além do projeto em Java, há um script Python em python_scripts/, que realiza:

1. **Extração de dados** do PDF `Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf` usando **pdfplumber**.  
2. **Geração de um CSV** (codificado em UTF-8 BOM).  
3. **Substituição** das abreviações `OD` e `AMB` conforme legenda.  
4. **Compactação** do CSV em um ZIP **(Teste_Kaiky.zip dentro de TesteNivelamento)**.

---

## Tecnologias Utilizadas

- Java 17 
- Jsoup (para web scraping)  
- Maven (gerenciamento de dependências e testes)  
- Python + pdfplumber(extração tabular do PDF, no teste de transformaçao de dados)

---

## Dependências

No arquivo `pom.xml`, estão especificadas as dependências necessárias para o projeto Java. As principais são:

- Jsoup: Para realizar o scraping da página e extrair links de PDFs.  
- JUnit: Para testes unitários.

---

## Como Executar o Scrapper

1. Tenha Java na sua maquina 
2. Rode Scraper.java (ou via Maven) para baixar os arquivos.
3. Executar Testes em Java
    mvn test

---

## Como Executar o Script Python

1. Tenha python na sua maquina 
2. Instale `pdfplumber`:
   pip install pdfplumber
3. Executar:
    python transformar_pdf.py


