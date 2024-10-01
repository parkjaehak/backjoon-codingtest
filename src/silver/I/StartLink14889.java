package silver.I;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. N을 2로 나눈다.
 * 2. 앞에서부터 순서대로 팀을 구성하여 check 표시한다.
 * 3. 팀원이 모두 할당되었을 경우 check 인덱스를 이용해 자신을 제외한 값의 합을 구한다.
 * 4. 두 팀의 능력치 차이가 적은 최솟값을 출력한다.
 */
public class StartLink14889 {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] board;
    static boolean[] check;
    static int N;

    static int min = Integer.MAX_VALUE;

    public StartLink14889 ()throws IOException {
        N = Integer.parseInt(bf.readLine());

        // 보드에 스코어 입력
        board = new int[N][N];
        check = new boolean[N];
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(bf.readLine());
            for (int m = 0; m < N; m++) {
                board[n][m] = Integer.parseInt(st.nextToken());
            }
        }
        int teamNumber = N / 2;
        recursive(0, teamNumber); //시작할 배열 인덱스, 팀원 수

        System.out.println(min);
    }

    static void recursive(int index, int validTeamNumber) {

        // 현재 선택가능한 팀원 수
        if (validTeamNumber == 0) {
            min = Math.min(min, getDifference());//현재 최솟값, 현재 선택한 팀원의 능력치의 합의 차이의 최솟값을 비교한다.
            return;
        }

        // 시작한 인덱스부터 선택
        for (int i = index; i < N; i++) {
            check[i] = true;
            recursive(i + 1, validTeamNumber - 1); // i + 1 사용해야한다.
            check[i] = false;
        }
    }

    static int getDifference() {
        int start = 0;
        int link = 0;

        // 각 팀의 능력치 합
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) {
                    continue;
                }
                // 스타트팀
                if (check[i] == true && check[j] == true) {
                    start += board[i][j];

                // 링크팀
                } else if (check[i] == false && check[j] == false) {
                    link += board[i][j];
                }
            }
        }
        return Math.abs(start-link);
    }
}
