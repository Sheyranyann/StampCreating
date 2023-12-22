package com.StampCreation;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Stamp stamp = createStamp();
        addToPdf(stamp);
    }
    private static Stamp createStamp() {
        String signature;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Input your signature: ");
            signature = sc.nextLine();
        } while (signature.length() > 3 || signature.length() == 0);
        return new Stamp(signature);
    }

    private static void addToPdf(Stamp stamp) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input path of pdf file to add stamp: ");
        String inputPdfPath = sc.nextLine();
        System.out.print("Input path to save pdf file with stamp: ");
        String outputPdfPath = sc.nextLine();
        try {
            stamp.addToPDF(inputPdfPath, outputPdfPath);
        } catch(IOException e) {
            System.out.println("Error loading or processing PDF");
        }
    }

}
