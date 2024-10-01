package silver.I;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CardBuying11052 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static int[] p; //카드팩의 가격 배열
    static int[] dp;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        p = new int[N + 1]; //인덱스 1부터 N까지 사용가능하다.
        dp = new int[N + 1];

        //p배열에 카드 장수(인덱스)에 따른 가격을 저장한다.
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            p[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= i; j++) {
                //카드가 i장일때 금액의 최댓값을 구한다.
                dp[i] = Math.max(dp[i], dp[i - j] + p[j]);
            }
        }
        System.out.println(dp[N]);
    }
}
