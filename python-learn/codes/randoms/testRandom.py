import random

def init():
    state = random.getstate()
    print("生成第一次随机数")
    print(random.randint(0, 10))
    print(random.randint(0, 10))
    print(random.randint(0, 10))
    print(random.randint(0, 10))
    print(random.randint(0, 10))
    random.setstate(state)
    print("重置随机ID，生成第二次随机数")
    print(random.randint(0, 10))
    print(random.randint(0, 10))
    print(random.randint(0, 10))
    print(random.randint(0, 10))
    print(random.randint(0, 10))

if __name__ == '__main__':
    init()
