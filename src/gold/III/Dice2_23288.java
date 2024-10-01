package gold.III;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Dice2_23288 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map;
    static int N, M, K;
    static int[] dy = {0, 1, 0, -1}; //동 남 서 북
    static int[] dx = {1, 0, -1, 0};
    static int move = 0; //방향정보의 인덱스로 사용된다.
    static int[] dice = new int[]{1, 6, 5, 2, 4, 3}; //초기 주사위면에 쓰여진 숫자
    static int TOP = 0, BOTTOM = 1, FRONT = 2, REAR = 3, LEFT = 4, RIGHT = 5;
    static int X = 0, Y = 0; //주사위의 시작위치이자 현재위치를 뜻한다.
    static int score = 0;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //row
        M = Integer.parseInt(st.nextToken()); //column
        K = Integer.parseInt(st.nextToken()); //이동 횟수

        map = new int[N][M];
        // 1. 지도 입력
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                map[n][m] = Integer.parseInt(st.nextToken());
            }
        }

        for (int k = 0; k < K; k++) {
            //1) 주사위 굴리기
            moveDice();
            //2) 얻을 수 있는 점수 체크 후 점수 합산
            getScore();
            //3) 방향 바꾸기
            changeDirection();
        }

        System.out.println(score);
    }

    private static void changeDirection() {
        //주사위 아랫면에 있는 값과 해당 칸의 정수를 비교한다.
        if (dice[BOTTOM] > map[Y][X]) {
            //시계방향회전
            move = (move + 1) % 4;
        } else if (dice[BOTTOM] < map[Y][X]) {
            //반시계방향회전
            move = (move + 3) % 4;
        }
    }

    private static void getScore() {
        //점수의 합 : 현재 값과 같은 값인 칸의 개수(자신도 포함) * 현재 칸의 값
        Queue<Info> queue = new LinkedList<>();
        boolean[][] check = new boolean[N][M];
        int count = 1; //이동한 칸의 점수 계산을 위해 이동할 수 있는 칸의 수를 저장한다.

        queue.add(new Info(Y, X));
        check[Y][X] = true;

        while (!queue.isEmpty()) {
            Info curr = queue.poll();

            //이웃한 네 방향에서 자신과 같은 숫자가 있는지 찾는다.
            for (int i = 0; i < 4; i++) {
                int nextRow = curr.row + dy[i];
                int nextColumn = curr.column + dx[i];

                //map의 범위안에 속하는 값인지, 이미 선택을 한 곳인지 검증한다.
                if (nextRow < 0 || nextRow >= N || nextColumn < 0 || nextColumn >= M || check[nextRow][nextColumn]) {
                    continue;
                }
                if (map[Y][X] == map[nextRow][nextColumn]) {
                    queue.add(new Info(nextRow, nextColumn));
                    check[nextRow][nextColumn] = true;
                    count += 1;
                }
            }
        }
        score += count * map[Y][X]; //이동한 칸의 점수를 더해나간다.
    }

    private static void moveDice() {
        //최초 동쪽으로 움직인다.
        int nx = X + dx[move]; //방향이 바뀌기 전까지 주사위를 한 방향으로 움직인다.
        int ny = Y + dy[move];

        if (nx < 0 || nx >= M || ny < 0 || ny >= N) {
            //범위를 넘어가면 방향을 반대로 바꾼다.
            move = (move + 2) % 4;
        }

        // 2. 주사위 굴리기(방향)
        switch (move) {
            case 0: //동
                rotate(BOTTOM, RIGHT, TOP, LEFT);
                break;
            case 1: //남
                rotate(BOTTOM, FRONT, TOP, REAR);
                break;
            case 2: //서
                rotate(BOTTOM, LEFT, TOP, RIGHT);
                break;
            case 3: //북
                rotate(BOTTOM, REAR, TOP, FRONT);
                break;
        }
        X += dx[move];
        Y += dy[move];
    }

    private static void rotate(int a, int b, int c, int d) {
        int temp = dice[a];
        dice[a] = dice[b];
        dice[b] = dice[c];
        dice[c] = dice[d];
        dice[d] = temp;
    }

    public static class Info {
        int row, column;

        public Info(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
