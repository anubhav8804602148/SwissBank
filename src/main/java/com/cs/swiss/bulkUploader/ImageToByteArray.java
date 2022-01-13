package com.cs.swiss.bulkUploader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageToByteArray {
	public static void main(String ar[]) throws IOException {
		File fi = new File("C:\\Users\\Anubhav\\Pictures\\Saved Pictures\\luffy.png");
		byte[] fileContent = Files.readAllBytes(fi.toPath());
		for(byte b : fileContent) System.out.println(b);
	}
}
