package com.timeith.examples.util;

import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.timeith.commons.util.MapperWrapper;
import com.timeith.models.NewsHMDL;

public class JSONReadWriteExample {

	private static int count = 2;
	private static String singleFileName = "src/main/resources/newsItem" + count + ".json";
	private static String listFileName = "src/main/resources/newsItemList" + count + ".json";

	public static void main(String[] args) {

		NewsHMDL newsItem = null;
		List<NewsHMDL> newsItemList = null;

		// write single item with class reference
		try {
			newsItem = new NewsHMDL(1, "tt" + 1, "ds" + 1, Date.from(Instant.now()), "gr" + 1, "li" + 1, "img" + 1);
			Boolean isWritten = MapperWrapper.writeWithClassRefence(singleFileName, newsItem);
			if (isWritten)
				System.out.println("Object is written to: " + singleFileName + ".. ");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// write list of items with class reference
		try {
			newsItemList = new ArrayList<NewsHMDL>();
			for (int i = 0; i < count; i++) {
				newsItem = new NewsHMDL(i, "tt" + i, "ds" + i, Date.from(Instant.now()), "gr" + i, "li" + i, "img" + i);
				newsItemList.add(newsItem);
			}
			Boolean isWritten = MapperWrapper.writeWithClassRefence(listFileName, newsItemList);
			if (isWritten)
				System.out.println("Object is written: " + listFileName + ".. ");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// self read
		try {
			newsItemList = new ArrayList<NewsHMDL>();
			newsItemList = MapperWrapper.MAPPER.readValue(new FileReader(listFileName),
					new TypeReference<List<NewsHMDL>>() {
					});
			newsItemList.stream().forEach(
					item -> System.out.println("self read, read from: " + listFileName + ".. " +"\n\t"+ item.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// read with class reference
		try {
			newsItem = MapperWrapper.readWithClassRefence(singleFileName, NewsHMDL.class);
			System.out.println("readWithClassRefence:, read from: " + singleFileName + ".. " +"\n\t"+ newsItem.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// read with type reference
		try {
			newsItemList = new ArrayList<NewsHMDL>();
			newsItemList = MapperWrapper.readWithTypeReference(listFileName, new TypeReference<List<NewsHMDL>>() {
			});
			newsItemList.stream().forEach(item -> System.out
					.println("readWithTypeReference, read from: " + listFileName + ".. " + "\n\t"+item.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// read 10x faster than type reference
		try {
			newsItemList = MapperWrapper.readAsList(listFileName, NewsHMDL[].class);
			newsItemList.stream().forEach(
					item -> System.out.println("readFaster, read from: " + listFileName + ".. " + "\n\t"+item.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
