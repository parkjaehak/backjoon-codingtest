package silver;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CoordinateCompression18870 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int[] array;
    static int[] sortedArray;
    static Map<Integer, Integer> map = new HashMap<>();
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        array = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int n = 0; n < N; n++) {
            array[n] = Integer.parseInt(st.nextToken());
        }
        sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray); //오름차순정렬
        sortedArray = Arrays.stream(sortedArray).distinct().toArray(); //중복을 제거한다.

        for (int i = 0; i < sortedArray.length; i++) {
            //순서를 정한다.
            map.put(sortedArray[i], i); //key: 실제 값, value: 순서
        }
        for (int i = 0; i < N; i++) {
            int result = map.get(array[i]);
            sb.append(result).append(" ");
        }
        bw.write(sb.toString());
        bw.flush();
    }
}
