package gold.II;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class SharkMiddleSchool21609 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] array;
    static boolean[][] check;
    static int N, M;
    static int[] dx = {0, 1, 0, -1}; //북동남서
    static int[] dy = {-1, 0, 1, 0};
    static int score;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        array = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //블록 그룹 찾기(0인 블록이 가장 많은 경우, 기준블록의 행 큰것, 열 큰것)
        //-> 블록의 수가 2보다 커야 하며 -1은 포함 x, 0은 포함되어도 됨,
        while (true) {
            //1. 크기가 가장 큰 블록 그룹을 찾는다.
            Block block = findBlockGroup();
            if (block == null) {
                break;
            }
            //블록그룹의 점수는 사라진 블록의 제곱과 같다.
            score += block.sum * block.sum;
            //2. 찾은 블록 그룹의 블록을 제거한다.
            removeBlock(block);
            //3. 격자에 중력을 작용시킨다.
            gravity();
            //4. 반시계 90도 회전
            rotate();
            //5. 격자에 중력을 작용시킨다.
            gravity();
        }
        System.out.println(score);
    }


    private static void rotate() {
        int[][] copy = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copy[i][j] = array[j][N - i - 1];
            }
        }
        array = copy;
    }

    private static void gravity() {
        for (int i = 0; i < N; i++) {
            for (int j = N - 2; j >= 0; j--) {
                if (array[j][i] == -1 || array[j][i] == -2) {
                    continue;
                }
                move(j, i);
            }
        }
    }

    private static void move(int row, int column) {
        int currRow = row;
        while (true) {
            currRow++;
            //범위 벗어나거나 검정 블록이거나 빈 칸이 아닐 경우 해당 칸으로 움직일 수 없다.break
            if (currRow >= N || array[currRow][column] != -2) {
                break;
            }
        }
        currRow--;
        if (currRow == row) {
            //더 이상 움직이지 않으면 멈춘다.
            return;
        }
        array[currRow][column] = array[row][column]; //값을 이동시키고 기존 칸은 빈칸이된다.
        array[row][column] = -2;
    }

    private static void removeBlock(Block block) {
        Queue<Info> queue = new LinkedList<>();
        check = new boolean[N][N];
        queue.add(new Info(block.row, block.column));
        check[block.row][block.column] = true;
        array[block.row][block.column] = -2; //공백으로 처리한다.

        while (!queue.isEmpty()) {
            Info curr = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nr = curr.row + dy[i];
                int nc = curr.column + dx[i];
                //범위를 넘어가면 패스

                if (nr >= N || nr < 0 || nc >= N || nc < 0) {
                    continue;
                }
                if (array[nr][nc] == -1 || array[nr][nc] == -2 || check[nr][nc]) {
                    continue;
                }
                if (array[nr][nc] != block.color) {
                    if (array[nr][nc] == 0) {
                        //컬러가 다른데 무지개 블록인 경우
                        array[nr][nc] = -2;
                        check[nr][nc] = true;
                        queue.add(new Info(nr, nc));
                    }
                    continue;
                }
                //컬러가 같은 경우
                array[nr][nc] = -2;
                check[nr][nc] = true;
                queue.add(new Info(nr, nc));
            }
        }
    }

    private static Block findBlockGroup() {
        //기준 블록은 무지개 블록이 아닌 블록 중에서 행의 번호가 가장 작은 블록, 그러한 블록이 여러개면 열의 번호가 가장 작은 블록이다.
        Block maxBlock = new Block(0, 0, -2, Integer.MIN_VALUE, Integer.MIN_VALUE);
        check = new boolean[N][N];

        //기준블럭정하기
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //검정블록이거나 무지개블록이거나 비어있거나 방문을 했으면 스킵한다.
                if (array[i][j] == -1 || array[i][j] == 0 || array[i][j] == -2 || check[i][j]) {
                    continue;
                }
                //무지개블록 방문 처리 초기화
                for (int k = 0; k < N; k++) {
                    for (int l = 0; l < N; l++) {
                        if (array[k][l] == 0) {
                            check[k][l] = false;
                        }
                    }
                }
                Block curr = bfs(i, j, array[i][j]);
                //최대 블럭이 2개보다 작으면 스킵
                if (curr == null) {
                    continue;
                }
                if (maxBlock.compareBlock(curr)) {
                    //현재 기준블록이 포함된 블록그룹이 더 클경우 갱신한다.
                    maxBlock = curr;
                }
            }
        }
        //초기값과 같은 경우 블록그룹을 찾지 못한 것이다.
        if (maxBlock.sum == Integer.MIN_VALUE) {
            return null;
        }
        return maxBlock;
    }

    private static Block bfs(int row, int column, int color) {
        //색이 있는 블럭을 기준으로 같은 색의 블럭을 찾는다.
        Queue<Info> queue = new LinkedList<>();
        queue.add(new Info(row, column));
        check[row][column] = true;
        int sum = 1;
        int rainbowSum = 0;

        while (!queue.isEmpty()) {
            Info curr = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nr = curr.row + dy[i];
                int nc = curr.column + dx[i];

                //범위를 넘어가면 패스
                if (nr >= N || nr < 0 || nc >= N || nc < 0) {
                    continue;
                }
                //검정색이거나 비어있거나 이미 간 곳이면 패스
                if (array[nr][nc] == -1 || array[nr][nc] == -2 || check[nr][nc]) {
                    continue;
                }
                if (array[nr][nc] != color) {
                    if (array[nr][nc] == 0) {
                        //컬러가 다른데 무지개 블록인 경우
                        rainbowSum++;
                        sum++;
                        check[nr][nc] = true;
                        queue.add(new Info(nr, nc));
                    }
                    continue;
                }
                //컬러가 같은 경우
                sum++;
                check[nr][nc] = true;
                queue.add(new Info(nr, nc));
            }
        }
        if (sum < 2) {
            return null;
        } else {
            return new Block(row, column, color, sum, rainbowSum); //블럭그룹의 기준블럭을 리턴한다.
        }
    }

    static class Info {
        int row;
        int column;
        public Info(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    static class Block {
        //기준 블록
        int row, column, color, sum, rainbowSum; //기준 블록의 좌표, 색상, 그룹의 총 블록수, 무지개 블록수

        public Block(int row, int column, int color, int sum, int rainbowSum) {
            this.row = row;
            this.column = column;
            this.color = color;
            this.sum = sum;
            this.rainbowSum = rainbowSum;
        }

        public boolean compareBlock(Block newBlock) {
            //새로운 것이 크면 true, 기존 것이 크면 false
            if (this.sum != newBlock.sum) {
                //블록그룹의 크기가 큰 것
                return this.sum < newBlock.sum;
            }
            if (this.rainbowSum != newBlock.rainbowSum) {
                //무지개 수가 많은 것
                return this.rainbowSum < newBlock.rainbowSum;
            }
            if (this.row != newBlock.row) {
                //행이 큰 것
                return this.row < newBlock.row;
            }
            //열이 큰 것
            return this.column < newBlock.column;
        }
    }
}
