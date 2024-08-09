package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * CCTV들을 리스트에 저장하여 dfs를 통해 모든 경우의 수에서 사각지대가 최소가 되는 경우를 구한다.
 */
public class Watch15683 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] office;
    static int N, M;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};
    static int whiteArea; //사각지대의 수
    static int min = Integer.MAX_VALUE;
    static List<int[]> cctvs = new ArrayList<>();

    //각 cctv가 바라볼 수 있는 방향 (북{0}, 동{1}, 남{2}, 서{3})
    static int[][][] cctvDirs = {
            {},
            {{0}, {1}, {2}, {3}}, //1번 cctv
            {{0, 2}, {1, 3}}, //2번 cctv
            {{0, 1}, {1, 2}, {2, 3}, {3, 0}}, //3번 cctv
            {{0, 1, 2}, {1, 2, 3}, {2, 3, 0}, {3, 0, 1}}, //4번 cctv
            {{0, 1, 2, 3}} //5번 cctv
    };


    public Watch15683() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        office = new int[N][M];
        for (int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for (int m = 0; m < M; m++) {
                office[n][m] = Integer.parseInt(st.nextToken()); //사무실의 벽, CCTV 정보 세팅

                if (office[n][m] >= 1 && office[n][m] <= 5) {
                    int type = office[n][m]; //cctv 타입
                    cctvs.add(new int[]{n, m, type}); //cctv가 위치하는 row, column, cctv의 타입을 결정한다.
                } else if (office[n][m] == 0) {
                    whiteArea++;
                }
            }
        }
        dfs(0, 0);

        System.out.println(min);
    }

    public static void dfs(int cctvIdx, int totalScaned) {

        if (cctvIdx == cctvs.size()) {
            //모든 cctv에 대한 탐색이 끝났다면
            min = Math.min(min, whiteArea - totalScaned); //모든 사각지대에서 cctv가 감시가능한 곳의 수를 뺀다.
            return;
        }
        //1. cctv를 가져온 후, cctv에 맞는 회전규칙을 통해 회전 시켜본다.
        int[] cctv = cctvs.get(cctvIdx);
        int cctvType = cctv[2];

        for (int d = 0; d < cctvDirs[cctvType].length; d++) {
            int[] dirs = cctvDirs[cctvType][d];

            //2. 해당 방향으로 감시구역 찾아간다.
            int scaned = scan(cctv, dirs, -1);//탐색하고자 하는 cctv, cctv가 회전가능한 방향정보, flag

            //3. 하나의 cctv를 scan했을 경우 다음 cctv를 호출한다.
            dfs(cctvIdx + 1, totalScaned + scaned);

            //4. 표시한 감시구역을 다시 되돌려 놓는다.
            scan(cctv, dirs, 1);
        }
    }

    private static int scan(int[] cctv, int[] dirs, int flag) {
        int count = 0; //특정 cctv와 특정 방향으로의 감시가능 구역의 수
        int row = cctv[0];
        int column = cctv[1];

        for (int d = 0; d < dirs.length; d++) {
            for (int i = 1; ; i++) {
                int nextR = row + dy[dirs[d]] * i; //한방향으로 쭉 파악할 수 있도록 i 곱해야 한다.
                int nextC = column + dx[dirs[d]] * i;

                if (!(nextR >= 0 && nextR < N && nextC >= 0 && nextC < M) || office[nextR][nextC] == 6) {
                    // 범위안에 있지 않거나 다음 칸이 벽일 경우 더 이상 감시할 수 없다.
                    break;
                }
                if (office[nextR][nextC]>=1 && office[nextR][nextC]<= 5) {
                    // 다른 cctv가 감시경로 상에 존재한다면 처리하지 않고 건너뛴다.
                    continue;
                }
                if (office[nextR][nextC] == 0) {
                    // 감시가능한 곳이 아직 감시되지 않은 공간인 경우에는 감시한다.
                    count++;
                }
                office[nextR][nextC] += flag; //감시한 곳을 지정할때는 7을 할당하고 해제할 때는 다시 0을 할당한다.
            }
        }
        return count;
    }
}
