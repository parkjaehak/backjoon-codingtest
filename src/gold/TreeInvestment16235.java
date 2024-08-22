package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * K년까지 반복 후 살아있는 나무 개수
 * <p>
 * 봄: 자신의 나이만큼 양분 흡수하는데 나이는 1 증가, 여러개가 있다면 나이가 어린 나무부터 흡수, 양분 부족하다면 죽음(나이기록)
 * 여름: 죽은 나무의 나이 /2 -> 양분으로 추가된다.
 * 가을: 나무의 나이가 5년마다 주위 8칸에 나이 1인 나무 생성
 * 겨울: 모든 칸을 돌아다니며 양분을 추가한다. A[r][c] 만큼
 */
public class TreeInvestment16235 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] winter;
    static int N, M, K;
    static int[][] ground;
    static List<Info> list = new ArrayList<>();
    static Queue<Integer> deadList = new LinkedList<>();

    //이문제에서는 x가 row, y는 column이다.
    static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1}; //동, 동남, 남, 남서, 서, 북서, 북, 북동
    static int[] dy = {1, 1, 0, -1, -1, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken()); //최초 나무 위치, 나이
        K = Integer.parseInt(st.nextToken()); //년

        //최초 모든 칸의 양분은 5
        ground = new int[N][N];
        winter = new int[N][N];
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                ground[r][c] = 5; //최초 양분은 5
                winter[r][c] = Integer.parseInt(st.nextToken()); //겨울에 각 땅의 추가할 양분의 양
            }
        }

        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int age = Integer.parseInt(st.nextToken());
            list.add(new Info(x - 1, y - 1, age, true)); //인덱스는 0부터 시작한다.
        }

        list.sort(Comparator.comparingInt(o -> o.age));

        //K 년동안 반복한다.
        for (int k = 0; k < K; k++) {
            spring();
            summer();
            fall();
            winter();
        }
        System.out.println(list.size());
    }

    private static void winter() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                ground[i][j] += winter[i][j];
            }
        }
    }

    private static void fall() {
        List<Info> newTrees = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Info curr = list.get(i);

            //죽어있는 나무일 경우 패스한다.
            if (!curr.isAlive) {
                continue;
            }
            if (curr.age % 5 == 0) {
                //나이가 5의 배수인 경우에만 번식한다.
                for (int j = 0; j < 8; j++) {
                    int nx = curr.row + dx[j];
                    int ny = curr.column + dy[j];
                    if (nx >= 0 && nx < N && ny >= 0 && ny < N) {
                        newTrees.add(new Info(nx, ny, 1, true)); // 나이가 1인 나무를 추가한다.
                    }
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Info curr = list.get(i);
            if (curr.isAlive) {
                newTrees.add(curr);
            }
        }
        list = newTrees; //기존 나무리스트를 새로운 나무리스트로 갱신한다.(추가된 1인 나무 + 기존 생존한 나무)
    }

    private static void summer() {
        while (!deadList.isEmpty()) {
            Info curr = list.get(deadList.poll());
            ground[curr.row][curr.column] += curr.age / 2;
        }
    }

    private static void spring() {
        for (int i = 0; i < list.size(); i++) {
            Info curr = list.get(i);

            if (ground[curr.row][curr.column] >= curr.age) {
                ground[curr.row][curr.column] -= curr.age; //나무가 자신의 나이만큼 양분을 먹고 자신의 나이는 1증가한다.
                curr.age += 1;
            } else {
                //땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 경우 즉시 죽는다.
                //죽은 나무의 인덱스만을 가져온다.
                deadList.add(i);
                curr.isAlive = false;
            }
        }
    }

    static class Info {
        int row, column, age;
        boolean isAlive;

        public Info(int row, int column, int age, boolean isAlive) {
            this.row = row;
            this.column = column;
            this.age = age;
            this.isAlive = isAlive;
        }
    }
}
