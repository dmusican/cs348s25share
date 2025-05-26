from pyspark.sql import SparkSession
from pyspark.sql import DataFrame
import pyspark.sql.functions as f

inputFilename = 'alice.txt'

spark = (SparkSession
         .builder
         .appName("WordCount")
         .getOrCreate())

lines = spark.read.text(inputFilename)


result = (lines
          .select(f.explode(f.split('value', ' '))
                  .alias("word"))
          .groupBy('word').count()
          )

result.show()

