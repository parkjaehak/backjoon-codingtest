package gold.IIIII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 주어진 정보
 * 1. 톱니바퀴는 4개, 가장 왼쪽부터 1번 2번 3번 4번
 * --> 톱니바퀴는 12시 방향부터 입력 (12시 부터 순서대로 0~7까지 인덱스 할당)
 * --> 맞닿는 부분 인덱스
 * 1번 : 2
 * 2번 : 2, 6
 * 3번 : 2, 6
 * 4번 : 6
 * 2. 시계방향 1, 반시계방향 -1
 * 3. N극 0, S극 1
 * 4. 회전 규칙
 * --> 입력받은 톱니바퀴 기준 맞닿는 부분의 극이 같은 경우 : 회전 x
 * --> 다른 경우 : 반대방향 회전 --> 전파(맞닿는 부분의 극이 같은 경우까지)
 * 5.
 */

public class Gear14891 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] gears = new int[4][8];
    static int score;

    public Gear14891() throws IOException {

        //1. 톱니바퀴의 극 값을 입력 (12시 방향 기준 인덱스 : 0)
        for (int i = 0; i < 4; i++) {
            String gearPoles = br.readLine();
            for (int j = 0; j < 8; j++) {
                //공백이 없기 때문에 string을 입력 후 하나하나 integer로 변환한다.
                char gearPole = gearPoles.charAt(j);
                int gearPoleNumber = Character.getNumericValue(gearPole);
                gears[i][j] = gearPoleNumber;
            }
        }

        int K = Integer.parseInt(br.readLine()); //회전 횟수
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int gearNumber = Integer.parseInt(st.nextToken()) - 1; //회전시킨 톱니바퀴 번호(가장 왼쪽부터 0,1,2,3)
            int direction = Integer.parseInt(st.nextToken()); //방향 (1: 시계, -1: 반시계)

            //2. 입력받은 값과 조건에 부합시키며 회전을 진행한다. (돌릴 톱니바퀴, 방향, 트리거: 0양쪽, 1오른쪽, -1왼쪽으로 이동)
            rotate(gearNumber, direction, 0);
        }
        score();

        System.out.println(score);
    }

    public static void rotate(int currentGear, int direction, int trigger) {
        int leftGear = currentGear - 1;
        int rightGear = currentGear + 1;

        //3.먼저 내 양쪽 기어가 존재하는지부터 확인한다.
        if (trigger == 0) {
            if (leftGear >= 0) {
                if (gears[leftGear][2] != gears[currentGear][6]) {  //4.기어가 존재한다면 내 양 옆 기어와 맞물리는 부분이 일치하는지 확인한다.
                   //5.맞불리는 부분이 일치하지 않는 경우, 내 옆의 기어를 나와는 반대방향으로 회전시킨다.
                    rotate(leftGear, direction*-1, -1);
                }
            }
            if (rightGear <= 3) {
                if (gears[rightGear][6] != gears[currentGear][2]) {
                    rotate(rightGear, direction*-1, 1);
                }
            }
        } else if (trigger == -1) {
            if (leftGear >= 0) { //움직일 수 있는지 체크
                if (gears[leftGear][2] != gears[currentGear][6]) { //왼쪽 기어와 현재 기어의 맞물리는 극이 다른 경우
                    rotate(leftGear, direction*-1, -1);
                }
            }
        } else if (trigger == 1) {
            if (rightGear <= 3) {
                if (gears[rightGear][6] != gears[currentGear][2]) {
                    rotate(rightGear, direction*-1, 1);
                }
            }
        }
        if (direction == 1) { //반대인 방향으로 회전시킨다
            clockWise(currentGear);
        } else {
            counterClockWise(currentGear);
        }
    }

    private static void clockWise(int gearNumber) {
        int temp = gears[gearNumber][0];
        gears[gearNumber][0] = gears[gearNumber][7];
        gears[gearNumber][7] = gears[gearNumber][6];
        gears[gearNumber][6] = gears[gearNumber][5];
        gears[gearNumber][5] = gears[gearNumber][4];
        gears[gearNumber][4] = gears[gearNumber][3];
        gears[gearNumber][3] = gears[gearNumber][2];
        gears[gearNumber][2] = gears[gearNumber][1];
        gears[gearNumber][1] = temp;
    }

    private static void counterClockWise(int gearNumber) {
        int temp = gears[gearNumber][0];
        gears[gearNumber][0] = gears[gearNumber][1];
        gears[gearNumber][1] = gears[gearNumber][2];
        gears[gearNumber][2] = gears[gearNumber][3];
        gears[gearNumber][3] = gears[gearNumber][4];
        gears[gearNumber][4] = gears[gearNumber][5];
        gears[gearNumber][5] = gears[gearNumber][6];
        gears[gearNumber][6] = gears[gearNumber][7];
        gears[gearNumber][7] = temp;
    }

    public static void score() {
        if (gears[0][0] == 1) {
            score += 1;
        }
        if (gears[1][0] == 1) {
            score += 2;
        }
        if (gears[2][0] == 1) {
            score += 4;
        }
        if (gears[3][0] == 1) {
            score += 8;
        }
    }
}
