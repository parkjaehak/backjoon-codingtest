package gold;

import java.io.*;
import java.util.StringTokenizer;

public class DustGoodBye17144 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int R, C, T;
    static int[][] map;
    static int[][] airLoc = new int[2][2]; //공기청정기의 위치 좌표(최대 두개까지만 사용가능)
    static int[] dy = {0, 1, 0, -1}; //동 남 서 북
    static int[] dx = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken()); //row
        C = Integer.parseInt(st.nextToken()); //column
        T = Integer.parseInt(st.nextToken()); //T초 후 미세먼지의 양

        map = new int[R][C];
        for (int r = 0; r < R; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < C; c++) {
                map[r][c] = Integer.parseInt(st.nextToken());

                if (map[r][c] == -1) {
                    if (airLoc[0][0] == 0) {
                        //한번도 저장한 적 없으면 그곳에 저장한다.
                        airLoc[0][0] = r;
                        airLoc[0][1] = c;
                    } else {
                        airLoc[1][0] = r;
                        airLoc[1][1] = c;
                    }
                }
            }
        }

        for (int t = 0; t < T; t++) {
            //T초동안 확산과 작동을 반복한다.
            spread();
            cleanUp(); //반시계 방향
            cleanDown(); //시계 방향
        }

        //남아있는 미세먼지의 양을 출력한다.
        int result = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (map[r][c] == -1) {
                    continue;
                }
                result += map[r][c];
            }
        }
        System.out.println(result);
    }

    private static void cleanDown() {
        //시계방향으로 공기청정기가 작동한다.
        int row = airLoc[1][0];
        int column = airLoc[1][1];

        //아래->위
        for (int r = row + 1; r < R - 1; r++) {
            int nr = r + 1;
            map[r][0] = map[nr][0];
        }
        //오른쪽->왼쪽
        for (int c = 0; c < C - 1; c++) {
            int nc = c + 1;
            map[R - 1][c] = map[R - 1][nc];
        }
        //위->아래
        for (int r = R - 1; r > row; r--) {
            int nr = r - 1;
            map[r][C - 1] = map[nr][C - 1];
        }
        //왼쪽->오른쪽
        for (int c = C - 1; c > 1; c--) {
            int nc = c - 1;
            map[row][c] = map[row][nc];
        }
        //공기청정기는 오른쪽으로 0을 한칸 확산시킨다.
        map[row][column + 1] = 0;
    }

    private static void cleanUp() {
        //반시계방향으로 공기청정기가 작동한다.
        int row = airLoc[0][0];
        int column = airLoc[0][1];

        //위->아래
        for (int r = row - 2; r >= 0; r--) {
            //현재 공기청정기 위치 다음부터 맨위까지 반복한다.
            int nr = r + 1;
            map[nr][0] = map[r][0]; //현재 row의 값을 다음 row로 이동시킨다.
        }
        //오른쪽->왼쪽
        for (int c = 0; c < C - 1; c++) {
            int nc = c + 1;
            map[0][c] = map[0][nc];
        }
        //아래->위
        for (int r = 0; r < row; r++) {
            int nr = r + 1;
            map[r][C - 1] = map[nr][C - 1];
        }
        //왼쪽->오른쪽
        for (int c = C - 1; c > 1; c--) {
            int nc = c - 1;
            map[row][c] = map[row][nc];
        }
        //공기청정기는 오른쪽으로 0을 한칸 확산시킨다.
        map[row][column + 1] = 0;
    }

    private static void spread() {
        //map2를 0으로 초기화
        int[][] map2 = new int[R][C];

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {

                int remain = map[r][c]; //현재 잔존하는 양
                int spread = map[r][c] / 5; //확산되는 양

                for (int k = 0; k < 4; k++) {
                    //상하좌우 미세먼지를 분배
                    int nr = r + dy[k];
                    int nc = c + dx[k];

                    //범위를 벗어나거나 해당 방향에 공기청정기가 존재한다면 확산이 일어나지 않는다.
                    if (nr >= R || nr < 0 || nc >= C || nc < 0 || map[nr][nc] == -1) {
                        continue;
                    }
                    map2[nr][nc] += spread; //확산하는 양을 map2에 저장한다.
                    remain -= spread;
                }
                map[r][c] = remain; //상화좌우에 미세먼지를 분배하고 남은 양을 map에 갱신한다.
            }
        }

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                map[r][c] += map2[r][c];
            }
        }
    }
}
