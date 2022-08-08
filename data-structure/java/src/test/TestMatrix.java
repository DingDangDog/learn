package test;

import utils.Matrix;

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
