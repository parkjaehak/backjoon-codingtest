package gold.II;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class YouthShark19236 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map = new int[4][4];
    static int[] dy = {0, -1, -1, 0, 1, 1, 1, 0, -1}; //북, 북서, 서, 서남, 남, 남동, 동 , 동북 : 인덱스는 1부터 첫인덱스는 무시한다.
    static int[] dx = {0, 0, -1, -1, -1, 0, 1, 1, 1};
    static int shark = -1; //상어의 번호
    static int blank = 0; //상어가 잡어먹고 달아난 후 생긴 빈 공간
    static int result = Integer.MIN_VALUE;
    static Fish[] fish = new Fish[17]; //1~16번의 물고기를 생성한다.

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 4; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                int num = Integer.parseInt(st.nextToken()); //1과 16사이의 물고기 번호가 저장된다.
                int dir = Integer.parseInt(st.nextToken());//1부터 8까지 방향 인덱스가 저장된다.
                fish[num] = new Fish(num, i, j, dir, true);
                map[i][j] = num;
            }
        }


        //1. 상어는 0,0의 물고기를 먹고 해당 물고기의 방향을 갖는다.
        int fishNum = map[0][0];
        fish[fishNum].isAlive = false; //초기 칸의 물고기는 잡아먹힌다.
        map[0][0] = shark; //상어 초기 위치는 먹은 물고기의 위치
        int sharkDir = fish[fishNum].dir; //상어 초기 방향은 먹은 물고기의 방향
        int score = fishNum; //먹은 물고기 번호의 합

        dfs(0, 0, sharkDir, score); //모든 상어의 경로를 구해보며 물고기를 먹어본다.
        System.out.println(result);
    }

    private static void dfs(int cr, int cc, int sharkDir, int score) {
        result = Math.max(result, score);

        //map 복사한다.
        int[][] copyMap = new int[4][4];
        for (int i = 0; i < 4; i++) {
            //map 배열의 i번째 행을 copy 배열의 i번째 행에 복사한다.
            //원시타입 배열이기때문에 각 요소의 값이 독립적으로 복사되고 깊은 복사가 일어난다.
            System.arraycopy(map[i], 0, copyMap[i], 0, map.length);
        }
        Fish[] copyFish = new Fish[17];
        for (int i = 1; i <= 16; i++) {
            //여기서 arraycopy는 객체 타입배열이기때문에 얕은 복사가 일어나 사용할 수 없다. 따라서 직접 new를 통해 새로 객체를 생성한다.
            copyFish[i] = new Fish(fish[i].num, fish[i].row, fish[i].column, fish[i].dir, fish[i].isAlive);
        }

        //2. 물고기를 이동시킨다.
        moveFish();

        //3. 상어를 이동시킨다.
        for (int i = 1; i < 4; i++) {
            int nr = cr + dy[sharkDir] * i;
            int nc = cc + dx[sharkDir] * i;

            if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4 || map[nr][nc] == blank) {
                continue;
            }
            map[cr][cc] = blank; //상어가 이동한 후 기존 칸은 빈칸이된다.
            int nextNum = map[nr][nc]; //이동한 칸의 물고기 번호
            int nextDir = fish[nextNum].dir;
            fish[nextNum].isAlive = false;
            map[nr][nc] = shark;

            dfs(nr, nc, nextDir, score + nextNum);

            map[nr][nc] = nextNum;
            fish[nextNum].isAlive = true;
            map[cr][cc] = shark;
        }
        for (int i = 0; i < 4; i++) {
            System.arraycopy(copyMap[i], 0, map[i], 0, map.length);
        }
        for (int i = 1; i <= 16; i++) {
            fish[i] = new Fish(copyFish[i].num, copyFish[i].row, copyFish[i].column, copyFish[i].dir, copyFish[i].isAlive);
        }
    }

    private static void moveFish() {
        //물고기는 번호 순서대로 움직인다.
        for (int i = 1; i < 17; i++) {
            if (!fish[i].isAlive) {
                //물고기가 죽었다면 패스
                continue;
            }

            int fishDir = fish[i].dir;
            int cr = fish[i].row;
            int cc = fish[i].column;
            int initialFishDir = fishDir;  // 처음 방향 저장

            while (true) {
                int nr = dy[fishDir] + cr;
                int nc = dx[fishDir] + cc;

                //경계를 넘거나 상어가 있는 칸으로는 이동할 수 없다.
                if (nr < 0 || nr >= 4 || nc < 0 || nc >= 4 || map[nr][nc] == shark) {
                    fishDir = (fishDir % 8) + 1;
                    fish[i].dir = fishDir; //방향 바꿨다면 바뀐 것 적용한다.
                    //한바퀴가 돌아 찾지 못하면 움직이지 않는다.
                    if (fishDir == initialFishDir) {
                        fish[i].dir = initialFishDir;
                        break;  // 더 이상 이동할 수 없으므로 while문을 종료한다.
                    }
                } else {
                    // 물고기의 번호와 위치를 교환합니다.
                    int tempFishNum = map[cr][cc];
                    int targetFishNum = map[nr][nc];

                    // 현재 위치 물고기 정보 갱신
                    fish[tempFishNum].row = nr;
                    fish[tempFishNum].column = nc;

                    // 이동할 위치의 물고기 정보 갱신(빈칸인 경우에는 fish 배열에 저장할 필요가 없다.)
                    if (targetFishNum != 0) {
                        fish[targetFishNum].row = cr;
                        fish[targetFishNum].column = cc;
                    }

                    // 지도에서 물고기 번호 교환
                    map[nr][nc] = tempFishNum;
                    map[cr][cc] = targetFishNum;

                    break;
                }
            }
        }
    }

    static class Fish {
        int num, row, column, dir;
        boolean isAlive;

        public Fish(int num, int row, int column, int dir, boolean isAlive) {
            this.num = num;
            this.row = row;
            this.column = column;
            this.dir = dir;
            this.isAlive = isAlive;
        }
    }
}
