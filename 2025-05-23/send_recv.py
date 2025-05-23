from mpi4py import MPI
import sys
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.Get_rank()
size = comm.Get_size()


if size < 2:
    print("Too few processes!")
    sys.exit(1)

buffer = np.zeros(1)

numItemsToTransfer = 1
sourceRank = 0
destinationRank = 1
messageTag = 0  # supplemental tag, often just not used

if rank == 0:
    buffer = np.array([5])
    comm.Send(buffer, destinationRank, messageTag)
elif rank == 1:
    buffer = np.array([0])
    comm.Recv(buffer, sourceRank, messageTag)
    print("Process 1 received value", buffer[0], "from process 0")
