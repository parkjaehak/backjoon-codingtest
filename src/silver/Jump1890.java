package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Jump1890 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static int[][] array;
    static int[] dy = {0, 1}; //동 남
    static int[] dx = {1, 0};

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        array = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //1) BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{0, 0});
        int count = 0;
        int ny, nx;

        while (!queue.isEmpty()) {
            int[] info = queue.poll(); // 위치 정보
            int currRow = info[0];
            int currColumn = info[1];

            // 오른쪽 아래 코너에 도달했는지 확인
            if (currRow == N - 1 && currColumn == N - 1) {
                count++;
                continue; // 도착 후 더 이상의 이동은 필요하지 않음
            }

            for (int i = 0; i < 2; i++) {
                ny = currRow + dy[i] * array[currRow][currColumn]; //해당 칸의 적힌 수만큼 이동
                nx = currColumn + dx[i] * array[currRow][currColumn];

                //범위 초과여부확인
                if (ny < 0 || ny >= N || nx < 0 || nx >= N) {
                    continue;
                }
                queue.add(new int[]{ny, nx});
            }
        }
        System.out.println(count);
    }
}
