package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Virus2606 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static List<List<Integer>> graph = new ArrayList<>();
    static int N, M; //N: 컴퓨터의 수,  M: 연결정보의 수
    static int sum ;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        //1. 연결정보 저장을 위한 N개의 List 객체 초기화
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        //2. 연결정보 저장
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            graph.get(A).add(B);
            graph.get(B).add(A);
        }

        //3. bfs탐색
        bfs(1);
        System.out.println(sum);


        //5. dfs탐색
        boolean[] check = new boolean[N + 1];
        dfs(1, check); //배열의 주소를 넘겨준다.
        System.out.println(sum);


    }

    static void dfs(int startNode, boolean[] check) {

        check[startNode] = true; //배열자체는 참조값이기 때문에 변경된 값은 계속 유지된다.
        List<Integer> neighbors = graph.get(startNode);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            if (check[neighbor] == false) {
                dfs(neighbor, check);
                sum += 1;
            }
        }
    }

    static void bfs(int startNode) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] check = new boolean[N + 1];

        //최초 노드 저장
        queue.add(startNode);
        check[startNode] = true;

        while (!queue.isEmpty()) {
            int currNode = queue.poll();
            List<Integer> neighbors = graph.get(currNode); //현재 노드가 가지고 있는 이웃노드들을 모두 가져온다.

            for (int n = 0; n < neighbors.size(); n++) {
                int neighborNode = neighbors.get(n);

                if (check[neighborNode] == false) {
                    queue.add(neighborNode);
                    check[neighborNode] = true;
                    sum += 1;
                }
            }
        }
    }
}
