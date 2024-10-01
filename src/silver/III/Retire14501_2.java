package silver.III;

import java.util.Scanner;

public class Retire14501_2 {
    public static int N;
    public static int[] time;
    public static int[] money;

    public static int[] dp;
    public Retire14501_2() {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();

        dp = new int[N+1];
        time = new int[N];
        money = new int[N];

        for(int i = 0; i < N; i++){
            time[i] = sc.nextInt();
            money[i] = sc.nextInt();
        }

        for(int i = N-1; i > -1; i--){
            // 현재 날짜 + 상담 소요일이 퇴사일을 넘어가면 다음날을 선택하는것으로 결정됨
            if(i + time[i] > N){
                dp[i] = dp[i+1];
            }

            else {
                // 다음날 dp값 vs 현재값 + 현재+소요일의 dp값을 비교하여 더 큰 값 선택
                dp[i] = Math.max(dp[i + 1],dp[i + time[i]] + money[i]);
            }
        }
        System.out.println(dp[0]);
    }
}
