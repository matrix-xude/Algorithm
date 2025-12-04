package classic150.array;

/**
 * author : xxd
 * date   : 2025/10/21
 * desc   : https://leetcode.cn/problems/jump-game-ii/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_45 {

    public static void main(String[] args) {
        Topic_45 topic = new Topic_45();
        int[] nums = new int[]{2, 3, 1, 1, 4};
        int i = topic.jump(nums);
        System.out.println(i);
    }

    // 自己想法，减少最后一次循环的遍历，可以达到结尾立马终止
    public int jump(int[] nums) {
        if (nums.length == 1)
            return 0;

        int step = 1;
        int currentMax = 0;
        int nextMax = 0;
        int index = 0;

        while (nextMax < nums.length - 1) {
            if (index > currentMax) {
                step++;
                currentMax = nextMax;
            }
            nextMax = Math.max(nextMax, index + nums[index]);
            index++;
        }

        return step;
    }

    // 步骤更少，直接遍历到倒数第2个角标
    public int jump2(int[] nums) {
        int step = 0;
        int currentMax = 0;
        int nextMax = 0;

        // 注意这里是遍历到 length - 2 的位置，减少一个长度0的判断
        for (int i = 0; i < nums.length - 1; i++) {
            nextMax = Math.max(nextMax, i + nums[i]);
            if (i == currentMax){
                step ++;
                currentMax = nextMax;
            }
        }

        return step;
    }
}
