package classic150.array;

/**
 * author : xxd
 * date   : 2025/12/5
 * desc   : https://leetcode.cn/problems/trapping-rain-water/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_42 {

    public static void main(String[] args) {
        Topic_42 topic = new Topic_42();
        int i = topic.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        System.out.println(i);
    }

    public int trap(int[] height) {
        int total = 0;
        int i = 0, j = height.length - 1;
        int preMax = height[i], sufMax = height[j];
        while (i < j - 1) {
            if (preMax >= sufMax) {
                j--;
                if (height[j] < sufMax) {
                    total += sufMax - height[j];
                } else {
                    sufMax = height[j];
                }
            } else {
                i++;
                if (height[i] < preMax) {
                    total += preMax - height[i];
                } else {
                    preMax = height[i];
                }
            }
        }

        return total;
    }
}
