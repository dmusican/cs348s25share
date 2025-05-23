from mpi4py import MPI
import sys
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.Get_rank()
size = comm.Get_size()


numItemsToTransfer = 1
sourceRank = 0
messageTag = 0  # supplemental tag, often just not used
numberToPlayWith = 5

buffer = np.zeros(1)
if rank == sourceRank:
    buffer[0] = numberToPlayWith * 3

comm.Bcast(buffer, sourceRank)

valueReceived = buffer[0]
buffer[0] = valueReceived * 4

finalAnswer = np.zeros(1)
destProcess = 0

comm.Reduce(buffer, finalAnswer, MPI.SUM, destProcess)

if rank == 0:
    print("Final answer =", finalAnswer[0])
