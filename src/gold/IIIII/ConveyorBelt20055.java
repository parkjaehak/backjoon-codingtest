package gold.IIIII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class ConveyorBelt20055 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] belt; //내구도를 저장한다.
    static boolean[][] check; //로봇의 위치
    static int N, K;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //내리는 위치
        K = Integer.parseInt(st.nextToken()); //k 내구도 이상이면 종료

        belt = new int[2][N];
        check = new boolean[2][N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            belt[0][i] = Integer.parseInt(st.nextToken());
        }
        for (int i = N - 1; i >= 0; i--) {
            belt[1][i] = Integer.parseInt(st.nextToken());
        }

        boolean run = true;
        int result = 0;
        while (run) {
            //1. 벨트를 한 칸 회전시킨다.
            rotate();
            //2. 벨트에 올라간 로봇부터 벨트회전방향으로 한 칸 이동한다. 해당 칸의 내구도 1을 감소시킨다.
            moveRobot();
            //3. 올리는 위치에서 로봇을 올린다. 해당 칸의 내구도 1을 감소시킨다.
            setRobot();
            //4. 내구도 0인 칸의 개수가 K개 이상이면 종료하고 그렇지 않으면 1로 돌아간다.
            run = calDurability();
            result++;
        }
        System.out.println(result);
    }

    private static boolean calDurability() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (belt[0][i] == 0) {
                count++;
            }
            if (belt[1][i] == 0) {
                count++;
            }
        }
        if (count >= K) {
            return false;
        } else {
            return true;
        }
    }

    private static void setRobot() {
        if (belt[0][0] >= 1) {
            check[0][0] = true;
            belt[0][0]--;
        }
    }

    private static void moveRobot() {
        //로봇을 움직인다.
        //이동하려는 위치에 로봇이 없고, 내구도가 1이상이어야한다.
        for (int i = N - 1; i >= 0; i--) {
            if (i == N - 1) {
                if (check[0][i]) {
                    check[0][i] = false;
                }
                continue;
            }

            if (check[0][i]) {
                //로봇이 있는지 확인한다.
                if (!check[0][i + 1] && belt[0][i + 1] >= 1) {
                    //그 다음칸에 로봇이 없고 내구도가 1이상 남아있으면
                    check[0][i] = false;
                    check[0][i + 1] = true;
                    belt[0][i + 1]--;
                }
            }
        }
    }

    private static void rotate() {
        int durUp, durDown; //각 칸의 내구도 정보

        durUp = belt[0][N - 1];
        durDown = belt[1][0];

        for (int i = N - 2; i >= 0; i--) {
            belt[0][i + 1] = belt[0][i];
            check[0][i + 1] = check[0][i];
        }
        for (int i = 1; i < N; i++) {
            belt[1][i - 1] = belt[1][i];
        }
        belt[1][N - 1] = durUp;
        belt[0][0] = durDown;
        check[0][0] = false;
    }
}
