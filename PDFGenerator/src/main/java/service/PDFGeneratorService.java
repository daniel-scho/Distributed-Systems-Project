package service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dto.Customer;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGeneratorService {

    public void generateAndSavePDF(Customer customer) {
        try {
            // Erstellen eines neuen PDF-Dokuments
            Document document = new Document();

            // Definieren des Dateinamens für die gespeicherte PDF
            String fileName = "..\\Invoice.pdf";

            // Erstellen einer PdfWriter-Instanz, um das Dokument in eine PDF-Datei zu schreiben
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            // Öffnen des Dokuments zum Schreiben
            document.open();
        // Hinzufügen einer Überschrift zum PDF-Dokument
            Font headingFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph heading = new Paragraph("Invoice", headingFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            heading.setSpacingAfter(20);
            document.add(heading);
            // Schreiben des JSON-Inhalts als Paragraph in das PDF-Dokument
            document.add(new Paragraph(customer.toString()));

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
