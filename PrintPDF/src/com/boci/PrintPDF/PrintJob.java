package com.boci.PrintPDF;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintJob  {

	
	public static void main(String[] args) throws PrinterException{
		PrinterJob pj = PrinterJob.getPrinterJob();
		PageFormat pf = pj.defaultPage();
	    Paper paper = new Paper();
	    double margin = 9;
	    paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight()
	        - margin * 2);
	    pf.setPaper(paper);
	    
	  pj.print();
	}

}
