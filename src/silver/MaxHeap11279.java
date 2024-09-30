package silver;

import java.io.*;
import java.util.Collections;
import java.util.PriorityQueue;

public class MaxHeap11279 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); //max heap
    
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(br.readLine());
            int result = 0;
            if (num == 0) {
                Integer value = maxHeap.poll();
                if (value != null) {
                    result = value;
                }
                sb.append(result).append('\n');
            } else {
                maxHeap.add(num);
            }
        }
        bw.write(String.valueOf(sb));
        bw.flush();
    }
}
