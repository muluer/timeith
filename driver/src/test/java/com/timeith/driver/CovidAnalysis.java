package com.timeith.driver;


import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.desc;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.row_number;
import static org.apache.spark.sql.functions.sum;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.types.DataTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * covid analysis
 *
 */
public class CovidAnalysis
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CovidAnalysis.class);
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
        
        String path = "src/main/resources/COVID19_20200412.json";
        Dataset<Row> covidDataset = spark
        		.read()
        		.option("multiline", true)
        		.json(path);
      
        Dataset<Row> sortedRecords = covidDataset
        		.select(explode(col("records")).alias("total"))
        		.withColumn("country", col("total").getField("countriesAndTerritories"))
        		.withColumn("cases", col("total").getField("cases").cast(DataTypes.IntegerType))
				.withColumn("deaths", col("total").getField("deaths").cast(DataTypes.IntegerType))
				.groupBy("country")
				.agg(sum("cases").alias("total cases"), sum("deaths").alias("total deaths"))
				.withColumn("idx", row_number().over(Window.orderBy(desc("total deaths"))))
				.cache();
        
        Dataset<Row> indexedRecords = sortedRecords.select(col("idx"), col("country"), col("total cases"), col("total deaths"));
        indexedRecords.printSchema();
        indexedRecords.show(20, false);
     
        LOGGER.info("stopping driver.." );
        spark.close();
    }
}
