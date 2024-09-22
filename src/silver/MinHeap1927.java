package silver;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Queue;

public class MinHeap1927 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static Queue<Integer> heap = new PriorityQueue<>(); //priority queue는 기본적으로 min heap 자료구조로 동작한다.
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
