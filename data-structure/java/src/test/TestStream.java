package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JDK8-Stream学习测试类
 *
 * @author DingDangDog
 * @since 2022/8/10 15:11
 */
public class TestStream {
    public static void main(String[] args) {
        List<Integer> ints = new ArrayList<>();
        ints.add(2);
        ints.add(1);
        ints.add(3);
        ints.add(5);
        ints.add(4);
        // 输出最小值
        // Integer min = Collections.min(ints);
        Integer min = ints.stream().min(Integer::compareTo).get();
        // 输出最大值
        // Integer max = Collections.max(ints);
        Integer max = ints.stream().max(Integer::compareTo).get();
        // 平均值
        Double average = ints.stream().mapToInt(Integer::new).average().getAsDouble();
        // 排序（默认正序从小到大）
        Collections.sort(ints);
        ints = ints.stream().sorted().collect(Collectors.toList());
        // 排序（倒序，从大到小）
        ints.sort(Collections.reverseOrder());
        ints = ints.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        // 倒置
        Collections.reverse(ints);
    }
}
