package silver;

import java.io.*;

public class Fibonacci1003 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static int T;
    static int[][] dp = new int[41][2];


    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        dp[0][0] = 1; //N이 0일때 0의 호출횟수
        dp[0][1] = 0; //N이 0일때 1의 호출횟수
        dp[1][0] = 0; //N이 1일때 0의 호출횟수
        dp[1][1] = 1; //N이 1일때 1의 호출횟수

        for (int i = 2; i < 41; i++) {
            dp[i][0] = dp[i - 1][0] + dp[i - 2][0];
            dp[i][1] = dp[i - 1][1] + dp[i - 2][1];
        }

        for (int i = 0; i < T; i++) {
            int N = Integer.parseInt(br.readLine());
            sb.append(dp[N][0] + " " + dp[N][1]).append('\n');
        }
        System.out.print(sb);
    }
}
