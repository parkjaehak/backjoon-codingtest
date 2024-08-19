import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[] parents;

    public static void main(String[] args) throws Exception {

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        parents = new int[n + 1];
        Arrays.fill(parents, -1); // -1로 초기화
        int a = 0, b = 0;
        for (int i = 1; i <= m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            //k번째 간선의 경우 a, b에 할당한다.
            if (i == k) {
                a = u;
                b = v;
                continue;
            }
            union(u, v);
        }

        a = find(a);
        b = find(b);

        //1인 간선을 제거했을때 2개의 그룹이 나누어지지 않으면 0인 간선이 존재한다는 뜻으로 최단거리는 0이다.
        if (a == b) {
            System.out.println(0);
            return;
        }

        int aCnt = 0, bCnt = 0;
        for (int i = 1; i <= n; i++) {
            int cur = find(i);
            if (cur == a)
                aCnt++;
            else if (cur == b)
                bCnt++;
        }
        System.out.println(1l * aCnt * bCnt);
    }
    private static void union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b)
            return;

        int high, low;
        if (parents[a] < parents[b]) {
            high = a;
            low = b;
        } else {
            high = b;
            low = a;
        }

        parents[high] += parents[low];
        parents[low] = high;
    }

    private static int find(int a) {
        if (parents[a] < 0)
            return a;
        return parents[a] = find(parents[a]);
    }
}
