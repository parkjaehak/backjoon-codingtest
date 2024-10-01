package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MakeOne1463 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] dp; //dp[i]: 숫자 i를 1로 만들기 위한 최소 연산 횟수 저장 배열

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1];
        //dp[1] = 0 , 숫자 1을  1로 만들기 위한 최소연산 횟수는 0
        //dp[2] = dp[1] + 1 = 1
        //dp[3] = dp[2] + 1 = 2 , dp[1] + 1 = 1
        //dp[4] = dp[2] + 1 = 2 , dp[3] + 1 = 2
        //dp[5] = dp[4] + 1 = 3

        dp[1] = 0;
        for (int i = 2; i <= N; i++) {
            //1을 뺸다
            dp[i] = dp[i - 1] + 1;

            //2로 나눈다.
            if (i % 2 == 0) {
                dp[i] = Math.min(dp[i], dp[i / 2] + 1); //더 작은 값으로 갱신한다.
            }

            //3으로 나눈다.
            if (i % 3 == 0) {
                dp[i] = Math.min(dp[i], dp[i / 3] + 1); //더 작은 값으로 갱신한다.
            }
        }
        System.out.println(dp[N]);
    }
}
