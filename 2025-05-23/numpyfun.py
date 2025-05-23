import numpy as np

x = np.array([1,2,3,4,5])
y = np.array([1.,2.,3.,4.,5.])
print(x)
print(y)
print(type(x))
print(type(y))
z = np.zeros(5)
print(z)
a = np.ones((4,2))
print(a)
print(a*2)
a[0][0] = 12
a[0][1] = 18
print()
print(a)
