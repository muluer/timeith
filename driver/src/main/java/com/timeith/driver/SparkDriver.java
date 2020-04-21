package com.timeith.driver;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkDriver {

	private static final Logger LOGGER = LoggerFactory.getLogger(SparkDriver.class);

	public static SparkConf conf() {
        SparkConf conf = new SparkConf()
        		.setAppName("test01")
        		.setMaster("local[5]");
        return conf;
	}
	
	public static SparkSession getOrCreate() {
		LOGGER.info("starting driver..");
        SparkSession spark= SparkSession
        		.builder()
        		.config(conf())
        		.getOrCreate();

		return spark;
	}
	
	public static JavaSparkContext jsc() {
		JavaSparkContext jsc = new JavaSparkContext(conf());
		return jsc;
	}
}
