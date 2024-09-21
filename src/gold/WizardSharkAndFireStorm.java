package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class WizardSharkAndFireStorm {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] A;
    static int[] fireStorm;
    static int N, Q ,N2;
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1}; //북 동 남 서
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        N2 = (int)Math.pow(2, N);

        A = new int[N2][N2];
        for (int i = 0; i < N2; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N2; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        fireStorm = new int[Q];
        st = new StringTokenizer(br.readLine());
        for (int q = 0; q < Q; q++) {
            fireStorm[q] = Integer.parseInt(st.nextToken()); //시전한 파이어스톰
        }

        processFireStorm();

        //출력: A[r][c]의 합, 가장 큰 덩어리가 차지하는 칸의 개수
        int total = 0;
        for (int i = 0; i < N2; i++) {
            for (int j = 0; j < N2; j++) {
                total += A[i][j];
            }
        }

        int largestBlockSize = findLargestIceBlock(); // 가장 큰 덩어리 크기 찾기


        System.out.println(total);
        System.out.println(largestBlockSize);
    }

    // 남아있는 얼음 중 가장 큰 덩어리의 크기 구하기
    private static int findLargestIceBlock() {
        visited = new boolean[N2][N2];
        int maxSize = 0;

        for (int i = 0; i < N2; i++) {
            for (int j = 0; j < N2; j++) {
                if (A[i][j] > 0 && !visited[i][j]) {
                    int size = dfs(i, j);
                    maxSize = Math.max(maxSize, size);
                }
            }
        }
        return maxSize;
    }

    private static int dfs(int row, int col) {
        visited[row][col] = true;
        int size = 1; // 현재 칸도 덩어리 크기에 포함

        for (int i = 0; i < 4; i++) {
            int newRow = row + dy[i];
            int newCol = col + dx[i];

            // 범위 내에 있고, 방문하지 않았으며, 얼음이 있는 칸이라면 탐색
            if (newRow >= 0 && newRow < N2 && newCol >= 0 && newCol < N2 && !visited[newRow][newCol] && A[newRow][newCol] > 0) {
                size += dfs(newRow, newCol); // 덩어리 크기 추가
            }
        }

        return size;
    }
    private static void processFireStorm() {

        for (int i = 0; i < fireStorm.length; i++) {
            int L = fireStorm[i];
            //2의 L승 격자로 나눈 후 90도 시계방향 회전
            // 상하좌우 얼음이 0개, 1개, 2개 있으면 해당 칸 얼음의 양 - 1
            divideAndRotate(L);
            countAndRemoveIceBlock();
        }
    }


    private static void countAndRemoveIceBlock() {
        ArrayList<Info> list = new ArrayList<>();

        for (int i = 0; i < N2; i++) {
            for (int j = 0; j < N2; j++) {
                int curr = A[i][j];

                if (curr == 0) {
                    continue;
                }

                int count = 0;
                for (int k = 0; k < 4; k++) {
                    int nr = i + dy[k];
                    int nc = j + dx[k];

                    if (nr >= 0 && nr < N2 && nc >= 0 && nc < N2 && A[nr][nc] > 0) {
                        count++;
                    }
                }

                if (count < 3) {
                    list.add(new Info(i, j));
                }
            }
        }

        for (int i = 0; i < list.size(); i++) {
            Info info = list.get(i);
            A[info.row][info.column] -= 1;
        }
    }

    private static void divideAndRotate(int L) {
        int size = (int) Math.pow(2, L);
        int[][] tempA = new int[size][size];

        for (int i = 0; i < N2; i += size) {
            for (int j = 0; j < N2; j += size) {

                //서브 그리드를 추출하고 90도 시계방향 회전시킨다.
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        //(k,l) -> (l, size - 1 - k)
                        tempA[l][size - 1 - k] = A[i + k][j + l];
                    }
                }

                //회전된 서브 그리드를 원래 그리드에 삽입한다.
                for (int k = 0; k < size; k++) {
                    for (int l = 0; l < size; l++) {
                        A[i + k][j + l] = tempA[k][l];
                    }
                }
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
