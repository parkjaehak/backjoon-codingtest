package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class HideAndSeek1697 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, K;
    static boolean[] visited = new boolean[100001];
    static Queue<Info> queue = new LinkedList<>(); //수빈이의 현재 위치와 시간을 저장한다.

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //수빈 위치
        K = Integer.parseInt(st.nextToken()); //동생 위치

        int result = bfs();
        System.out.println(result);
    }

    private static int bfs() {
        queue.add(new Info(N, 0));
        visited[N] = true; //수빈이의 방문 여부

        while (!queue.isEmpty()) {
            Info curr = queue.poll();
            int position = curr.position; //현재 위치
            int time = curr.time; //현재까지 걸린 시간

            if (position == K) {
                return time; //해당 시간을 반환한다.
            }

            //수빈이가 갈 수 있는 위치
            int[] nextPositions = {position - 1, position + 1, position * 2};

            for (int nextPosition : nextPositions) {
                if (nextPosition < 0 || nextPosition >= 100001 || visited[nextPosition]) {
                    continue;
                }
                visited[nextPosition] = true; //방문처리를 진행한다.
                queue.add(new Info(nextPosition, time + 1)); //큐에 다음위치를 저장한다.
            }
        }
        return -1; //수빈이가 동생에게 도달할 수 있는 것은 문제의 이동 조건(걷기와 순간이동)과 BFS 알고리즘이 모든 경로를 탐색하는 특성 덕분에 언제나 보장된다.
    }

    static class Info {
        int position, time;

        public Info(int position, int time) {
            this.position = position;
            this.time = time;
        }
    }
}
