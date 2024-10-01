package silver.I;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Jump1890 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static int[][] array;
    static long[][] dp;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        array = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //2)DP(경우의 수의 합을 구한다.)
        dp = new long[N][N];
        dp[0][0] = 1; //시작점 초기화

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == N - 1 && j == N - 1) {
                    continue;
                }
                int move = array[i][j];

                //오른쪽으로 이동
                if (j + move < N) {
                    dp[i][j + move] += dp[i][j];
                }
                //아래로 이동
                if (i + move < N) {
                    dp[i + move][j] += dp[i][j];
                }
            }
        }
        // 최종 목적지에 도달하는 경로의 수 출력
        System.out.println(dp[N - 1][N - 1]);

//        //1) BFS
//        Queue<int[]> queue = new LinkedList<>();
//        queue.add(new int[]{0, 0});
//        int count = 0;
//        int ny, nx;
//
//        while (!queue.isEmpty()) {
//            int[] info = queue.poll(); // 위치 정보
//            int currRow = info[0];
//            int currColumn = info[1];
//
//            // 오른쪽 아래 코너에 도달했는지 확인
//            if (currRow == N - 1 && currColumn == N - 1) {
//                count++;
//                continue; // 도착 후 더 이상의 이동은 필요하지 않음
//            }
//
//            for (int i = 0; i < 2; i++) {
//                ny = currRow + dy[i] * array[currRow][currColumn]; //해당 칸의 적힌 수만큼 이동
//                nx = currColumn + dx[i] * array[currRow][currColumn];
//
//                //범위 초과여부확인
//                if (ny < 0 || ny >= N || nx < 0 || nx >= N) {
//                    continue;
//                }
//                queue.add(new int[]{ny, nx});
//            }
//        }
//        System.out.println(count);
    }
}
