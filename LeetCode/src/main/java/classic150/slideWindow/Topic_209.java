package classic150.slideWindow;

/**
 * author : xxd
 * date   : 2025/12/5
 * desc   : https://leetcode.cn/problems/minimum-size-subarray-sum/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_209 {

    public static void main(String[] args) {
        Topic_209 topic = new Topic_209();
        int i = topic.minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3});
        System.out.println(i);
    }

    public int minSubArrayLen(int target, int[] nums) {
        int minLength = Integer.MAX_VALUE;

        int pre = 0, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum >= target) {
                while (pre < i && sum - nums[pre] >= target) {
                    sum = sum - nums[pre];
                    pre++;
                }

                minLength = Math.min(minLength, i - pre + 1);
                if (minLength == 1)
                    return 1;
            }

        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}
