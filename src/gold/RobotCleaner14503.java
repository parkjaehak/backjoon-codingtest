package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.StringTokenizer;

/**
 * 1. 방의 크기를 입력받고 배열을 생성한다.
 * 2. 처음 청소기가 위치한 좌표, 바로보는 방향을 입력받는다 (0: 북, 1: 동, 2: 남, 3: 서)
 * 3. 가장자리는 모두 1이다.
 * 4. 청소로직
 * --> (1) 현재 위치가 청소되지 않은 경우, 현재 칸 청소
 * --> (2) 현재 위치 북동남서 방향 모두 청소된 경우
 * --> 후진 가능 : 후진 후 (1)번 실행
 * --> 후진 불가능 : 벽이면 작동 멈춤
 * --> (3) 현재 위치 북동남서 중 청소되지 않은 칸 있는 경우
 * --> 반시계(좌)90 도 회전하여 바라보는 방향 앞향 기준 앞 청소되지 않았으면 전진 후 청소
 */
public class RobotCleaner14503 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] room;
    static int cleaned; // 청소된 블럭의 개수
    static int R, C, D;
    static int[] dx = {-1, 0, 1, 0}; //북 동 남 서
    static int[] dy = {0, 1, 0, -1};
    static boolean[][] check; //기본형의 default는 false

    public RobotCleaner14503() throws IOException {
        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken()); // 방 크기

        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken()); // 현재 위치
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken()); // 바라보는 위치 (0: 북, 1: 동, 2: 남, 3: 서)

        // 방 생성
        room = new int[N][M];
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                room[n][m] = Integer.parseInt(st.nextToken());
            }
        }

        // 1. 현재 칸이 청소되지 않은 경우 청소한다.
        boolean run = true;
        check = new boolean[N][M];
        while (run) {
            if (room[R][C] == 0 && (check[R][C] == false)) {
                clean(R, C);
            }
            //3. 반시계 회전 하며 앞 칸이 청소되지 않은 경우 전진한다.
            boolean moved = false;
            for (int i = 0; i < 4; i++) {
                D = turn(D);
                int nx = R + dx[D];
                int ny = C + dy[D];

                if (room[nx][ny] == 0 && (check[nx][ny] == false)) {
                    R = nx;
                    C = ny;
                    moved = true;
                    break;
                    // 네 방향 중 한 방향 청소 하기 위해 앞으로 이동 후 break 하면 다시 while 문으로 이동해야함
                }
            }
            //2. 네 방향 모두 청소되어 있는 경우 후진 여부 파악해 후진한다.
            if (moved == false) {
                run = back(D);
            }
        }
        System.out.println(cleaned);
    }

    //청소
    public static void clean(int row, int column) {
        check[row][column] = true;
        cleaned++;
    }

    //회전 - 반 시계 방향
    public static int turn(int dir) {
        dir--;
        if (dir == -1) {
            dir = 3;
        }
        return dir;
    }

    //후진 - 후진 여부 반환
    public static boolean back(int dir) {
        // 현재 보는 방향의 반대로 이동
        R -= dx[dir];
        C -= dy[dir];
        if (room[R][C] == 1) { // 후진 시 벽이 있어 후진 못한 경우 작동 멈춘다.
            // 다시 원상복구
            R += dx[dir];
            C += dy[dir];
            return false;
        }
        return true;
    }
}
