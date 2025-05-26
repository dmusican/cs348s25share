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
          )

result.show()

# words = lines.flatMap(lambda line: line.split(" "))
# # print(words.take(50))

# words_with_ones = words.map(lambda word: (word, 1))
# #print(words_with_ones.take(50))

# # reduceByKey assumes key to aggregate is first of
# # tuple
# counts = (words_with_ones
#           .reduceByKey(lambda a, b: a+b)  #type: ignore
#           )
# print(counts.take(10))
