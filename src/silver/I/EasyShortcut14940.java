package silver.I;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class EasyShortcut14940 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    static int[][] map;
    static int[][] result;
    static int N, M;
    static int[] dy = {0, 1, 0, -1};
    static int[] dx = {1, 0, -1, 0}; //동남서북

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        result = new int[N][M];
        Info startInfo = new Info(0, 0, 0);
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                map[n][m] = Integer.parseInt(st.nextToken());
                if (map[n][m] == 2) {
                    //목표지점의 위치를 저장한다.
                    startInfo = new Info(n, m, 0);
                }
            }
        }

        //2가 발견된 지점부터 bfs를 통해 각 노드까지 간선의 수를 저장한다.
        Queue<Info> queue = new LinkedList<>();
        boolean[][] check = new boolean[N][M];
        queue.add(startInfo);
        check[startInfo.row][startInfo.column] = true;
        result[startInfo.row][startInfo.column] = startInfo.count;

        while (!queue.isEmpty()) {
            Info curr = queue.poll();

            for (int k = 0; k < 4; k++) {
                int ny = dy[k] + curr.row;
                int nx = dx[k] + curr.column;

                //범위안에 존재하는지 이미 간 곳인지 못가는 곳인지 확인한다.
                if (ny < 0 || ny >= N || nx < 0 || nx >= M || check[ny][nx] || map[ny][nx] == 0) {
                    continue;
                }
                //1인 칸으로만 움직일 수 있다
                if (map[ny][nx] == 1) {
                    queue.add(new Info(ny, nx, curr.count + 1));
                    check[ny][nx] = true;
                    result[ny][nx] = curr.count + 1;
                }
            }
        }

        //갈수없을 경우 0을 저장하고, 갈수있지만 도달할수없으면 -1출력한다.
        for (int n = 0; n < N; n++) {
            for (int m = 0; m < M; m++) {
                //한번도 방문하지 않은 곳이고 원래 0이 아닌 경우
                if (!check[n][m] && map[n][m] != 0) {
                    result[n][m] = -1;
                }
                sb.append(result[n][m]).append(" ");
            }
            sb.append("\n");
        }

        // 결과를 한 번에 출력
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }


    static class Info {
        int row, column, count;

        public Info(int row, int column, int count) {
            this.row = row;
            this.column = column;
            this.count = count;
        }
    }
}
