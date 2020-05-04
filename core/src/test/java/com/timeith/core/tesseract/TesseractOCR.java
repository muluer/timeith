package com.timeith.core.tesseract;

import static org.bytedeco.leptonica.global.lept.pixDestroy;
import static org.bytedeco.leptonica.global.lept.pixRead;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;

public class TesseractOCR {

	public static void main(String[] args) {

		final String DATAPATH = "src/main/resources/lang";
		final String IMGPATH = "src/main/resources/image/tth001.JPG";
		final String LANGUAGE = "tur";

		BytePointer outText = null;
		TessBaseAPI api = new TessBaseAPI();
		PIX image = null;

		try {
			int res = api.Init(DATAPATH, LANGUAGE);
			if (res != 0) {
				System.out.println("Could not init Tesseract..");
			} else {
				image = pixRead(IMGPATH);
				api.SetImage(image);
				outText = api.GetUTF8Text();
				System.out.println("OCR output: " + outText.getString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			api.End();
			api.close();
			outText.deallocate();
			pixDestroy(image);
		}
	}
}
