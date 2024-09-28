package silver;

import java.io.*;
import java.util.StringTokenizer;

public class ColoredPaper2630 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] array;
    static int N;
    static int whiteCount = 0, blueCount = 0;

    public static void main(String[] args) throws IOException {
        //잘라진 하얀색 파란색  종이의 개수를 출력한다.
        N = Integer.parseInt(br.readLine());

        array = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        divide(N, 0, 0);
        System.out.println(whiteCount + "\n" + blueCount);
    }

    private static void divide(int size, int row, int column) {
        int color = array[row][column]; // 시작 색
        boolean isSameColor = true;

        // 해당 영역이 모두 같은 색인지 확인
        for (int i = row; i < row + size; i++) {
            for (int j = column; j < column + size; j++) {
                if (array[i][j] != color) {
                    isSameColor = false;
                    break; // 내부 루프 탈출
                }
            }
            if (!isSameColor) break; // 외부 루프 탈출
        }

        // 해당 공간이 모두 같은 색이면
        if (isSameColor) {
            if (color == 0) {
                whiteCount++; // 하얀색
            } else {
                blueCount++;  // 파란색
            }
            return; // 분할 종료
        }

        // 그렇지 않으면 4개의 영역으로 나눔
        int nextSize = size / 2;
        divide(nextSize, row, column);               // 1구역
        divide(nextSize, row, column + nextSize);    // 2구역
        divide(nextSize, row + nextSize, column);    // 3구역
        divide(nextSize, row + nextSize, column + nextSize);  // 4구역
    }
}
