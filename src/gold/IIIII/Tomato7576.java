package gold.IIIII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Tomato7576 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static int[][] array;
    static Queue<Info> queue = new LinkedList<>();
    static int[] dy = {-1, 0, 1, 0}; //북 동 남 서
    static int[] dx = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        array = new int[N][M];
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                array[n][m] = Integer.parseInt(st.nextToken());
                if (array[n][m] == 1) {
                    queue.add(new Info(n, m));
                }
            }
        }
        //창고에 보관된 토마토들이 며칠이 지나면 다 익게 되는지, 그 최소 일수
        System.out.println(bfs());
    }

    private static int bfs() {
        int days = -1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            days++; //레벨 단위로 분할하여 날짜를 계산한다.

            for (int s = 0; s < size; s++) {
                Info curr = queue.poll();

                for (int i = 0; i < 4; i++) {
                    int ny = curr.row + dy[i];
                    int nx = curr.column + dx[i];

                    //범위를 넘었거나 -1이거나 이미 간 곳이면 더이상 갈 수 없다.
                    if (ny < 0 || ny >= N || nx < 0 || nx >= M || array[ny][nx] != 0) {
                        continue;
                    }
                    array[ny][nx] = 1;
                    queue.add(new Info(ny, nx));
                }
            }
        }

        for (int n = 0; n < N; n++) {
            for (int m = 0; m < M; m++) {
                if (array[n][m] == 0) {
                    //익지 않은 토마토가 존재한다면
                    return -1;
                }
            }
        }
        return days;
    }

    static class Info {
        int row;
        int column;
        public Info(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
