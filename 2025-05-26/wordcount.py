from pyspark import SparkConf
from pyspark import SparkContext

inputFilename = 'alice.txt'

conf = (SparkConf()
        .setAppName("WordCount")
        .setMaster("local"))
spark = SparkContext(conf=conf)

lines = spark.textFile(inputFilename)

words = lines.flatMap(lambda line: line.split(" "))
# print(words.take(50))

words_with_ones = words.map(lambda word: (word, 1))
print(words_with_ones.take(50))

# reduceByKey assumes key to aggregate is first of
# tuple
counts = words_with_ones.reduceByKey(lambda a, b: a+b)
