package silver;

import java.io.*;
import java.util.StringTokenizer;

public class OrganicCabbage1012 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int[] dy = {1, 0, -1, 0}; //북동남서
    static int[] dx = {0, 1, 0, -1};
    static int T, N, M, K;
    static int [][]array;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine()); //테스트 횟수

        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken()); //가로
            N = Integer.parseInt(st.nextToken()); //세로
            K = Integer.parseInt(st.nextToken()); //배추 심어져 있는 위치 개수

            array = new int[N][M];
            visited = new boolean[N][M];
            for (int k = 0; k < K; k++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken()); //가로
                int y = Integer.parseInt(st.nextToken()); //세로

                array[y][x] = 1; //배추가 있는 곳에 1을 표시한다.
            }
            int count = findWhiteWorm();
            sb.append(count).append('\n');
        }

        bw.write(sb.toString());
        bw.flush();
    }

    private static int findWhiteWorm() {
        int wormCount = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (array[i][j] == 1 && !visited[i][j]) {
                    dfs(i, j);
                    wormCount++; //군집 중 최초 발견한 곳에서만 count 한다.
                }
            }
        }
        return wormCount;
    }

    private static void dfs(int row, int column) {
        visited[row][column] = true;

        for (int i = 0; i < 4; i++) {
            int ny = row + dy[i];
            int nx = column + dx[i];

            //범위를 넘어간 곳이거나 배추가 없는 곳이거나 방문한 곳일 경우 넘긴다.
            if (ny < 0 || ny >= N || nx < 0 || nx >= M || array[ny][nx] == 0 || visited[ny][nx]) {
                continue;
            }
            dfs(ny, nx);
        }
    }
}
