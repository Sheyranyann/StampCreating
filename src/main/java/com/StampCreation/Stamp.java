package com.StampCreation;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Stamp {
    private final String templatePath = "src/main/resources/StampTemplate.png";
    private final ImagePlus template = IJ.openImage(templatePath);
    public Stamp(String signature) {
        ImageProcessor processor = template.getProcessor();
        processor.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.ITALIC, 60);
        processor.setFont(font);
        FontMetrics metrics = processor.getFontMetrics();
        int abscissa = (template.getWidth() - metrics.stringWidth(signature)) / 2;
        int ordinate = (template.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
        processor.drawString(signature, abscissa, ordinate);
    }

    public void addToPDF(String inputPdfPath, String outputPdfPath) throws IOException{
        BufferedImage image = template.getProcessor().getBufferedImage();
        ImageIO.write(image, "png", new File(outputPdfPath));
        try (PDDocument document = PDDocument.load(new File(inputPdfPath))) {
            float scalingFactor = 0.25F;
            float imageWidth = image.getWidth() * scalingFactor;
            float imageHeight = image.getHeight() * scalingFactor;
            float x = 20;
            float y = 20;
            try (PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(document.getNumberOfPages() - 1), PDPageContentStream.AppendMode.APPEND, true)) {
                PDImageXObject pdImageXObject = LosslessFactory.createFromImage(document, image);
                contentStream.drawImage(pdImageXObject, x, y, imageWidth, imageHeight);
            }
            document.save(new File(outputPdfPath));
        }
    }
}
