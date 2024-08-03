package gold;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Dice14499 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int N, M, X, Y, K;
    static int TOP = 0, BOTTOM = 1, FRONT = 2, REAR = 3, LEFT = 4, RIGHT = 5;

    static int[][] map;
    static int[] dice = new int[6];

    //주사위 이동방향: 동, 서, 북, 남
    static int[] dx = {0, 0, -1, 1}; //x값은 세로 = 상하방향, 주사위 판을 위에서부터 아래로 저장하기 때문에 위로 올라가려면 북으로 갈때 -1 해야 함
    static int[] dy = {1, -1, 0, 0}; //y값은 가로 = 좌우방향

    public Dice14499() throws IOException {
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); //row
        M = Integer.parseInt(st.nextToken()); //column
        X = Integer.parseInt(st.nextToken()); //주사위 시작 위치
        Y = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken()); //명령 횟수

        map = new int[N][M];
        // 1. 지도 입력
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                map[n][m] = Integer.parseInt(st.nextToken());
            }
        }


        st = new StringTokenizer(br.readLine());
        for (int k = 0; k < K; k++) {
            int move = Integer.parseInt(st.nextToken());
            // 굴린 방향으로 방향 최신화
            X += dx[move - 1];
            Y += dy[move - 1];
            if (X < 0 || X >= N || Y < 0 || Y >= M) {
                continue;
            }


            // 2. 주사위 굴리기(방향)
            switch (move) {
                case 4: //남
                    rotate(BOTTOM, REAR, TOP, FRONT);
                    break;
                case 3: //북
                    rotate(BOTTOM, FRONT, TOP, REAR);
                    break;
                case 2: //서
                    rotate(BOTTOM, RIGHT, TOP, LEFT);
                    break;
                case 1: //동
                    rotate(BOTTOM, LEFT, TOP, RIGHT);
                    break;
            }

            // 3. 바닥과 주사위의 상호작용
            if (map[X][Y] == 0) { //지도의 값이 0이면 주사위 바닥면 값을 지도로 복사
                map[X][Y] = dice[BOTTOM];
            } else {//지도의 값이 0이 아닌 경우 주사위 바닥면으로 복사하고 지도는 0
                dice[BOTTOM] = map[X][Y];
                map[X][Y] = 0;
            }

            // 4. 주사위 윗면의 값 반환
            sb.append(dice[TOP]).append("\n");
        }
        System.out.println(sb);
    }

    public void rotate(int a, int b, int c, int d) {
        int temp = dice[a];
        dice[a] = dice[b];
        dice[b] = dice[c];
        dice[c] = dice[d];
        dice[d] = temp;
    }
}



