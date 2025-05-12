import multiprocessing as mp
import time

def countdown(n):
    sum = 0
    while n > 0:
        n -= 1
        sum = sum + n

COUNT = int(1e8)

q1 = mp.Queue(1)  # size of queue
q2 = mp.Queue(1)
t1 = mp.Process(target=countdown,args=(COUNT,))
t2 = mp.Process(target=countdown,args=(COUNT*2,))
start = time.time()
t1.start()
t2.start()
t1.join()
t2.join()
end = time.time()
print(end-start)
