package silver.IIII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ATM11399 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] array;
    static int[] sum;
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        array = new int[N + 1];
        sum = new int[N + 1];

        //모든 사람의 인출시간을 저장한다.
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }

        //인출시간이 적게 걸리는 사람을 앞으로 보낸다.(오름차순정렬)
        Arrays.sort(array);

        int total = 0;
        for (int i = 1; i <= N; i++){
            for (int j = 1; j <= i; j++){
                total += array[j];
            }
        }
        System.out.println(total);
    }
}
