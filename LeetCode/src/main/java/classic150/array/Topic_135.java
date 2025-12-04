package classic150.array;

/**
 * author : xxd
 * date   : 2025/12/3
 * desc   : https://leetcode.cn/problems/candy/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_135 {


    public static void main(String[] args) {
        Topic_135 topic = new Topic_135();
        int[] nums = new int[]{2, 3, 1, 1, 4};
        int i = topic.candy(nums);
        System.out.println(i);
    }

    public int candy(int[] ratings) {
        // 上升数组个数（1个按照上升计算），下降数组个数，前一个分的糖果，总糖果
        int inc = 1, dec = 0, pre = 1, total = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] >= ratings[i - 1]) {
                dec = 0;
                inc = ratings[i] == ratings[i - 1] ? 1 : pre + 1;
                total += inc;
                pre = inc;
            } else {
                dec++;
                if (dec == inc) {
                    dec++;
                }
                total += dec;
                pre = 1;
            }
        }
        return total;
    }
}
