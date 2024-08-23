package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Gerrymandering17471 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static List<List<Integer>> graph = new ArrayList<>();
    static int[] population;
    static boolean[] isGroupA;
    static boolean[] visited;
    static int N;
    static int min = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        //그래프 초기화
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        //각 노드별 인구수
        st = new StringTokenizer(br.readLine());
        population = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            population[i] = Integer.parseInt(st.nextToken()); //각 구역별 인구수
        }

        //노드간 연결정보
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int numOfNeighbors = Integer.parseInt(st.nextToken()); //인접한 구역의수

            for (int j = 0; j < numOfNeighbors; j++) {
                int A = Integer.parseInt(st.nextToken());
                graph.get(A).add(i);
                graph.get(i).add(A);
            }
        }

        //선거구를 선택한다.
        isGroupA = new boolean[N + 1]; // 부분집합 만들 때 사용
        select(1);

        if (min == Integer.MAX_VALUE) {
            //모든 경우에 선거구를 나눌 수 없는 경우
            System.out.println(-1);
        } else {
            System.out.println(min);
        }
    }

    private static void select(int idx) {
        if (idx == N) {
            List<Integer> areaA = new ArrayList<>();
            List<Integer> areaB = new ArrayList<>();

            for (int i = 1; i <= N; i++) {
                if (isGroupA[i]) { //선택한 노드는 A로 그렇지 않은 노드는 B에 할당한다.
                    areaA.add(i);
                } else {
                    areaB.add(i);
                }
            }
            // 선거구는 적어도 하나의 구역을 포함해야한다.
            if (areaA.isEmpty() || areaB.isEmpty()) {
                return;
            }
            //선택한 노드간 연결되어 있는지 확인한다.
            if (check(areaA) && check(areaB)) {
                //인구수의 차이를 구한다.
                calculate();
            }
            return;
        }

        isGroupA[idx] = true; //A구역을 선택하는 경우
        select(idx + 1);

        isGroupA[idx] = false; //B구역을 선택하는 경우
        select(idx + 1);
    }

    private static void calculate() {
        int sumA = 0, sumB = 0;
        for (int i = 1; i <= N; i++) {
            if (isGroupA[i]) {
                sumA += population[i];
            } else {
                sumB += population[i];
            }
        }
        min = Math.min(min, Math.abs(sumA - sumB));
    }

    static boolean check(List<Integer> list) {
        Queue<Integer> queue = new LinkedList<>();
        visited = new boolean[N + 1];
        int currNode = list.get(0);

        queue.add(currNode);
        visited[currNode] = true;

        int count = 1;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            List<Integer> neighbors = graph.get(curr); //현재노드의 이웃노드를 가져온다.
            for (int i = 0; i < neighbors.size(); i++) {
                int currNeighbor = neighbors.get(i);
                if (list.contains(currNeighbor) && !visited[currNeighbor]) {
                    //같은 선거구에 있는 노드가 이웃노드인지 확인한다.
                    queue.add(currNeighbor);
                    visited[currNeighbor] = true;
                    count++;
                }
            }
        }
        if (list.size() == count) {
            return true;
        } else {
            return false;
        }
    }
}
