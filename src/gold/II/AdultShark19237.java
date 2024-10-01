package gold.II;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class AdultShark19237 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M, k;
    static int [][]red, blue;
    static int[][][]priority;
    static Shark[] sharks;
    static int[] dy = {0, -1, 1, 0, 0};
    static int[] dx = {0, 0, 0, -1, 1}; //상 하 좌 우


    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        red = new int[N + 1][N + 1]; //냄새가 없어지기 까지 남은 시간
        blue = new int[N + 1][N + 1]; //냄새의 원인인 상어번호
        priority = new int[M + 1][5][4]; //M번 상어, 상하좌우(1~4), 우선순위(0~3)
        sharks = new Shark[M + 1];

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                int num = Integer.parseInt(st.nextToken());
                if (num > 0) {
                    sharks[num] = new Shark(i, j, 0); //현재 방향정보는 알 수 없다.
                    red[i][j] = k; //현재칸의 남은 냄새 시간 (red)
                    blue[i][j] = num; //냄새의 원인인 상어번호 (blue)

                }
            }
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            sharks[i].dir = Integer.parseInt(st.nextToken()); //초기 상어들의 방향정보 저장한다.
        }

        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= 4; j++) {
                st = new StringTokenizer(br.readLine());
                for (int l = 0; l <= 3; l++) {
                    priority[i][j][l] =Integer.parseInt(st.nextToken()); //M번 상어의 방향에 따른 우선순위
                }
            }
        }

        System.out.println(getTime());
    }

    private static int getTime() {
        int time = 0;

        while (true) {
            int count = 0;
            //1. 현재 남아있는 상어의 수를 센다
            for (int m = 1; m <= M; m++) {
                if (sharks[m] != null) {
                    count++;
                }
            }

            //2. 상어 1만 남은 경우 해당 시간을 반환한다.
            if (count == 1 && sharks[1] != null) {
                return time;
            }

            //3. 시간이 1000초가 넘으면 -1을 return 한다.
            if (time >= 1000) {
                return -1;
            }

            //4. 상어를 움직인다.
            int[][] smellOfShark = new int[N + 1][N + 1]; //이동을 마친 위치의 상어 정보를 담는 배열
            for (int m = 1; m <= M; m++) {
                if (sharks[m] != null) {
                    move(smellOfShark, m);
                }
            }

            //5. 상어가 움직이기 직전까지 존재했던 남은 시간을 1씩 감소한다.
            for (int i = 1; i <= N; i++) {
                for (int j = 0; j <= N; j++) {
                    if (red[i][j] > 0) {
                        red[i][j]--;
                    }
                    if (red[i][j] == 0) {
                        blue[i][j] = 0; //남은 시간이 0이면 해당 칸의 냄새 정보도 초기화 한다.
                    }
                }
            }

            //6. 상어가 움직인 후 해당 위치의 냄새원인 상어와 남은 시간을 설정한다.
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (smellOfShark[i][j] > 0) {
                        //새로 상어가 이동한 칸에 상어가 존재하면 남은시간정보와 냄새 정보를 초기화하여 저장한다.
                        red[i][j] = k;
                        blue[i][j] = smellOfShark[i][j];
                    }
                }
            }
            time++;
        }

    }

    private static void move(int[][] smellOfShark, int m) {
        int nr = 0, nc = 0, dir = 0;
        boolean flag = false;

        //1. 냄새가 없는 곳을 찾는다.
        for (int i = 0; i < 4; i++) {
            dir = priority[m][sharks[m].dir][i]; //현재 상어의 위치에서 우선순위에 따라 냄새가 없는 곳을 찾는다.
            nr = sharks[m].row + dy[dir];
            nc = sharks[m].column + dx[dir];

            if (nr >= 1 && nr <= N && nc >= 1 && nc <= N && blue[nr][nc] == 0) {
                //이동한 위치가 범위 내에 있고 해당 칸에 냄새가 없을 경우 이동한다.
                flag = true;
                break;
            }
        }

        //2. 냄새가 없는 곳을 찾지 못한 경우
        if (!flag) {
            for (int i = 0; i < 4; i++) {
                dir = priority[m][sharks[m].dir][i];
                nr = sharks[m].row + dy[dir];
                nc = sharks[m].column + dx[dir];

                //자신의 냄새가 있는 방향으로 설정하고 여러 개이면 우선순위를 따른다.(주위에 내 냄새가 없는 칸이 존재할 수 는 없다.)
                if (nr >= 1 && nr <= N && nc >= 1 && nc <= N && blue[nr][nc] == m) {
                    break;
                }
            }
        }
        if (smellOfShark[nr][nc] == 0) {
            smellOfShark[nr][nc] = m;
            sharks[m].row = nr;
            sharks[m].column = nc;
            sharks[m].dir = dir;
        } else {
            sharks[m] = null; //해당 칸에 상어가 있을 경우 더 큰 상어는 제거시킨다.
        }
    }

    static class Shark {
        int row, column, dir;

        public Shark(int row, int column, int dir) {
            this.row = row;
            this.column = column;
            this.dir = dir;
        }
    }
}
