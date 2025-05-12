from threading import Thread
import time

def countdown(n):
    while n > 0:
        n -= 1

COUNT = int(1e3)

start = time.time()
countdown(COUNT)
end = time.time()
print(end-start)
