package classic150.array;

import java.util.Arrays;

/**
 * author : xxd
 * date   : 2025/10/21
 * desc   : https://leetcode.cn/problems/rotate-array/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_189 {
    public static void main(String[] args) {
        Topic_189 topic = new Topic_189();
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7};
        int k = 3;
        topic.rotate2(nums, k);
        System.out.println(Arrays.toString(nums));
    }

    public void rotate(int[] nums, int k) {
        int length = nums.length;
        k %= length;
        int[] nums1 = new int[length];

        for(int i = 0; i < length; ++i) {
            if (i + k < length) {
                nums1[i + k] = nums[i];
            } else {
                nums1[i + k - length] = nums[i];
            }
        }

        System.arraycopy(nums1, 0, nums, 0, length);
    }

    // 3次反转，实现数组轮转，空间O(1)
    public void rotate2(int[] nums, int k) {
        int length = nums.length;
        k %= length;
        this.reverse(nums, 0, length);
        this.reverse(nums, 0, k);
        this.reverse(nums, k, length - k);
    }

    private void reverse(int[] nums, int start, int length) {
        for(int i = 0; i < length / 2; ++i) {
            int temp = nums[start + i];
            nums[start + i] = nums[start + length - i - 1];
            nums[start + length - i - 1] = temp;
        }

    }
}

