package gold.IIII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * N,M은 5000보다 작다
 * 각 칸의 정수는 1000을 넘지 않는 자연수
 * <p>
 * 테트로미노가 가린 네 칸의 합 중 최댓값 출력
 */
public class Tetromino14500 {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int[][] map;
    static boolean[][] check;
    static int N, M;
    static int[] dy = {0, 1, 0, -1}; // 동, 남, 서, 북
    static int[] dx = {1, 0, -1, 0};
    static int max = Integer.MIN_VALUE;

    public Tetromino14500() throws IOException {
        st = new StringTokenizer(bf.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        //1. 각 칸에 정수를 입력한다.
        map = new int[N][M];
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(bf.readLine());
            for (int m = 0; m < M; m++) {
                map[n][m] = Integer.parseInt(st.nextToken());
            }
        }

        //2. 테트로미노가 가리는 네 공간의 합이 최대가 되도록 한다.
        check = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                check[i][j] = true;
                dfs(1, i, j, map[i][j]); //반복횟수, 행, 열, 해당 칸의 합
                check[i][j] = false;
            }
        }

        System.out.println(max);
    }

    public static void dfs(int count, int row, int column, int sum) {
        if (count == 4) {
            max = Math.max(max, sum);
            return;
        }

        for (int i = 0; i < 4; i++) {
            int ny = dy[i] + row;
            int nx = dx[i] + column;

            //3. 범위를 벗어나거나 이미 간 곳은 다시 가지 않는다.
            if (ny >= N || ny < 0 || nx >= M || nx < 0 || (check[ny][nx] == true)) {
                continue;
            }

            //4. (ㅗ) 모양 찾기
            if (count == 2) {
                check[ny][nx] = true;
                dfs(count + 1, row, column, map[ny][nx] + sum);
                check[ny][nx] = false;
            }

            check[ny][nx] = true;
            dfs(count + 1, ny, nx, map[ny][nx] + sum);
            check[ny][nx] = false;
        }
    }
}
