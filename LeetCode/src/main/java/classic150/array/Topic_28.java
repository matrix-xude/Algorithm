package classic150.array;

import java.util.Arrays;

/**
 * author : xxd
 * date   : 2025/12/5
 * desc   : KMP算法
 * https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_28 {

    public static void main(String[] args) {
        Topic_28 topic = new Topic_28();
        int i = topic.strStr("aaaacaaadbaac", "aad");
        System.out.println(i);
    }

    public int strStr(String ss, String pp) {
        int length = pp.length();
        int[] ints = new int[length]; // 长度角标为index的最大前后缀相同的长度
        ints[0] = 0;

        System.out.println(Arrays.toString(ints));

        for (int i = 1; i < length; i++) {
            int j = ints[i - 1];  // 字符串前一个子串的最大前后缀相同的数
            while (j > 0 && pp.charAt(j) != pp.charAt(i)) {  // 重复匹配
                j = ints[j - 1];
            }
            if (pp.charAt(j) == pp.charAt(i)) {
                j++;
            }
            ints[i] = j;
        }

        int matched = 0;
        for (int i = 0; i < ss.length(); i++) {
            char c = ss.charAt(i);
            while (matched > 0 && pp.charAt(matched) != c) {
                matched = ints[matched - 1];
            }
            if (pp.charAt(matched) == c) {
                matched++;
            }

            if (matched == length) {
                return i - length + 1;
            }
        }

        return -1;
    }
}
