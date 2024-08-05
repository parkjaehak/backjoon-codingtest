package gold;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 정보
 * 1. 보드의 크기
 * 2. 사과의 위치
 * 3. 뱀의 방향 정보 : x초 후 L 또는 D
 * 3. 뱀
 * --> 위치 : 최초 (0,0), 오른쪽 바라보고 있음, 매 초마다 한 칸씩 이동하고 사과를 먹으면 꼬리를 유지
 * --> 규칙 : 자신의 몸이나 보드 경계를 나갈 경우 게임 오버
 * 뱀은 x초 후에 방향을 변경가능
 * <p>
 * 4. 결과
 * --> 게임 종료 시간
 */
public class Snake3190 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] board;
    static int[][]apples;
    static int[] dx = {1, 0, -1, 0};
    static int[] dy = {0, 1, 0, -1}; //동 남 서 북
    static Map<Integer, String> secondShift = new HashMap<>();
    static LinkedList<int[]> snake = new LinkedList<>();
    static int N;
    static int direction = 0; //최초 동쪽을 바라보고 있음

    public Snake3190() throws IOException {
        N = Integer.parseInt(br.readLine()); //보드의 크기
        int K = Integer.parseInt(br.readLine()); //사과의 개수

        board = new int[N + 1][N + 1];
        apples = new int[N + 1][N + 1];
        for (int k = 0; k < K; k++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken()); //사과의 위치 row
            int x = Integer.parseInt(st.nextToken()); //사과의 위치 column
            apples[y][x] = 1; //보드에 사과 위치 지정
        }

        int L = Integer.parseInt(br.readLine()); //회전 정보 횟수(L 이후에도 생각해 주어야 함)
        for (int l = 0; l < L; l++) {
            int second;
            String shift;

            st = new StringTokenizer(br.readLine());
            second = Integer.parseInt(st.nextToken()); //s초 뒤
            shift = st.nextToken(); //L, D 둘 중 하나로 회전
            secondShift.put(second, shift);
        }

        board[1][1] = 2; //뱀의 최초 위치 설정
        snake.add(new int[]{1, 1});

        int currentTime = 0;
        boolean run = true;
        while (run) {
            run = move();
            currentTime++;
            turn(currentTime);
        }
        System.out.println(currentTime);
    }

    public static boolean move() {
        int[] head = snake.getFirst();

        //1. 뱀의 머리를 이동시킨다.
        int ny = head[0] + dy[direction];
        int nx = head[1] + dx[direction];

        //2. 자신의 몸에 닿았거나 벽에 닿았는지 확인한다.
        if (nx >= 1 && nx <= N && ny >= 1 && ny <= N && board[ny][nx] != 2) {
            int[] newHead = new int[]{ny, nx};
            snake.addFirst(newHead); //새로운 뱀의 머리를 가장 앞에 위치시킨다.
            board[ny][nx] = 2;
        } else {
            return false;
        }

        //3. 사과가 있는지 확인한다.
        if (apples[ny][nx] == 1) {
            apples[ny][nx] = 0;
        } else {
            // 사과가 없으면 꼬리를 없앤다.
            int[] tail = snake.getLast();
            board[tail[0]][tail[1]] = 0;
            snake.removeLast();
        }
        return true;
    }

    public static void turn(int currentTime) {
        //4. Map에 저장된 시간이 되었는지 체크 한다.
        if (secondShift.containsKey(currentTime)) {
            //해당 시간에 존재하는 shift 명령어가 있으면 실행한다.
            if (secondShift.get(currentTime).equals("L")) {
                direction -= 1;
                if (direction == -1) {
                    direction = 3;
                }
            } else {
                direction += 1;
                if (direction == 4) {
                    direction = 0;
                }
            }
        }
    }
}
