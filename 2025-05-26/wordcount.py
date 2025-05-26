from pyspark import SparkConf
from pyspark import SparkContext

inputFilename = 'alice.txt'

conf = (SparkConf()
        .setAppName("WordCount")
        .setMaster("local"))
