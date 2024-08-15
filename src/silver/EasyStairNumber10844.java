package silver;

import java.util.Scanner;

public class EasyStairNumber10844 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        //계단수는 인접한 모든 자리가 1이다.
        //N의 계단수가 총 몇개인지 출력한다.

        int[][] dp = new int[N + 1][10];
        for (int i = 1; i < 10; i++) {
            dp[1][i] = 1; //0을 제외한 한자리수는 모두 계단 수이다.
        }

        for (int n = 2; n <= N; n++) {
            for (int m = 0; m < 10; m++) {
                if (m == 0) {
                    dp[n][m] = dp[n - 1][m + 1];
                } else if (m == 9) {
                    dp[n][m] = dp[n - 1][m - 1];
                } else {
                    dp[n][m] = dp[n - 1][m - 1] + dp[n - 1][m + 1];
                }
            }
        }

        long answer = 0;
        for (int i = 0; i < 10; i++) {
            answer += dp[N][i];
        }
        System.out.println(answer % 1000000000);
    }
}
