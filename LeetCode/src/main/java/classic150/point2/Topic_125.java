package classic150.point2;

/**
 * author : xxd
 * date   : 2025/12/5
 * desc   : https://leetcode.cn/problems/valid-palindrome/?envType=study-plan-v2&envId=top-interview-150
 */
public class Topic_125 {

    public static void main(String[] args) {
        System.out.printf("ASCII: A=%s, Z=%s, a=%s, z=%s, 0=%s, 9=%s,%n", (int) 'A', (int) 'Z', (int) 'a', (int) 'z', (int) '0', (int) '9');

        Topic_125 topic = new Topic_125();
        boolean palindrome = topic.isPalindrome("A man, a plan, a canal: Panama");
        System.out.println(palindrome);
    }

    public boolean isPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j) {
            int checked1 = check(s.charAt(i));
            if (checked1 == -1) {
                i++;
                continue;
            }

            int checked2 = check(s.charAt(j));
            if (checked2 == -1) {
                j--;
                continue;
            }

            if (checked1 != checked2){
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    // 'A-Z':65-90; 'a-z':97-122; '0-9':48-57
    private int check(char c) {
        int i = c;
        if (i >= 65 && i <= 90) {
            return i;
        } else if (i >= 97 && i <= 122) {
            return i - 32;
        } else if (i >= 48 && i <= 57) {
            return i;
        } else {
            return -1;
        }
    }
}
