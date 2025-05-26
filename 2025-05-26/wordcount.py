from pyspark import SparkConf
from pyspark import SparkContext

inputFilename = 'alice.txt'

conf = (SparkConf()
        .setAppName("WordCount")
        .setMaster("local"))
spark = SparkContext(conf=conf)

lines = spark.textFile(inputFilename)

words = lines.flatMap(lambda line: line.split(" "))
print(words.take(50))
