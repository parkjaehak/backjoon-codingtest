package silver.III;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StairUp2579 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] array;
    static int[] dp;
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        array = new int[N + 1];
        dp = new int[N + 1];

        for (int n = 1; n <= N; n++) {
            array[n] = Integer.parseInt(br.readLine());
        }

        // N이 1인 경우
        if (N == 1) {
            System.out.println(array[1]);
            return;
        }

        // N이 2인 경우
        if (N == 2) {
            System.out.println(array[1] + array[2]);
            return;
        }

        dp[0] = 0; //0번째 계단에 도착했을때 얻을 수 있는 최대 점수
        dp[1] = array[1];//첫번째 계단의 점수
        dp[2] = array[1] + array[2]; //두번째 계단은 첫번째에서 이동하거나 0번째에서 두칸 이동한다.
        //dp[3] = Math.max(dp[1] + array[3], dp[0] + array[2] + array[3]); //계단 두번밟고 바로오는경우 + 이전과 세칸전을 밟고 오는 경우
        for (int i = 3; i <= N; i++) {
            dp[i] = Math.max(dp[i - 2] + array[i], dp[i - 3] + array[i - 1] + array[i]);
        }

        System.out.println(dp[N]);
    }
}
