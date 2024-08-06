package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 상담가능한 일 수 : N
 * 상담하는데 걸리는 일 수 : T
 * 상담 가격 : P
 *
 * 최대 수익을 구하자
 *
 *
 * 규칙
 *  --> 현재 날짜 + T 일째에 상담을 잡을 수 있다.
 *  --> 남은 일짜 < 상담기간, 해당 날짜에 시작하는 상담은 할 수 없다.
 */
public class Retire14501 {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static int[][] table;
    static int max = Integer.MIN_VALUE;

    public Retire14501() throws IOException {

        N = Integer.parseInt(bf.readLine()); //남은 상담 가능일 수
        table = new int[2][N + 1]; //[0][] : 상담기간, [1][] : 상담비용

        //1. 테이블에 데이터를 삽입한다.
        for (int n = 1; n <= N; n++) { //1일 - N일
            st = new StringTokenizer(bf.readLine());
            for (int r = 0; r < 2; r++) {
                table[r][n] = Integer.parseInt(st.nextToken());
            }
        }
        consult(1, 0); //상담가능한 날짜,
        System.out.println(max);
    }

    public static void consult(int today, int income) {
        max = Math.max(max, income);

        if (today == N + 1) {
            return;
        }
        //2. 오늘의 상담기간과 상담비용을 가져온다.
        int T = table[0][today]; //상담기간
        int P = table[1][today]; //상담비용

        //3. 오늘 상담을 할 수 있으면 한다.
        // --> 오늘 상담을 할 수 있으면 우선하고 해당 경우의 수가 종료되면 오늘 상담을 하지 않는 케이스로 이동
        if (today + T <= N + 1) {
            consult(today + T, income + P);
        }
        //4.오늘 상담을 안한다.
        // --> 상담기간이 길어서 오늘 상담을 하지 못하거나 이미 오늘 상담을 진행한 경우 내일 상담으로 넘어간다.
        consult(today + 1, income);
    }
}
