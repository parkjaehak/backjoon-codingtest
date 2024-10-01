package gold.IIIII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Z모양 순서로 탐색하여 방문순서를 구한다.
 * <p>
 * 2^N 의 배열을 생성하여 N>1인 경우  2^N-1로 4등분하여 재귀적으로 처리한다.
 * <p>
 * r, c 에 위치한 칸이 몇번째로 방문하는지 출력한다.
 */
public class Z1074 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, r, c;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        int length = (int) Math.pow(2, N); //한 변의 길이


        //1. array에 방문하는 순서대로 숫자를 입력한다.
        recursive(r, c, length);
        System.out.println(count);
    }


    public static void recursive(int row, int column, int length) {

        if (length == 1) {
            return;
        } else if (row < length / 2 && column < length / 2) {
            //1사분면에 위치하는 경우
            recursive(row, column, length / 2);
        } else if (row < length / 2 && column >= length / 2) {
            //2사분면에 위치하는 경우 - 1사분면까지 방문한 횟수를 더해주고 찾아야하는 좌표의 인덱스를 갱신한다.
            count += length * length / 4;
            recursive(row, column - length / 2, length / 2);
        } else if (row >= length / 2 && column < length / 2) {
            //3사분면에 위치하는 경우 - 2사분면까지 방문한 횟수를 더해준다.
            count += length * length / 4 * 2;
            recursive(row - length / 2, column, length / 2);
        } else if (row >= length / 2 && column >= length / 2) {
            //4사분면에 위치하는 경우 - 3사분면까지 방문한 횟수를 더해준다.
            count += length * length / 4 * 3;
            recursive(row - length / 2, column - length / 2, length / 2);
        }
    }
}
