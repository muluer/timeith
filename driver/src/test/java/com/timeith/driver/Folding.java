package com.timeith.driver;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import scala.Tuple2;

public class Folding {

	public static void main(String[] args) {

		JavaSparkContext spark = SparkDriver.jsc();
		List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
		JavaRDD<Integer> javaRDD = spark.parallelize(list1);
		
		int sum = javaRDD.fold(100, new Function2<Integer, Integer, Integer>() {
			private static final long serialVersionUID = 8074307594550200731L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {

				int interm = v1 + v2 > 105 ? 1 : v1 + v2;
				return interm;
			}
		});

		System.out.println(sum);
		
		List<Tuple2<Integer,Integer>> list2 = Arrays.asList(
				new Tuple2<>(1,11), 
				new Tuple2<>(1,13), 
				new Tuple2<>(1,17), 
				new Tuple2<>(2,19), 
				new Tuple2<>(2,23), 
				new Tuple2<>(2,29), 
				new Tuple2<>(3,31),
				new Tuple2<>(3,37),
				new Tuple2<>(3,41));
		JavaPairRDD<Integer, Integer> javaPairRDD = spark.parallelizePairs(list2);
		
		JavaPairRDD<Integer, Integer> sums = javaPairRDD.foldByKey(0, new Function2<Integer, Integer, Integer>() {

			private static final long serialVersionUID = 2784963752430401373L;

			@Override
			public Integer call(Integer v1, Integer v2) throws Exception {
				// TODO Auto-generated method stub
				return v1+v2;
			}
		});
		
		List<Tuple2<Integer, Integer>> list3 = sums.collect();
		for (Tuple2<Integer, Integer> tuple2 : list3) {
			System.out.println(tuple2._1() + " : " + tuple2._2());			
		}
		

	}
}
