package test;

import utils.Matrix;

/**
 * 自建矩阵类测试类
 *
 * @author DingDangDog
 * @since 2022/8/10 15:11
 */
public class TestMatrix {
    public static void main(String[] args) {
        Matrix<Integer> intMatrix = new Matrix<>(new Integer[2][3], 2, 3);
        intMatrix.setValue(0, 0, 1);
        intMatrix.setValue(0, 1, 2);
        intMatrix.setValue(1, 0, 2);
        System.out.println(intMatrix.getValue(0, 0));
        System.out.println(intMatrix.getValue(0, 1));
        System.out.println(intMatrix.getValue(1, 0));
    }
}
