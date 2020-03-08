package com.timeith.commons.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

public class MapperWrapper {
	public static final ObjectMapper MAPPER = new ObjectMapper();
	public static final ObjectWriter WRITER = new ObjectMapper()
			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).writerWithDefaultPrettyPrinter();

	public static <T> T readWithTypeReference(String fileName, TypeReference<T> typeReference) throws IOException {
		T readTo = null;
		try {
			readTo = MapperWrapper.MAPPER.readValue(new FileReader(fileName), typeReference);
		} catch (IOException e) {
			throw e;
		}
		return readTo;
	}

	public static <T> T readWithClassRefence(String fileName, Class<T> classRefence) throws IOException {
		T readTo = null;
		try {
			readTo = MapperWrapper.MAPPER.readValue(new FileReader(fileName), classRefence);
		} catch (IOException e) {
			throw e;
		}
		return readTo;
	}

	public static <T> List<T> readAsList(String fileName, Class<T[]> classRefence) throws IOException{
		List<T> itemList = new ArrayList<T>();
			try {
				itemList = Arrays.asList(MapperWrapper.MAPPER.readValue(new FileReader(fileName), classRefence));
			} catch (IOException e) {
				throw e;
			}
		return itemList;
	}

	public static <T> Boolean writeWithClassRefence(String fileName, T classRefence) throws IOException {
		Boolean isWritten = false;
		try {
			MapperWrapper.WRITER.writeValue(new File(fileName), classRefence);
			isWritten = true;
		} catch (IOException e) {
			throw e;
		}
		return isWritten;
	}

}
