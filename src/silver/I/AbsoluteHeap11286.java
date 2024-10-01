package silver.I;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;

public class AbsoluteHeap11286 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static Queue<Integer> heap = new PriorityQueue<>((a, b) -> {
        int absA = Math.abs(a);
        int absB = Math.abs(b);
        //절대값이 같으면 원래 숫자가 더 작은 수가 앞에 온다
        if (absA == absB) {
            return Integer.compare(a, b);
        }
        return Integer.compare(absA, absB);
    }); //커스텀 로직 추가
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(br.readLine());
            int result = 0;
            if (num == 0) {
                Integer value = heap.poll();
                if (value != null) {
                    result = value;
                }
                sb.append(result).append('\n');
            } else {
                heap.add(num);
            }
        }
        bw.write(String.valueOf(sb));
        bw.flush();
    }
}
