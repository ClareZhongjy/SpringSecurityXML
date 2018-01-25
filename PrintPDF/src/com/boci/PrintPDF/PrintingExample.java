package com.boci.PrintPDF;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageable;




public class PrintingExample {

    public static void main(String args[]) throws Exception {

        PDDocument document = PDDocument.load(new File("D:/Archive/A2.pdf"));
        
        PrintService myPrintService = findPrintService("Microsoft XPS Document Writer");

        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper paper = new Paper();
        double margin = 4.5; 
        paper.setImageableArea((margin*2),(margin*2), paper.getWidth() - margin * 2, paper.getHeight()
            - margin * 2);
        pf.setPaper(paper);
        
        job.setPageable(new PDPageable(document));
        
        job.setPrintService(myPrintService);
        job.print();

    }       

    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
}