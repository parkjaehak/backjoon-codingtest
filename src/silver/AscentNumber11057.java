package silver;

import java.util.Scanner;

public class AscentNumber11057 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();//수의길이

        long[][] dp = new long[N + 1][10]; //인덱스 0는 사용하지 않는다.
        for (int i = 0; i < 10; i++) {
            dp[1][i] = 1; //1자리 수의 경우 오르막수의 개수는 모두 1이다.
        }

        //길이가 n인 수의 오르막수
        for (int n = 2; n <= N; n++) {
            for (int m = 0; m < 10; m++) {
                // 특정 열의 오르막수는 길이는 하나 작은 행에서 처음부터 해당 열까지의 합과 같다
                for (int k = 0; k <= m; k++) {
                    //처음부터 해당 열까지의 합
                    dp[n][m] += dp[n - 1][k];
                }
                dp[n][m] %= 10007;
            }
        }

        //오르막수의 총 개수 구하기
        long answer = 0;
        for (int i = 0; i < 10; i++) {
            answer += dp[N][i];
        }
        System.out.println(answer % 10007);
    }
}
