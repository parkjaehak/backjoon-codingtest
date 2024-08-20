package silver;

import java.io.*;

public class AddOneTwoThree9095 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] dp = new int[12]; //n은 11까지 가능하며 각 n에 대한 방법의 수를 저장한다.
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static int T, N;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;

        for (int j = 4; j <= 11; j++) {
            dp[j] = dp[j - 3] + dp[j - 2] + dp[j - 1];
        }

        for (int t = 0; t < T; t++) {
            N = Integer.parseInt(br.readLine());
            sb.append(dp[N]).append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
    }
}