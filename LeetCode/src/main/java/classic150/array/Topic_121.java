package classic150.array;

/**
 * author : xxd
 * date   : 2025/10/21
 * desc   : https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_121 {

    public static void main(String[] args) {
        Topic_121 topic = new Topic_121();
        int[] nums = new int[]{7, 1, 5, 3, 6, 4};
        int i = topic.maxProfit(nums);
        System.out.println(i);
    }

    public int maxProfit(int[] prices) {
        int minLeft = prices[0];
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            maxProfit = Math.max(maxProfit, prices[i] - minLeft);
            minLeft = Math.min(prices[i], minLeft);
        }
        return maxProfit;
    }
}
