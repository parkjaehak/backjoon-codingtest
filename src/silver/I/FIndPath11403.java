package silver.I;

import java.io.*;
import java.util.StringTokenizer;

public class FIndPath11403 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        // 인접 행렬 입력 받기
        int[][] graph = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                graph[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        // 플로이드-워셜 알고리즘 적용
        for (int k = 0; k < N; k++) {  // 중간에 거쳐가는 노드
            for (int i = 0; i < N; i++) {  // 시작 노드
                for (int j = 0; j < N; j++) {  // 끝 노드
                    if (graph[i][k] == 1 && graph[k][j] == 1) {
                        graph[i][j] = 1;  // i에서 j로 가는 경로가 있으면 1로 설정
                    }
                }
            }
        }

        // 결과 출력
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

    }
}
