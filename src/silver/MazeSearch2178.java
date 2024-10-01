package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class MazeSearch2178 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static int[][] array;
    static boolean[][] check;
    static int[] dy = {-1, 0, 1, 0};//북 동 남 서
    static int[] dx = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        array = new int[N][M];
        check = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < line.length(); j++) { //M == line.length()
                int number = Character.getNumericValue(line.charAt(j)); //한줄을 받아와서 각 자리를 숫자로 변환하여 저장한다.
                array[i][j] = number;
            }
        }

        //(0,0)에서 출발하여 (N-1,M-1)로 이동한다.
        move();
    }

    private static void move() {
        Queue<Info> queue = new LinkedList<>();
        queue.add(new Info(0, 0, 1));
        check[0][0] = true;
        //시작위치는 고정이다.

        while (!queue.isEmpty()) {
            Info curr = queue.poll();
            // 목표 위치에 도착했으면 그때의 거리를 반환
            if (curr.row == N - 1 && curr.column == M - 1) {
                System.out.println(curr.count);
                return; //최단 거리를 찾은 즉시 종료한다.
            }

            for (int i = 0; i < 4; i++) {
                int nr = curr.row + dy[i];
                int nc = curr.column + dx[i];

                if (nr < 0 || nr >= N || nc < 0 || nc >= M || check[nr][nc] || array[nr][nc] == 0) {
                    //범위를 넘거나 이미 간곳이거나 0인칸은 이동할 수 없다.
                    continue;
                }
                check[nr][nc] = true;
                queue.add(new Info(nr, nc, curr.count + 1));
            }
        }
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
