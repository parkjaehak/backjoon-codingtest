package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DFSBFS1260 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static List<List<Integer>> graph = new ArrayList<>();
    static int N, M, V; //정점 개수, 간선 개수, 탐색 시작 정점 번호
    static StringBuilder outputBFS = new StringBuilder(); // 출력 문자열을 누적할 StringBuilder


    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());

        //1. 정점의 수만큼 ArrayList 생성 후 초기화
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        //2. M번 반복하여 양뱡향 간선 연결
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            graph.get(A).add(B); //입력값 A가 가지는 ArrayList에 연결된 이웃노드들을 저장한다.
            graph.get(B).add(A);
        }
        //3. 탐색 시작번호부터 dfs 탐색을 진행하여 탐색 순서를 출력한다.
        boolean[] check = new boolean[N + 1];
        StringBuilder outputDFS = new StringBuilder();
        dfs(V, check, outputDFS);
        System.out.println(outputDFS.toString().trim());

        //4. 탐색 시작번호부터 bfs 탐색을 진행하여 탐색 순서를 출력한다.
        bfs(V); //탐색을 시작할 번호가 주어진다.
    }

    public static void dfs(int startNode, boolean[] check, StringBuilder outputDFS) {
        //현재 노드를 방문한다.
        check[startNode] = true;
        outputDFS.append(startNode).append(" ");

        //현재 노드의 이웃노드들을 가져온 후 오름차순 정렬한다.
        List<Integer> neighbors = graph.get(startNode);
        Collections.sort(neighbors);

        //정렬된 이웃노드들에 대해 dfs를 재귀적으로 호출한다.
        for (int i = 0; i < neighbors.size(); i++) {
            int neighborNode = neighbors.get(i);
            if (check[neighborNode] == false) {
                dfs(neighborNode, check, outputDFS); //이웃노드와 체크배열, 출력버퍼를 넘겨준다.
            }
        }
        //더 이상 선택할 노드가 없으면 dfs 탐색을 재귀적으로 종료한다.
    }

    public static void bfs(int startNode) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] check = new boolean[N + 1];

        queue.add(startNode); //최초 큐에 시작 노드 정보를 저장한다.
        check[startNode] = true;

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            outputBFS.append(currentNode).append(" "); // 현재 노드를 출력 문자열에 추가

            List<Integer> neighbors = graph.get(currentNode); //현재 노드와 연결된 이웃노드들의 리스트를 가져온다.
            Collections.sort(neighbors); //오른차순 정렬한다.

            // 실제로 bfs를 수행하는 부분 : depth별로 단계를 두어 가장 가까운 노드들을 반복적으로 방문하여 방문한적 없는 경우만 큐에 넣는다.
            for (int i = 0; i < neighbors.size(); i++) {
                int neighborNode = neighbors.get(i); //이웃노드의 번호

                //이웃노드를 거쳐간 적이 없는 경우만 큐에 저장한다.
                if (check[neighborNode] == false) {
                    queue.add(neighborNode);
                    check[neighborNode] = true;
                }
            }
        }
        System.out.println(outputBFS.toString().trim()); // 결과를 출력하고, 문자열 끝의 공백을 제거
    }
}
