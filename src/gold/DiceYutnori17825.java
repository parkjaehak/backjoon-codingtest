package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class DiceYutnori17825 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] dice = new int[10];
    static int result = Integer.MIN_VALUE;
    static int[][] map = {
            {0, 2, 4, 6, 8, 10},
            {10, 13, 16, 19, 25},
            {10, 12, 14, 16, 18, 20},
            {20, 22, 24, 25},
            {20, 22, 24, 26, 28, 30},
            {30, 28, 27, 26, 25},
            {30, 32, 34, 36, 38, 40},
            {25, 30, 35, 40},
            {40, 0}
    };

    public static void main(String[] args) throws IOException {
        //5면체 주사위
        //말이 이동을 마치는 칸에 다른 말이 존재하면 해당 말은 선택 불가
        //얻을 수 있는 점수의 최댓값
        //10번 실행하고 모든 말이 도착할 필요는 없으며,말은 4개가 존재한다.

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 10; i++) {
            //주사위에서 나올 열 가지 수를 저장한다.
            dice[i] = Integer.parseInt(st.nextToken());
        }
        int[][] loc = new int[4][2]; //4개의 말의 위치 인덱스
        boolean[][] check = {
                {false, false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false},
                {false, false}
        };

        //1) 하나의 말을 도착할때까지 이용한다.
        //2) 새로운 말을 이동시키기 전 다음 위치에 말이 존재하는지 확인한다.

        recursive(0, loc, 0, check);
        System.out.println(result);
    }

    private static void recursive(int index, int[][] loc, int score, boolean[][] check) {
        if (index == 10) {
            result = Math.max(result, score);
            return;
        }

        //말이 4개이기 때문에 네 번 반복한다.
        for (int i = 0; i < 4; i++) {
            if (loc[i][0] == 8 && loc[i][1] == 1) {
                //말이 도착에 위치하였다면 건너뛴다.
                continue;
            }
            int startRow = loc[i][0];
            int startColumn = loc[i][1];
            int currRow = loc[i][0];
            int currColumn = loc[i][1] + dice[index];

            if (currColumn == map[currRow].length - 1) {
                //현재 위치가 파란칸인 경우
                if (currRow == 6 || currRow == 7) {
                    //6번과 7번일 경우 현재 위치는 40이다.
                    currRow = 8;
                    currColumn = 0;
                } else if (currRow < 6 && currRow % 2 == 0) {
                    //짝수번째 row는 홀수번째 row가 된다.
                    currRow++;
                    currColumn = 0;
                } else if (currRow % 2 == 1) {
                    //홀수번째 row는 7번째 row가 된다.
                    currRow = 7;
                    currColumn = 0;
                } else {
                    //7번 row의 마지막에 위치할 경우
                    currColumn = 1;
                }

            } else if (currColumn >= map[currRow].length) {
                //현재 위치가 파란색칸을 넘어 다른 row로 이동한 경우
                currColumn = currColumn - map[currRow].length + 1;

                if (currRow % 2 == 0) {
                    //짝수번째 row인 경우 다다음 row로 이동시킨다.
                    currRow += 2;
                    if (currRow >= 8) {
                        currRow = 8;
                        if (currColumn >= 1) {
                            currColumn = 1;
                        }
                    }
                } else if (currRow == 7) {
                    currRow = 8;
                    if (currColumn >= 1) {
                        currColumn = 1;
                    }
                } else {
                    //홀수번째 row는 무조건 7번으로 간다.
                    currRow = 7;
                    if (currColumn >= map[currRow].length) {
                        //row를 두번 뛰어넘어 도착을 했다면
                        currRow = 8;
                        currColumn = 1;
                    } else if (currColumn == map[currRow].length - 1) {
                        //도착 바로 전 칸인 경우
                        currRow = 8;
                        currColumn = 0;
                    }
                }
            }

            if ((currRow == 8 && currColumn == 1) || !check[currRow][currColumn]) {
                //이동을 마친칸이 도착칸이거나 이동을 마치는 칸에 다른 말이 없는 경우 해당 말을 위치시킬 수 있다.
                check[startRow][startColumn] = false;
                check[currRow][currColumn] = true;
                loc[i][0] = currRow;
                loc[i][1] = currColumn;

                recursive(index + 1, loc, score + map[currRow][currColumn], check);
                //재귀호출이 종료될때마다 상태를 원복시킨다.
                check[currRow][currColumn] = false;
                check[startRow][startColumn] = true;
                loc[i][0] = startRow;
                loc[i][1] = startColumn;
            }
        }
    }
}
