import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object SparkJob {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder
      .appName("Spark Job")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "data_stream")
      .load()
      .selectExpr("CAST(value AS STRING)")

    val processedDF = df
      .withColumn("processed_value", expr("some_processing_function(value)"))

    val query = processedDF.writeStream
      .outputMode("append")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
