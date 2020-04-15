package com.timeith.driver;


import org.apache.spark.SparkConf;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class SingleLineJson 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(SingleLineJson.class);
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
        String path = "D:\\covid\\datatsets\\data.europa.eu_20200411\\COVID-19-geographic-disbtribution-worldwide_4Rows.json";
        Dataset<Row> covidDataset1 = spark.read().json(path);
		covidDataset1.printSchema();
		try {
			covidDataset1.createTempView("covid19");
		} catch (AnalysisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dataset<Row> dataTurkey = spark.sql("select * from covid19 where covid19.countriesAndTerritories=\"Afghanistan\"");
		dataTurkey.show();
		
        System.out.println("Ok..");
        
        spark.close();
        LOGGER.info("stoppinng driver.." );
    }
}
