package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * N: 지도의 크기
 * L: L개의 연속 칸에 경사로 바닥이 접해야함
 * <p>
 * 한칸만 차이나고 더 낮은 높이의 칸이 L개 존재해야 경사로를 설치가능
 * 한 줄에 존재하는 모든 칸이 이어져 있어야 이동할 수 있는 길임.
 * <p>
 * 지나갈 수 있는 길의 개수 구하기
 * <p>
 * 1. 높이 차이가 1이하로 차이나야한다.
 * 1-1. 현재 땅보다 다음 땅의 높이가 더 큰 경우
 * 경사면의 유무 확인
 * 낮은 높이의 칸의 개수가 L개 있어야 함을 확인
 * 1-2. 현재 땅보다 다음 땅의 높이가 낮은 경우
 * 낮은 높이의 칸의 개수가 L개 있어야 함을 확인
 * <p>
 * dfs를 사용해야 할 것 같다.
 */
public class Ramp14890 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map;

    static int N, L;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        //높이 정보 저장완료
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int count = 0;
        for (int n = 0; n < N; n++) {
            if (calRow(n)) { //n번째 행을 확인한다.
                count += 1;
            }
            if (calCol(n)) { //n번째 열을 확인한다.
                count += 1;
            }
        }
        //지나갈 수 있는 길의 수를 계산한다.
        System.out.println(count);
    }

    private static boolean calRow(int row) {
        boolean[] slider = new boolean[N]; //하나의 열 내에서 경사면을 얼만큼 설치하였는지 배열을 통해 확인한다.

        for (int i = 0; i < N - 1; i++) {
            int diff = map[row][i] - map[row][i + 1]; //현재 행과 다음 행의 높이 차

            if (diff > 1 || diff < -1) {
                return false; //경사면 높이차가 1을 넘어버리면 설치할 수 없다.
            } else if (diff == 1) {
                //내려가는 경사로를 설치가능한지 확인한다.
                for (int j = 1; j <= L; j++) {
                    if (i + j >= N || slider[i + j]) { //범위를 넘어가거나 이미 경사로가 설치되었을 경우 더 이상 설치가 불가능하다.
                        return false;
                    }
                    if (map[row][i] - 1 != map[row][i + j]) { //L개만큼의 칸에서 모두 높이가 한칸 낮아야 설치가 가능하다.
                        return false;
                    }
                    slider[i + j] = true;
                }
            } else if (diff == -1) {
                //올라가는 경사로를 설치가능한지 확인한다.
                for (int j = 0; j < L; j++) {
                    if (i - j < 0 || slider[i - j]) { //경사로는 현재 자신의 위치부터 L-1개만큼 뒤로 설치가 가능해야한다.
                        return false;
                    }
                    if (map[row][i] != map[row][i - j]) { //현재 내위치와 내위치포함 L-1개의 높이는 같아야 한다.
                        return false;
                    }
                    slider[i - j] = true;
                }
            }
        }
        return true; //높이가 같은 경우 경사로 설치할 필요는 없다.
    }

    private static boolean calCol(int column) {
        boolean[] slider = new boolean[N];

        for (int i = 0; i < N - 1; i++) {
            int diff = map[i][column] - map[i + 1][column];

            if (diff < -1 || diff > 1) {
                return false;
            } else if (diff == 1) { //내리막길
                for (int j = 1; j <= L; j++) {
                    if (i + j >= N || slider[i + j]) {
                        return false;
                    }
                    if (map[i][column] - 1 != map[i + j][column]) {
                        return false;
                    }
                    slider[i + j] = true;
                }
            }else if (diff == -1) { //오르막길
                for (int j = 0; j < L; j++) {
                    if (i - j < 0 || slider[i - j]) {
                        return false;
                    }
                    if (map[i][column] != map[i - j][column]) {
                        return false;
                    }
                    slider[i - j] = true;
                }
            }
        }
        return true;
    }
}
