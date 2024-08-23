package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 인구가 가장 많은 선거구와 가장 적은 선거구의 인구 차이의 최솟값
 * r,c --> x,y
 */

public class Gerrymandering2_17779 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static int[][] map;
    static int total = 0;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken()); //인구수 입력
                total += map[i][j];
            }
        }

        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                for (int d1 = 1; d1 < N; d1++) {
                    for (int d2 = 1; d2 < N; d2++) {
                        //경계의 조건을 확인한다.
                        if (r + d1 + d2 < N && c - d1 >= 0 && c + d2 < N) {
                            setLine(r, c, d1, d2);
                        }
                    }
                }
            }
        }
        System.out.println(min);
    }

    //d1, d2 ≥ 1, 1 ≤ x < x+d1+d2 ≤ N, 1 ≤ y-d1 < y < y+d2 ≤ N
    private static void setLine(int row, int column, int d1, int d2) {
        boolean[][] check = new boolean[N][N];

        check[row][column] = true;
        //1. 경계를 나눈다.
        for (int i = 1; i <= d1; i++) {
            check[row + i][column - i] = true;
            check[row + d2 + i][column + d2 - i] = true;
        }
        for (int i = 1; i <= d2; i++) {
            check[row + i][column + i] = true;
            check[row + d1 + i][column - d1 + i] = true;
        }

        //2. 각 구역별 인구수의 합을 구한다.
        int[] sum = new int[5];

        //1구역
        for (int r = 0; r < row + d1; r++) {
            for (int c = 0; c <= column; c++) {
                //경계를 만나면 멈춘다.
                if (check[r][c]) {
                    break;
                }
                sum[0] += map[r][c];
            }
        }

        //2구역
        for (int r = 0; r <= row + d2; r++) {
            for (int c = N - 1; c > column; c--) {
                if (check[r][c]) {
                    break;
                }
                sum[1] += map[r][c];
            }
        }

        //3구역
        for (int r = row + d1; r < N; r++) {
            for (int c = 0; c < column - d1 + d2; c++) {
                if (check[r][c]) {
                    break;
                }
                sum[2] += map[r][c];
            }
        }

        //4구역
        for (int r = row + d2 + 1; r < N; r++) {
            for (int c = N - 1; c >= column - d1 + d2; c--) {
                if (check[r][c]) {
                    break;
                }
                sum[3] += map[r][c];
            }
        }

        //5구역: 전체 - (1~4구역의 합)
        sum[4] = total - (sum[0] + sum[1] + sum[2] + sum[3]);

        //3. 최소와 최대 선거구를 찾고 그 차이를 구하여 min을 갱신한다.
        Arrays.sort(sum);
        min = Math.min(min, sum[4] - sum[0]);
    }
}
