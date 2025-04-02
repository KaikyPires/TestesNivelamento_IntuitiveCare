import pdfplumber
import csv
import os
import zipfile

def extrair_tabela(pdf_path, csv_path):

    all_rows = []
    with pdfplumber.open(pdf_path) as pdf:
        for page in pdf.pages:
            # Extrai todas as tabelas de cada página
            tables = page.extract_tables()
            for table in tables:
                for row in table:
                    row = [cell if cell is not None else "" for cell in row]
                    all_rows.append(row)


    new_rows = []
    for row in all_rows:
        nova_linha = []
        for cell in row:
            # Substitui somente se for exatamente "OD" ou "AMB"
            palavras = cell.split()
            for i, p in enumerate(palavras):
                if p == "OD":
                    palavras[i] = "Seg. Odontológica"
                elif p == "AMB":
                    palavras[i] = "Seg. Ambulatorial"
            cell_substituido = " ".join(palavras)

            nova_linha.append(cell_substituido)
        new_rows.append(nova_linha)

    # CSV delimitado por ;
    with open(csv_path, 'w', encoding='utf-8-sig', newline='') as f:
        writer = csv.writer(f, delimiter=';')
        writer.writerows(new_rows)

def compactar_csv(csv_path, zip_path):

    with zipfile.ZipFile(zip_path, 'w', zipfile.ZIP_DEFLATED) as zipf:
        zipf.write(csv_path, os.path.basename(csv_path))

def main():
    pdf_path = "../Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf"
    csv_path = "../tabela_rol_procedimentos.csv"
    zip_path = "../Teste_Kaiky.zip"

    extrair_tabela(pdf_path, csv_path)
    print(f"CSV gerado em: {csv_path}")

    compactar_csv(csv_path, zip_path)
    print(f"Arquivo ZIP gerado em: {zip_path}")

if __name__ == "__main__":
    main()
