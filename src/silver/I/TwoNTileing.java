package silver.I;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TwoNTileing {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        //2xN 크기의 직사각형을 채우는 방법의 수
        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1]; //최대 1000이기 때문에 new int[1001] 해도 됨

        // N이 1일 경우 처리
        if (N == 1) {
            System.out.println(1);
            return;
        }

        dp[1] = 1;//세로
        dp[2] = 2; // 세로 or 가로
        //dp[3] = dp[2] + dp[1]; //맨끝에 새로 바 하나인 경우 + 맨끝에 가로바 두개인 경우

        for (int i = 3; i <= N; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % 10007;
        }

        System.out.println(dp[N] % 10007);
    }
}
