package utils;

import model.Billing;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.FileOutputStream;

public class PDFGenerator {

    public static void generateBillPDF(Billing bill) {

        try {

            // Create Bills folder if it doesn't exist
            File folder = new File("Bills");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String fileName = "Bills/Bill_" + bill.getId() + ".pdf";

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(fileName)
            );

            document.open();

            Font titleFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    20
            );

            Font normalFont = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    14
            );

            document.add(new Paragraph("CITY HOSPITAL", titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Hospital Billing Invoice", normalFont));
            document.add(new Paragraph("------------------------------------------------"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Bill ID : " + bill.getId(), normalFont));
            document.add(new Paragraph("Patient ID : " + bill.getPatientId(), normalFont));
            document.add(new Paragraph("Doctor Charge : ₹ " + bill.getDoctorCharge(), normalFont));
            document.add(new Paragraph("Medicine Charge : ₹ " + bill.getMedicineCharge(), normalFont));
            document.add(new Paragraph("Room Charge : ₹ " + bill.getRoomCharge(), normalFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total Amount : ₹ " + bill.getTotalAmount(), titleFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Payment Status : " + bill.getPaymentStatus(), normalFont));
            document.add(new Paragraph("Bill Date : " + bill.getBillDate(), normalFont));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("------------------------------------------------"));
            document.add(new Paragraph("Thank you for choosing City Hospital.", normalFont));

            document.close();

            JOptionPane.showMessageDialog(
                    null,
                    "PDF Generated Successfully!\nSaved as:\n" + fileName
            );

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Error generating PDF:\n" + e.getMessage()
            );
        }
    }
}