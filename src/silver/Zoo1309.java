package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Zoo1309 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] dp;
    static int[][]array;
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        array = new int[N + 1][3];
        //column(0: 아무것도 선택안함, 1: 왼쪽 선택, 2: 오른쪽 선택)
        array[1][0] = array[1][1] = array[1][2] = 1;

        for (int i = 2; i <= N; i++) {
            array[i][0] = array[i-1][0] + array[i-1][1] + array[i-1][2]; //i번째 row에 아무것도 놓지 않을 경우 i-1번째에는 모든 케이스가 가능하다.
            array[i][1] = array[i-1][0] + array[i-1][2]; //오른쪽을 선택가능하다.
            array[i][2] = array[i - 1][0] + array[i - 1][1];//왼쪽을 선택가능하다.
        }

        int result = array[N][0] + array[N][1] + array[N][2];


//        dp = new int[N + 1]; //사자가 i마리일때의 모든 경우의 수
//        dp[0] = 1; //사자를 한마리도 배치하지 않는 경우(사자가 없는 경우)
//        dp[1] = 3; //사자가 한마리 존재할때 배치하는 경우의 수
//
//        //사자는 0~N마리 배치가능하다.
//        // N=i일때 맨 위가 공백인 경우 i1, 왼쪽 한칸에 넣는 경우 i2, 오른쪽 한칸에 넣는 경우 i3
//        // N=i+1일때 맨 위가 공백인 경우 i1 + i2 + i3, 왼쪽 한칸에 넣는 경우 i1 + i3, 오른쪽 한칸에 넣는 경우 i1 + i2
//
//        for (int i = 2; i <= N; i++) {
//            dp[i] = (2 * dp[i - 1] + dp[i - 2]) % 9901;
//        }
//        System.out.println(dp[N] % 9901);




    }
}
