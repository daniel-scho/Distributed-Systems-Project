package service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dto.Customer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFGeneratorService {

    private DatabaseQueryExecuter dbClient;
    public PDFGeneratorService(DatabaseQueryExecuter dbCleint) {
        this.dbClient = dbCleint;
    }

    public void generateAndSavePDF(Customer customer) {
        customer = dbClient.getCustomerData(customer);

        if (customer == null) {
            System.out.println("Customer not found - PDF not generated");
            return;   // Customer does not exits -> no PDF is generated
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        try {
            Document document = new Document();

            // Define path
            String fileName = "..\\Invoice.pdf";

            // Erstellen einer PdfWriter-Instanz, um das Dokument in eine PDF-Datei zu schreiben
            PdfWriter.getInstance(document, new FileOutputStream(fileName));

            // Öffnen des Dokuments zum Schreiben
            document.open();
            // Add header
            Font headingFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph heading = new Paragraph("Invoice", headingFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            heading.setSpacingAfter(20);
            document.add(heading);

            document.add(new Paragraph("Date: " + formattedDateTime));
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
