package com.timeith.driver;


import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class MultiLineJson
{
	private static final Logger LOGGER = LoggerFactory.getLogger(MultiLineJson.class);
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
        
        // Single object per line
        String path = "D:\\covid\\datatsets\\data.europa.eu_20200411\\COVID-19-geographic-disbtribution-worldwide_ROWS.json";
        Dataset<Row> covidDataset = spark.read().option("multiline", true).json(path);
        covidDataset.printSchema();
        Dataset<Row> covidDataset1 = covidDataset.groupBy("countriesAndTerritories").sum("cases");
        covidDataset1.show();
		
        System.out.println("Ok..");
        
        spark.close();
        LOGGER.info("stoppinng driver.." );
    }
}
