package gold;

import java.util.*;
import java.io.*;

public class DifficultShortcut23324 {

    static int N;
    static int[] parents;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        parents = new int[N + 1];

        for (int i = 1; i < N + 1; i++) {
            parents[i] = i;
        }

        int M = Integer.parseInt(st.nextToken());
        int one = Integer.parseInt(st.nextToken());
        int groupA = 0;
        int groupB = 0;

        for (int i = 1; i < M + 1; i++) {
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (i == one) {
                groupA = a;
                groupB = b;
                continue;
            }
            union(a, b);
        }
        groupA = find(groupA);
        groupB = find(groupB);

        if (groupA == groupB) {
            System.out.println(0);
        } else {
            long sumA = 0;
            long sumB = 0;
            for (int i = 1; i < N + 1; i++) {
                if (find(i) == groupA) {
                    sumA++;
                } else {
                    sumB++;
                }
            }
            long ans = sumA * sumB;
            System.out.println(ans);
        }
    }

    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA < rootB) {
            parents[rootB] = rootA;
        } else {
            parents[rootA] = rootB;
        }
    }

    static int find(int num){
        if (parents[num] == num) {
            return num;
        }
        return parents[num] = find(parents[num]);
    }
}
