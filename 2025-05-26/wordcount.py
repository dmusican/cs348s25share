from pyspark import SparkConf
from pyspark import SparkContext

inputFilename = 'alice.txt'

conf = (SparkConf()
        .setAppName("WordCount")
        .setMaster("local"))
spark = SparkContext(conf=conf)

lines = spark.textFile(inputFilename)

print(lines)
