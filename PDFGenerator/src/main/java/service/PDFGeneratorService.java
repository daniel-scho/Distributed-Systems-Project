package service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGeneratorService {

    public void generateAndSavePDF(String customerData) {
        try {
            // Erstellen eines neuen PDF-Dokuments
            Document document = new Document();

            // Definieren des Dateinamens für die gespeicherte PDF
            String fileName = "..\\Invoice.pdf";

            // Erstellen einer PdfWriter-Instanz, um das Dokument in eine PDF-Datei zu schreiben
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            // Öffnen des Dokuments zum Schreiben
            document.open();

            // Schreiben des JSON-Inhalts als Paragraph in das PDF-Dokument
            document.add(new Paragraph(customerData));

            // Schließen des Dokuments
            document.close();

            System.out.println("PDF generated and saved successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
