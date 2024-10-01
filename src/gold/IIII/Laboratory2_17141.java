package gold.IIII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Laboratory2_17141 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] array;
    static int N, M;
    static List<Info> viruses = new ArrayList<>();
    static int[] activeVirus;
    static boolean[][] check;
    static int min = Integer.MAX_VALUE;
    static int uninfected = 0;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        array = new int[N][N];
        activeVirus = new int[M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
                if (array[i][j] == 2) {
                    //바이러스가 존재가능한 구역을 리스트에 담아둔다.
                    viruses.add(new Info(i, j, 0));
                } else if (array[i][j] == 0) {
                    uninfected += 1;
                }
            }
        }

        uninfected += viruses.size() - M;

        dfs(0, 0); //조합을 통해 바이러스 존재가능 구역 중 M개의 바이러스 위치를 선택한다.

        if (min == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(min);
        }
    }

    static void dfs(int index, int count) {

        if (count == M) {
            int time = bfs();
            if (time != -1) {
                min = Math.min(min, time);
            }
            return;
        }

        for (int i = index; i < viruses.size(); i++) {
            activeVirus[count] = i; //가능한 바이러스 리스트 중 하나를 뽑아 count인덱스에 위치시킨다.
            dfs(i + 1, count + 1);
        }

    }

    static int bfs() {
        Queue<Info> queue = new LinkedList<>();
        check = new boolean[N][N]; //매 bfs마다 새롭게 객체를 생성해주어야한다.
        for (int m = 0; m < M; m++) {
            //활성화 시킬 바이러스를 큐에 저장한다.
            int currActiveVirus = activeVirus[m];
            Info info = viruses.get(currActiveVirus);
            queue.add(info);
            check[info.row][info.column] = true; //바이러스 활성화를 표시한다.
        }

        int time = 0;
        int infected = 0;
        while (!queue.isEmpty()) {
            Info info = queue.poll();
            int currRow = info.row;
            int currColumn = info.column;
            int currTime = info.time;

            time = currTime;

            for (int i = 0; i < 4; i++) {
                int nextRow = currRow + dy[i];
                int nextColumn = currColumn + dx[i];

                if (nextRow < 0 || nextRow >= N || nextColumn < 0 || nextColumn >= N || check[nextRow][nextColumn] == true) {
                    //범위를 벗어나거나 이미 바이러스를 퍼트린 곳이면 패스한다.
                    continue;
                }

                if (array[nextRow][nextColumn] != 1) {
                    check[nextRow][nextColumn] = true;
                    infected += 1;
                    queue.add(new Info(nextRow, nextColumn, currTime + 1));
                }
            }
        }

        if (infected == uninfected) {
            return time;
        } else {
            return -1;
        }
    }

    static class Info {
        int row, column, time;

        public Info(int row, int column, int time) {
            this.row = row;
            this.column = column;
            this.time = time;
        }
    }
}
