package utils;

/**
 * 矩阵类
 *
 * @author DingDangDog
 * @since 2022/8/8 14:55
 */
public class Matrix<T> {
    /**
     * 矩阵数据存储【Class[行数][列数]】
     */
    private final T[][] values;
    /**
     * 矩阵行数记录
     */
    private final int row;
    /**
     * 矩阵列数记录
     */
    private final int col;

    /**
     * 构造
     *
     * @param t 数据存储器
     */
    public Matrix(T[][] t) {
        this.row = t.length;
        this.col = t[0].length;
        this.values = t;
    }

    /**
     * 构造
     *
     * @param t   数据存储器
     * @param row 行数
     * @param col 列数
     */
    public Matrix(T[][] t, int row, int col) {
        this.row = row;
        this.col = col;
        this.values = t;
    }

    /**
     * 获取矩阵内数据
     *
     * @param row 行
     * @param col 列
     * @return 数据
     */
    public T getValue(int row, int col) {
        return values[row][col];
    }

    /**
     * 设置矩阵内数据
     *
     * @param row   行
     * @param col   列
     * @param value 数据
     */
    public void setValue(int row, int col, T value) {
        values[row][col] = value;
    }

    public T[][] getValues() {
        return values;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
