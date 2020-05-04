package com.timeith.driver;


import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.desc;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.round;
import static org.apache.spark.sql.functions.row_number;
import static org.apache.spark.sql.functions.sum;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.types.DataTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * covid19 analysis
 *
 */
public class CovidAnalysis {
	private static final Logger LOGGER = LoggerFactory.getLogger(CovidAnalysis.class);
	private static Dataset<Row> sortedDeathRecords, sortedCasesRecords, sortedPecentageRecords, worldTotal;	


	public static void main(String[] args) {
		
		LOGGER.info("starting driver..");
		SparkSession spark= SparkDriver.getOrCreate();
		
		String path = "src/main/resources/";
		String datasetFile = path + "COVID19_20200504.json";
        
        Dataset<Row> covidDataset = spark
        		.read()
        		.option("multiline", true)
        		.json(datasetFile);
      
        Dataset<Row> groupedRecords = covidDataset
        		.select(explode(col("records")).alias("country total"))
        		.withColumn("country", col("country total").getField("countriesAndTerritories"))
        		.withColumn("cases", col("country total").getField("cases").cast(DataTypes.IntegerType))
				.withColumn("deaths", col("country total").getField("deaths").cast(DataTypes.IntegerType))
				.groupBy("country")
				//.map
				.agg(sum("cases").alias("country total cases"), sum("deaths").alias("country total deaths"))
        		.withColumn("percent", round(col("country total deaths").divide(col("country total cases")), 2))
				.cache();
        
        sortedDeathRecords = groupedRecords
        		.withColumn("idx (sorted deaths)", row_number().over(Window.orderBy(desc("country total deaths"))))
        		.select(col("idx (sorted deaths)"), col("country"), col("country total cases"), col("country total deaths"), col("percent"));

        sortedCasesRecords = groupedRecords
        		.withColumn("idx (sorted cases)", row_number().over(Window.orderBy(desc("country total cases"))))
        		.select(col("idx (sorted cases)"), col("country"), col("country total cases"), col("country total deaths"), col("percent"));

        sortedPecentageRecords = groupedRecords
        		.withColumn("idx (sorted percent)", row_number().over(Window.orderBy(desc("percent"))))
        		.select(col("idx (sorted percent)"), col("country"), col("country total cases"), col("country total deaths"), col("percent"));

        worldTotal = groupedRecords
        		.agg(sum(col("country total cases")).alias("world total cases"), sum(col("country total deaths")).alias("world total deaths"))
        		.select(col("world total cases"), col("world total deaths"), round(col("world total deaths").divide(col("world total cases")), 2).alias("percent"));

        sortedDeathRecords.write().format("csv").mode(SaveMode.Overwrite).save(path + "sortedDeathRecords.csv");
        sortedCasesRecords.write().format("csv").mode(SaveMode.Overwrite).save(path + "sortedCasesRecords.csv");
        sortedPecentageRecords.write().format("csv").mode(SaveMode.Overwrite).save(path + "sortedPecentageRecords.csv");
        worldTotal.write().format("csv").mode(SaveMode.Overwrite).save(path + "worldTotal.csv");

        int count = (int) groupedRecords.count();
        print(count);

        LOGGER.info("stopping driver.." );
        spark.close();
    }

    private static void print(int count) {
		sortedDeathRecords.show(count, false);
        sortedCasesRecords.show(count, false);
        sortedPecentageRecords.show(count, false);
        sortedPecentageRecords
        		.filter(sortedPecentageRecords.col("country total cases").gt(1000))
        		.filter(sortedPecentageRecords.col("country total deaths").gt(100))
				.select(row_number().over(Window.orderBy(desc("percent"))).alias("idx (filtered)"), 
						col("country"),
						col("country total cases"), 
						col("country total deaths"), 
						col("percent"))
        		.show(count, false);        		

        worldTotal.show(false);	
	}
}
