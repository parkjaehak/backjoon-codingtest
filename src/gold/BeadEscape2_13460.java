package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BeadEscape2_13460 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map;
    static int N, M;
    static int[] dy = {-1, 0, 1, 0};//북 동 남 서
    static int[] dx = {0, 1, 0, -1};
    static int blank = 0;
    static int wall = 1;
    static int hole = 2;
    static int Red = 3;
    static int Blue = 4;
    static Info red, blue;
    static boolean[][][][]visited;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //세로
        M = Integer.parseInt(st.nextToken()); //가로

        map = new int[N][M];
        visited = new boolean[N][M][N][M];

        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            int num;
            for (int j = 0; j < M; j++) {
                char input = line.charAt(j);

                map[i][j] = parseChar(input, i,j);
            }
        }
        System.out.println(bfs());

    }

    private static int parseChar(char input, int i, int j) {
        int num;
        if (input == '#') {
            num = wall;
        } else if (input == '.') {
            num = blank;
        } else if (input == 'O') {
            num = hole;
        } else if (input == 'R') {
            num = Red;
            red = new Info(i, j, 0, 0, 0);
        } else {
            num = Blue;
            blue = new Info(0,0, i, j,  0);
        }
        return num;
    }

    private static int bfs() {
        Queue<Info> queue = new LinkedList<>();
        queue.add(new Info(red.ry, red.rx, blue.by, blue.bx, 1));
        visited[red.ry][red.rx][blue.by][blue.bx] = true;

        while (!queue.isEmpty()) {
            Info curr = queue.poll();
            int curRy = curr.ry;
            int curRx = curr.rx;
            int curBy = curr.by;
            int curBx = curr.bx;
            int moveCount = curr.cnt;

            if (moveCount > 10) {
                return -1;
            }

            for (int i = 0; i < 4; i++) {
                int newRy = curRy;
                int newRx = curRx;
                int newBy = curBy;
                int newBx = curBx;

                boolean isRedHole = false;
                boolean isBlueHole = false;

                //빨간 구슬이 벽 만날때까지 이동시킨다.
                while (map[newRy + dy[i]][newRx + dx[i]] != wall) {
                    newRy += dy[i];
                    newRx += dx[i];

                    if (map[newRy][newRx] == hole) {
                        isRedHole = true;
                        break;
                    }
                }
                //파란 구슬이 벽 만날때까지 이동시킨다.
                while (map[newBy + dy[i]][newBx + dx[i]] != wall) {
                    newBy += dy[i];
                    newBx += dx[i];

                    if (map[newBy][newBx] == hole) {
                        isBlueHole = true;
                        break;
                    }
                }

                if(isBlueHole) { // 파란 구슬이 구멍에 빠지면 무조건 실패
                    continue;
                }
                if(isRedHole) { // 빨간 구슬만 구멍에 빠지면 성공
                    return moveCount;
                }

                //이동 위치가 겹치는 경우
                if (newRy == newBy && newRx == newBx) {
                    if (i == 0) {
                        //북
                        if (curRy > curBy) {
                            newRy -= dy[i];
                        } else {
                            newBy -= dy[i];
                        }
                    } else if (i == 1) {
                        //동
                        if (curRx > curBx) {
                            newBx -= dx[i];
                        } else {
                            newRx -= dx[i];
                        }
                    } else if (i == 2) {
                        //남
                        if (curRy > curBy) {
                            newBy -= dy[i];
                        } else {
                            newRy -= dy[i];
                        }
                    } else {
                        //서
                        if (curRx > curBx) {
                            newRx -= dx[i];
                        } else {
                            newBx -= dx[i];
                        }
                    }
                }

                if (!visited[newRy][newRx][newBy][newBx]) {
                    visited[newRy][newRx][newBy][newBx] = true;
                    queue.add(new Info(newRy, newRx, newBy, newBx, moveCount + 1));
                }
            }
        }
        return -1;
    }

    static class Info {
        int ry, rx, by, bx, cnt;

        public Info(int ry, int rx, int by, int bx, int cnt) {
            this.ry = ry;
            this.rx = rx;
            this.by = by;
            this.bx = bx;
            this.cnt = cnt;
        }
    }
}
