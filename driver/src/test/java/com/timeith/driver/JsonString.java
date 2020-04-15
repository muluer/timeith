package com.timeith.driver;


import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class JsonString 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonString.class);
    public static void main( String[] args )
    {
        LOGGER.info("starting driver.." );
        SparkConf conf = new SparkConf()
        		.setAppName("Covid19")
        		.setMaster("local[3]");
        		
        SparkSession spark= SparkSession
        		.builder()
        		.config(conf)
        		.getOrCreate();
        
        List<String> jsonData = Arrays.asList(
                "{\"name\":\"Mehmet\",\"address\":{\"city\":\"CAnkara\",\"state\":\"TR\"}}");
        Dataset<String> anotherPeopleDataset = spark.createDataset(jsonData, Encoders.STRING());
        Dataset<Row> anotherPeople = spark.read().json(anotherPeopleDataset);
        anotherPeople.show();

        System.out.println("Ok..");
        
        spark.close();
        LOGGER.info("stoppinng driver.." );
    }
}
