package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 모든 칸은 하나의 나라를 나타낸다.
 * L명이상 R명이하 인구차이가 나는 인접한 나라들에 대해 국경을 연다.
 * 모든 나라의 인구수의 합 / 칸의 개수 로 인구를 재분배한다.
 * 더 이상 국경을 열지 않아도 될 때까지 반복한다.
 *
 * 출력: 인구이동 걸리는 일수
 */

public class PopulationMove16234 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map;
    static int N, L, R;
    static int[] dy = {0, 1, 0, -1}; //동남서북
    static int[] dx = {1, 0, -1, 0};
    static boolean[][] check;
    static List<Info> nodes;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken()); //최저기준
        R = Integer.parseInt(st.nextToken()); //최고기준

        //1. 인구 수 정보입력
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //차이가 L과 R사이만 나는 인접한 칸들을 구한다.
        //해당 칸들의 count와 해당 칸들의 값의 합을 구한다.
        int day = 0;
        while (true) {
            boolean run = false;
            check = new boolean[N][N];

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (!check[i][j]) { //인구이동은 한 나라(한 칸)에서 하루에 한번만 가능하며 하루에 한번 이동했으면 그날은 인구이동이 불가능하다.
                        int sum = bfs(i, j);
                        //인구 이동이 필요한 칸이 있는 경우 true, 없는 경우 false를 반환한다.
                        if (nodes.size() > 1) {
                            for (int k  = 0; k < nodes.size(); k++) {
                                Info node = nodes.get(k);
                                //국경을 허문 칸들에는 계산된 평균값을 넣어준다.
                                map[node.row][node.column] = sum / nodes.size();
                            }
                            run = true;
                        }
                    }
                }
            }
            //하루에 인구이동이 한번도 일어나지 않은 경우 더 이상 인구이동이 없기 때문에 종료한다.
            if (!run) {
                break;
            }
            //한번이라도 국경이동을 했을 경우 true를 반환하기 때문에
            day += 1;
        }
        System.out.println(day);
    }

    static int bfs(int row, int column) {
        Queue<Info> queue = new LinkedList<>();
        nodes = new ArrayList<>(); //연합인 칸들을 저장할 리스트

        queue.add(new Info(row, column)); //시작위치
        nodes.add(new Info(row, column));
        check[row][column] = true;

        int sum = map[row][column]; //초기값
        while (!queue.isEmpty()) {
            Info curr = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = curr.row + dy[i];
                int nx = curr.column + dx[i];

                //범위를 벗어나거나 이미 선택한적이 있는 경우
                if (nx < 0 || nx >= N || ny < 0 || ny >= N || check[ny][nx]) {
                    continue;
                }
                int diff = Math.abs(map[curr.row][curr.column] - map[ny][nx]);
                if (diff >= L && diff <= R) {
                    //인접한 칸의 차이가 L과 R사이일 경우에만 국경을 해제한다.
                    queue.add(new Info(ny, nx));
                    check[ny][nx] = true;
                    nodes.add(new Info(ny, nx));
                    sum += map[ny][nx];
                }
            }
        }
        return sum;
    }

    static class Info {
        int row, column;
        public Info(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
