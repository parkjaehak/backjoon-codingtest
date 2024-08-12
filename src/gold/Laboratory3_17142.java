package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 연구소의 크기 N
 * 활성시킬 바이러스의 개수 M
 * 비활성된 바이러스 수(2의 개수)는 최대 10개이며, 활성시킬 바이러스 M보다 크거나 같아야 한다.
 * 0: 빈칸, 1: 벽, 2: 비활성 바이러스
 * <p>
 * 연구소의 모든 빈칸에 바이러스가 존재할 수 있는 최소 시간
 * 모든 빈칸에 바이러스를 퍼트릴 수 없다면 -1을 출력한다.
 */
public class Laboratory3_17142 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] array;
    static int N, M;
    static int minTime = Integer.MAX_VALUE;
    static List<int[]> viruses = new ArrayList<>();
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {-1, 0, 1, 0}; //북 동 남 서
    static int uninfected = 0;


    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken()); //활성화시킬 바이러스 수

        array = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                array[i][j] = Integer.parseInt(st.nextToken()); //모든 데이터 입력 완료
                //모든 바이러스 위치를 리스트에 저장한다. (비활성화 + 활성화)
                if (array[i][j] == 2) {
                    int[] location = new int[]{i, j};
                    viruses.add(location);
                } else if (array[i][j] == 0) {
                    uninfected++;
                }
            }
        }

        // 빈칸 없는 경우 바로 끝
        if(uninfected == 0) {
            System.out.println(0);
            return;
        }

        dfs(0, 0);

        if (minTime == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(minTime);
        }
    }

    static void dfs(int count, int index) {
        //2. 활성화 가능한 수에 도달할 경우 bfs 진행한다.
        if (count == M) {
            //이 구간에 들어갔다는 의미는 활성화시킬 바이러스를 모두 찾았다는 의미
            bfs();
            return;
        }

        //1. 총 바이러스 수만큼 반복하여 호출한다. (조합)
        for (int i = index; i < viruses.size(); i++) {
            int[] virus = viruses.get(i);
            // 바이러스의 위치를 가져와서 활성화시킨다. 이때 음수값을 붙여서 활성화/비활성화를 구분한다.
            int row = virus[0];
            int column = virus[1];

            array[row][column] *= -1; //-2: 활성화
            dfs(count + 1, i + 1);
            array[row][column] *= -1; //2: 비활성화
        }
    }

    static void bfs() {
        // -2인 숫자를 기준으로 바이러스를 퍼트린다.
        Queue<Info> queue = new LinkedList<>();
        boolean[][] check = new boolean[N][N]; //퍼트린 곳인지 여부 체크
        int infected = 0; //실제 감염시킨 칸 수 (최초 바이러스는 포함시키지 않는다.)

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (array[i][j] == -2) {
                    queue.add(new Info(i, j, 0)); //활성화된 바이러스의 위치를 큐에 저장한다.
                    check[i][j] = true;
                }
            }
        }

        int time = 0;
        while (!queue.isEmpty()) {
            Info currentNode = queue.poll();
            int currRow = currentNode.dy;
            int currColumn = currentNode.dx;
            int currCount = currentNode.count;

            time = currCount; //가장 마지막 노드 탐색 시간을 반환한다.

            //현재 위치에서 이동가능한 상하좌우 방향을 탐색한다.
            for (int i = 0; i < 4; i++) {
                int ny = currRow + dy[i];
                int nx = currColumn + dx[i];
                // 범위를 넘었거나 이미 바이러스 전파한 곳인 경우 건너뛴다.
                if (!(0 <= nx && nx < N && 0 <= ny && ny < N) || check[ny][nx] == true) {
                    continue;
                }

                if (array[ny][nx] == 0) { // 빈 셀이고 아직 감염되지 않은 경우
                    infected++;
                    check[ny][nx] = true;
                    queue.add(new Info(ny, nx, currCount + 1));

                } else if (array[ny][nx] == 2) { // 비활성화된 바이러스 셀인 경우
                    // 비활성화된 바이러스 셀도 큐에 추가하되 감염 시간에는 포함되지 않음
                    check[ny][nx] = true;
                    queue.add(new Info(ny, nx, currCount + 1));
                }
            }
            /**
             *   모든 0이 감염이 되었다면 바이러스가 다 퍼졌다는 의미
             *   따라서 마지막 2가 남은 경우에 전파하지 않고 그대로 종료
             *   이경우, 새로 뽑은 시간값 할당이 안되므로, 임의로 1 증가
             */
            if(infected == uninfected) {
                time = currCount + 1;
                break;
            }
        }
        //전부 감염시키지 못한 경우에는 아래 조건문으로 걸러낸다.
        if (infected != uninfected) {
            return;
        }
        //전부 감염시킨 경우에만 그 시간을 비교한다.
        minTime = Math.min(minTime, time);
    }

    static class Info {
        int dx, dy, count;

        public Info(int dy, int dx, int count) {
            this.dy = dy;
            this.dx = dx;
            this.count = count;
        }
    }
}
