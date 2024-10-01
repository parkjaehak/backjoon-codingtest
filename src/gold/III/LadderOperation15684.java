package gold.III;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LadderOperation15684 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] array;
    static int N, M, H;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //세로
        M = Integer.parseInt(st.nextToken()); //사다리 개수
        H = Integer.parseInt(st.nextToken()); //가로

        array = new int[H + 1][N + 1]; //인덱스는 1부터 시작
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(st.nextToken());
            int column = Integer.parseInt(st.nextToken());

            array[row][column] = 1; //시작 위치는 1, 그 맞은 편은 -1
            array[row][column + 1] = -1;
        }
        
        //모든 지점에 가로선을 임의로 설치한다 최대 3개까지(완전탐색)
        //중간에 성공을 했다면 더이상 재귀 x
        for (int i = 0; i <= 3; i++) {
            //i개수 만큼 가로선을 추가한다.
            boolean result = dfs(1, 1, i, i);
            if (result) {
                return;
            }
        }
        //모든 경우의 수에서 결과를 찾지 못했다면 -1을 출력한다.
        System.out.println(-1);
    }

    private static boolean dfs(int row, int column, int result, int count) {
        if (count == 0) {
            //사다리를 모두 선택했을 경우 지금까지 고른 사다리 수를 출력한다.
            if (move()) {
                System.out.println(result);
                return true;
            }
            return false;
        }
        for (int r = row; r <= H; r++) {
            for (int c = column; c < N ; c++) {
                if (array[r][c] == 0 && array[r][c + 1] == 0) {
                    //사다리를 놓을 수 있는 위치
                    array[r][c] = 1;
                    array[r][c + 1] = -1;

                    // 그 다음 사다리 설치는 두칸 건너서 한다.
                    if (dfs(r, c + 2, result, count - 1)) {
                        return true;
                    }
                    array[r][c] = array[r][c + 1] = 0;
                }
            }
            //row가 변경되면 column도 초기화한다.
            column = 1;
        }
        return false;
    }

    private static boolean move() {
        //사다리의 모든위치에서 출발지점과 종료지점이 같은 세로선에 위치하는지 확인한다.
        for (int c = 1; c <= N; c++) {
            int nc = c;
            for (int r = 1; r <= H; r++) {
                if (array[r][nc] == 1) {
                    nc++; //오른쪽을 이동한다.
                } else if (array[r][nc] == -1) {
                    nc--; //왼쪽으로 이동한다.
                }
            }
            if (c != nc) {
                //시작위치와 종료위치가 같지 않은 경우
                return false;
            }
        }
        return true;
    }
}
