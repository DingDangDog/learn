#!/usr/bin/python3
"""
### 猜数字小游戏
"""
import myMath
import random


def init():
    guess = -1
    number = random.randint(0, 20000)  # 生成随机数
    tipNum = myMath.getTipNum(number)  # 获取数字提示上限
    guessNum = myMath.getGuessTimes(tipNum)  # 获取游戏参考次数

    print("开始猜数字游戏，给予你" + str(guessNum) + "次机会，提示：0到" + str(tipNum))

    while guess != number and guessNum > 0:
        print("剩余" + str(guessNum) + "次机会")
        guess = input("请输入数字：")

        """
        --- 判断输入是退出指令，结束循环，结束游戏
        """
        if guess == "exit":
            break

        """
        --- 捕获整数数字转换异常
        --- 出现异常给出提示，并且不减少游戏次数
        """
        try:
            guess = int(guess)
        except ValueError:
            print("请输入合法的整数数字！\n")
            continue

        """
        --- 判断数字大小并给出基本提示
        """
        if guess == number:
            print("恭喜您，猜对了\n")
        elif guess < number:
            print("猜错了，提示：猜小了\n")
        elif guess > number:
            print("猜错了，提示：猜大了\n")
        guessNum -= 1

    print("游戏结束")


if __name__ == '__main__':
    init()
