package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 1. 벽을 3개 세운다
// 2. 바이러스를 퍼트려본다
// 3. 0의 개수를 구한다.
//  -> 이 값을 max값과 계속 비교하면서 최대값을 구한다.
public class Laboratory14502 {

    static int [][]array;
    static int row;
    static int colum;
    static int maxSafeZone = 0;

    public Laboratory14502() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        row = Integer.parseInt(st.nextToken());
        colum = Integer.parseInt(st.nextToken());
        array = new int[row][colum];


        for(int i=0; i<row; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<colum; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        dfs(0);
        System.out.println(maxSafeZone);

    }

    static void dfs(int wallCnt) {
        if (wallCnt == 3) {
            // 2. 바이러스를 퍼트려본다(안전영역의 개수를 반환)
            int countZero = bfs();

            // 3. 남아있는 0의 개수를 센다
            maxSafeZone = Math.max(maxSafeZone, countZero);
            return;
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                if (array[i][j] == 0) {
                    // 1. 벽을 세개 세운다.
                    array[i][j] = 1;
                    dfs((wallCnt + 1));
                    array[i][j] = 0;
                }
            }
        }
    }

    public static int []dx = new int[]{0, 0, 1, -1};
    public static int []dy = new int[]{1, -1, 0, 0};
    private static int bfs() {
        // 2인 숫자를 기준을 퍼트림
        Queue<int[]> q = new LinkedList<>();
        boolean[][] check = new boolean[row][colum]; //체크 배열(false 초기화)

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                if (array[i][j] == 2) {
                    q.add(new int[]{i, j});
                    check[i][j] = true;
                }
            }
        }
        while (!q.isEmpty()) {
            int []list = q.poll(); //가장 앞 요소를 반환
            int curx = list[0];
            int cury = list[1];

            // 상하좌우로 퍼질 수 있음
            for (int i = 0; i < 4; i++) {
                int nx = curx + dx[i];
                int ny = cury + dy[i];
                if (0 <= nx && nx < row && ny >= 0 && ny < colum &&
                        check[nx][ny] == false &&
                        array[nx][ny] == 0) { // 바이러스는 벽(1)인 곳은 퍼질 수 없음.
                    check[nx][ny] = true;
                    q.add(new int[]{nx, ny});
                }
            }
        }

        // 안전영역의 개수 구하기
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colum; j++) {
                // 0인 공간이면서 바이러스가 퍼지지 않은 false인 곳
                if (array[i][j] == 0 && check[i][j] == false) {
                    count++;
                }
            }
        }
        return count;
    }
}
