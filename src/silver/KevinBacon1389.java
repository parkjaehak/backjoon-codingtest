package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class KevinBacon1389 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static List<List<Integer>> graph = new ArrayList<>();
    static int min = Integer.MAX_VALUE;

    static int minUserIdx;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // N개의 graph를 생성 후 초기화한다.
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>(i)); //생성되는 인덱스 중 1부터 N까지만 사용한다.
        }
        //M개의 친구관계에 대해 그래프로 나타낸다.
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            graph.get(A).add(B);
            graph.get(B).add(A); //친구관계를 등록한다.
        }

        // N명의 유저 각각에 대해 케빈 베이컨 수를 찾고 그 최솟값 및 최소인 유저를 구한다.
        for (int i = 1; i <= N; i++) {
            //1번 부터 시작
            find(i);
        }
        System.out.println(minUserIdx);
    }

    //BFS는 레벨 단위로 노드를 탐색하므로 각 노드까지의 최단 경로를 자연스럽게 계산할 수 있다.
    static void find(int userIdx) {
        Queue<Info> queue = new LinkedList<>();
        boolean[] check = new boolean[N + 1];
        //user의 인덱스와 특정 유저가 거쳐간 거리의 수인 (다리 건너기)sum이라는 정보가 필요하다.

        Info info = new Info(userIdx, 0); //처음 시작할때 0부터 시작
        queue.add(info);
        check[userIdx] = true; //내가 선택되었음을 표시
        int sum = 0; //내가 어떤 친구와 연결되기까지의 단계 수(간선 수)

        while (!queue.isEmpty()) {
            Info currentUserIdx = queue.poll();
            List<Integer> friends = graph.get(currentUserIdx.index); //현재 유저를 꺼내고 해당 유저의 친구들을 꺼낸다.

            for (int i = 0; i < friends.size(); i++) {
                int friendIdx = friends.get(i);
                if (!check[friendIdx]) { //친구를 선택한적이 없는 경우
                    check[friendIdx] = true; //그 친구를 선택하여 연결한다.
                    sum += currentUserIdx.count + 1;
                    Info newInfo = new Info(friendIdx, currentUserIdx.count + 1); //그 친구를 기준으로 더 이상 친구가 연결할 친구가 없을때까지 반복한다.
                    queue.add(newInfo);
                }
            }
        }
        if (sum < min) {
            min = sum;
            minUserIdx = userIdx;
        }
    }

    public static class Info {
        int index, count;
        public Info(int index, int count) {
            this.index = index;
            this.count = count;
        }
    }
}