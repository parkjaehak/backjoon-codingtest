package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class RGB1149 {

    static int[][] cost;

    public RGB1149() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        cost = new int[N][3];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) { //0은 red, 1은 green, 2는 blue
                cost[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 1; i < N; i++) {
            cost[i][0] += Math.min(cost[i - 1][1], cost[i - 1][2]); //red
            cost[i][1] += Math.min(cost[i - 1][0], cost[i - 1][2]); //green
            cost[i][2] += Math.min(cost[i - 1][0], cost[i - 1][1]); //blue
        }
        int min = Math.min(Math.min(cost[N - 1][0], cost[N - 1][1]), cost[N - 1][2]);

        System.out.println(min);
    }
}
