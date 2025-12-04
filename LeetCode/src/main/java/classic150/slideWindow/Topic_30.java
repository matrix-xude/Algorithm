package classic150.slideWindow;

import java.util.*;

/**
 * author : xxd
 * date   : 2025/12/5
 * desc   : https://leetcode.cn/problems/substring-with-concatenation-of-all-words/description/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_30 {

    public static void main(String[] args) {
        Topic_30 topic = new Topic_30();
        List<Integer> list = topic.findSubstring("bcabbcaabbccacacbabccacaababcbb", new String[]{"c", "b", "a", "c", "a", "a", "a", "b", "c"});
        System.out.println(list);
    }

    public List<Integer> findSubstring(String s, String[] words) {
        ArrayList<Integer> list = new ArrayList<>();

        int wordLength = words[0].length(); // 每个单词长度
        int wordSize = words.length; // 单词个数

        for (int i = 0; i < wordLength; i++) {  // 字符串长度固定，按照长度循环一遍，好分割字符
            if (wordSize * wordLength > s.length() - i)
                break;

            Map<String, Integer> map = new HashMap<>();
            for (int j = 0; j < wordSize; j++) {
                // 在字符串中添加size个数据
                String substring = s.substring(j * wordLength + i, (j + 1) * wordLength + i);
                map.put(substring, map.getOrDefault(substring, 0) + 1);
            }

            for (String word : words) {
                // 减去原本words中的数据
                map.put(word, map.getOrDefault(word, 0) - 1);

                if (map.getOrDefault(word, 0) == 0) {
                    map.remove(word);
                }
            }

            if (map.isEmpty())
                list.add(i);

            for (int j = 1; (j + wordSize) * wordLength + i <= s.length(); j++) {
                String pre = s.substring((j - 1) * wordLength + i, j * wordLength + i);
                map.put(pre, map.getOrDefault(pre, 0) - 1);
                if (map.getOrDefault(pre, 0) == 0) {
                    map.remove(pre);
                }

                String suf = s.substring((j + wordSize - 1) * wordLength + i, (j + wordSize) * wordLength + i);
                map.put(suf, map.getOrDefault(suf, 0) + 1);
                if (map.getOrDefault(suf, 0) == 0) {
                    map.remove(suf);
                }

                if (map.isEmpty())
                    list.add(i + j * wordLength);
            }

        }

        return list;
    }
}
