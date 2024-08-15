package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 상어 > 물고기 : 먹을 수 있음 + 이동
 * 상어 = 물고기 : 이동만
 * 상어 < 물고기 : 이동불가능
 * <p>
 * 거리가 가까운 칸 우선 먹을 수 있고 위칸 -> 왼쪽칸 순서
 * 현재 자신의 크기와 같은 수의 물고기를 먹어야 크기가 1 증가
 * size = count, size++
 */
public class BabyShark16236 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map;
    static int N;
    static int result;
    static int[] dy = {0, 1, 0, -1}; //동남서북
    static int[] dx = {1, 0, -1, 0};
    static int minRow;
    static int minColumn;
    static int minDistance;
    static int[][] distance;
    static int size = 2;
    static int time = 0;
    static int count = 0;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        int startRow = 0;
        int startColumn = 0;

        //1. map에 물고기 정보 저장(물고기의 크기 1~6)
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 9) {
                    startRow = i;
                    startColumn = j;
                    map[i][j] = 0; // 상어가 있었던 위치도 이 후로 탐색해야 하므로 0으로 초기화
                }
            }
        }


        //자신이 바로 다음에 먹을 수 있는 물고기 위치를 찾는다.
        //해당 칸의 물고기를 먹고 상어를 이동시킨다.
        while (true) {
            //상어가 매회마다 물고기를 잡아먹을때마다 새로 생성해야함.
            distance = new int[N][N];
            minRow = Integer.MAX_VALUE;
            minColumn = Integer.MAX_VALUE;
            minDistance = Integer.MAX_VALUE;

            //2.bfs를 통해 다음에 먹을 수 있는 물고기 위치를 찾는다.
            bfs(startRow, startColumn);

            //3. 초기화 상태인 경우에는 먹을 수 있는 물고기를 찾지 못한 경우이다.
            if (minRow == Integer.MAX_VALUE || minColumn == Integer.MAX_VALUE) {
                break;
            }

            count++; //상어가 물고기를 먹은 횟수
            map[minRow][minColumn] = 0; //물고기를 먹은 위치를 0으로 표시한다.
            startRow = minRow;
            startColumn = minColumn;
            time += distance[minRow][minColumn]; //총 걸린시간은 매회마다 물고기를 찾은 위치까지 걸린 이동 횟수의 합이다.

            if (count == size) {
                //물고기를 먹은 횟수가 물고기의 크기와 같은 경우 물고기의 크기를 1씩 증가한다.
                size++;
                count = 0; //물고기 먹은 횟수는 초기화한다.
            }
        }
        System.out.println(time);
    }

    static void bfs(int row, int column) {
        Queue<Info> queue = new LinkedList<>();
        queue.add(new Info(row, column));

        while (!queue.isEmpty()) {
            Info curr = queue.poll();

            for (int i = 0; i < 4; i++) {
                int ny = curr.row + dy[i];
                int nx = curr.column + dx[i];

                //범위 밖에 있는 경우 pass
                //distance 배열이 0이 아니라는 의미는 이동을 이미 했었다는 의미이다. 거쳐간 적이 있는 배열은 방문하지 않는다.
                if (ny >= N || ny < 0 || nx >= N || nx < 0 || distance[ny][nx] != 0) {
                    continue;
                }
                //상어크기보다 큰 위치는 pass(작거나 같은 위치만 탐색한다)
                if (map[ny][nx] > size) {
                    continue;
                }

                distance[ny][nx] = distance[curr.row][curr.column] + 1; //현재위치와의 거리를 저장한다.

                //빈칸이 아니고 상어보다 물고기 사이즈가 더 작아야 먹을 수 있다.
                if (map[ny][nx] < size && map[ny][nx] != 0) {

                    if (minDistance > distance[ny][nx]) {
                        //해당 위치의 물고기가 더 가까운 거리에 있는 경우 갱신한다.
                        minDistance = distance[ny][nx];
                        minRow = ny;
                        minColumn = nx;
                    } else if (minDistance == distance[ny][nx]) {
                        //물고기가 같은 거리에 있는 경우 row가 더 높은 물고기를 선택한다.
                        if (ny < minRow) {
                            minRow = ny;
                            minColumn = nx;
                        } else if (ny == minRow) {
                            //row의 높이가 같을 경우 column이 더 작은 물고기를 선택한다.
                            if (nx < minColumn) {
                                minRow = ny;
                                minColumn = nx;
                            }
                        }
                    }
                }
                queue.add(new Info(ny, nx));
            }
        }
    }

    static class Info {
        int row, column;
        public Info(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
