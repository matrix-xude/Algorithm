package classic150.point2;

/**
 * author : xxd
 * date   : 2025/12/5
 * desc   : https://leetcode.cn/problems/trapping-rain-water/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_11 {

    public static void main(String[] args) {
        Topic_11 topic = new Topic_11();
        int i = topic.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7});
        System.out.println(i);
    }

    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1;
        int preMax = height[i], sufMax = height[j];
        int total = Math.min(preMax, sufMax) * (j - i);
        while (i < j) {
            if (preMax >= sufMax) {
                j--;
                if (height[j] > sufMax) {
                    sufMax = height[j];
                    total = Math.max(total, Math.min(preMax, sufMax) * (j - i));
                }
            } else {
                i++;
                if (height[i] > preMax) {
                    preMax = height[i];
                    total = Math.max(total, Math.min(preMax, sufMax) * (j - i));
                }
            }
        }

        return total;
    }
}
