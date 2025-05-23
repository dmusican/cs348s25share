import random
import time
import sys
import numpy as np

# Data clustering. Implementation by Dave Musicant

TOL = 0.1

# Grab the data
def importData():
    filename = "/opt/data/clusdata.txt"

    with open(filename) as file:
        allrows = [[float(value) for value in line.strip().split()] for line in file]
        return np.array(allrows)

def cluster(data, k):
    sourceRank = 0

    rows = len(data)
    cols = len(data[0])

    # Initially assign all points to no cluster at all
    assignments = np.ones(rows, 'i') * -1
    rng = np.random.default_rng(seed=90125)

    # Initial centers are random points
    centers = rng.random((k,cols))*20 - 10


    # Kick off main loop with crazy large errors to get started
    prevError = np.array([sys.float_info.max])
    currError = prevError / 2

    initialTime = time.time()

    numIterations = 0
    while (currError < prevError - TOL):
        numIterations += 1

        prevError = currError
        currError = np.zeros(1)

        # Assign all clusters to nearest centers.
        for i in range(rows):
            closest = -1
            minDist = float('inf')
            for j in range(k):
                dist = sum((data[i]-centers[j])**2)
                if dist <= minDist:
                    minDist = dist
                    closest = j
            assignments[i] = closest
            currError += minDist

        # Assign new centroids for each cluster
        numPoints = np.zeros(k)
        total = np.zeros((k,cols))

        # Aggregate each cluster
        for i in range(rows):
            cluster = assignments[i]
            numPoints[cluster] += 1
            total[cluster] += data[i]

        # Calculate averages. If a cluster is empty, just pick a random point
        # as the new cluster center.
        # This is not a great strategy, but good enough and the code is
        # short (and more easily parallelizable.)
        print("Total Clustering error:", currError)
        for cluster in range(k):
            if numPoints[cluster] > 0:
                centers[cluster] = total[cluster] / numPoints[cluster]
            else:
                print("Empty cluster happened");
                newCenterIndex = rng.integers(0, rows)
                centers[cluster] = data[newCenterIndex]


    print("Total number of iterations =", numIterations)
    print("Average time per iteration =  ",
                (time.time() - initialTime)/numIterations)

    return centers

def main():
    dataset = importData()
    k = 3
    centers = cluster(dataset, k)
    print(centers)

if __name__=='__main__':
    main()
