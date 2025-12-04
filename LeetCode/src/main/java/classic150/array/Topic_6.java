package classic150.array;

/**
 * author : xxd
 * date   : 2025/12/3
 * desc   : https://leetcode.cn/problems/zigzag-conversion/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_6 {

    public static void main(String[] args) {
        Topic_6 topic = new Topic_6();
        String converted = topic.convert("trump_king", 3);
        System.out.println(converted);
    }

    public String convert(String s, int numRows) {
        int length = s.length();
        if (numRows == 1 || numRows >= length)
            return s;

        // 直接构造新字符串
        StringBuilder sb = new StringBuilder();
        // 周期
        int cycle = numRows * 2 - 2;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j + i < length; j += cycle) {
                sb.append(s.charAt(j + i)); // 第一个字符
                if (i > 0 && i < numRows-1 && j + cycle - i < length){
                    sb.append(s.charAt(j + cycle - i));  // 第二个字符
                }
            }
        }

        return sb.toString();
    }
}
