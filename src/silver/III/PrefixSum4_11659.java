package silver.III;

import java.io.*;
import java.util.StringTokenizer;

public class PrefixSum4_11659 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int N, M;
    static int [] array;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        array = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            //누적합을 구한다.
            array[i] = array[i - 1] + Integer.parseInt(st.nextToken());
        }

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            int sum = array[end] - array[start - 1]; //end까지의 누적합에서 start - 1까지의 누적합을 뺸다.
            sb.append(sum).append('\n');
        }
        bw.write(sb.toString());
        bw.flush();
    }
}
