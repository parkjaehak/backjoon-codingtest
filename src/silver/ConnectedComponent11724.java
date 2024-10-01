package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ConnectedComponent11724 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static List<List<Integer>> graph = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //노드
        M = Integer.parseInt(st.nextToken()); //링크

        for (int n = 0; n <= N; n++) {
            //이웃들을 담을 객체
            graph.add(new ArrayList<>()); //노드의 수만큼 담을 객체 생성한다.
        }

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        //무방향그래프에서 연결요소의 개수 = 정점들의 그룹의 개수
        findConnectedComponent();
    }

    private static void findConnectedComponent() {
        //bfs
        Queue<Integer> queue = new LinkedList<>();
        boolean[] check = new boolean[N + 1];
        int connectedComponents = 0; // 연결 요소의 수를 저장할 변수

        // 모든 정점에 대해 연결 요소를 확인 --> 연결요소를 확인하기 위해서 모든 정점을 확인해야한다.
        for (int i = 1; i <= N; i++) {
            if (!check[i]) {  // 아직 방문하지 않은 정점이 있으면 새로운 연결 요소 발견
                connectedComponents++; // 새로운 연결 요소를 발견할 때마다 증가
                queue.add(i);
                check[i] = true;

                while (!queue.isEmpty()) {
                    Integer curr = queue.poll();
                    List<Integer> neighbors = graph.get(curr); // 정점의 이웃 노드들


                    for (int neighbor : neighbors) {
                        if (!check[neighbor]) {
                            queue.add(neighbor);
                            check[neighbor] = true;
                        }
                    }

                }
            }
        }
        System.out.println(connectedComponents);
    }
}
