#!/usr/bin/python3

"""
### 获取提示数字
"""
def getTipNum(number):
    tip = 10
    while (number := (number // 10)) > 10:
        tip *= 10
    return tip * number * 2
"""
### 获取使用最小二分法大概猜测次数
"""
def getGuessTimes(number):
    n = 3
    while 2 ** n < number:
        n += 1
    return n
