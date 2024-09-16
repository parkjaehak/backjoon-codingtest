package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MonominoDomino2_20061 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int t, x, y, N; //x는 row, y는 column
    static int[][] green = new int[6][4];
    static int[][] blue = new int[4][6];
    static int score;
    static ArrayList<Info> blocks = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        //블럭의 시작위치와 정보를 저장한다.
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            t = Integer.parseInt(st.nextToken()); //1타입: 1x1, 2타입: 1x2, 3타입: 2x1
            x = Integer.parseInt(st.nextToken());
            y = Integer.parseInt(st.nextToken());

            blocks.add(new Info(x, y, t));  // 타입별로 ArrayList에 블록 정보 저장한다.
        }
        fallBlock();

        int count = 0;
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (green[i][j] == 1) {
                    count++;
                }
                if (blue[j][i] == 1) {
                    count++;
                }
            }
        }
        System.out.println(score);
        System.out.println(count);
    }

    private static void fallBlock() {
        //규칙1: 4,5번 인덱스에 블럭이 존재하면 밀어버린다.
        //규칙2: 하나의 행이나 열이 생기면 제거하고 각 아래, 우측으로 이동시킨다. 점수를 없어진 행열만큼 얻는다.
        //빈칸은 0, 칸이 존재하면 1

        for (int i = 0; i < N; i++) {
            Info info = blocks.get(i);
            int type = info.type;

            moveGreen(type, info.column);
            moveBlue(type, info.row);

            removeGreen();
            removeBlue();

            moveLightGreen();
            moveLightBlue();

        }

    }

    private static void moveLightBlue() {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (blue[j][i] == 1) {
                    count++;
                    break;
                }
            }
        }
        for (int k = 5; k >= 2; k--) {
            for (int i = 0; i < 4; i++) {
                blue[i][k] = blue[i][k - count];
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                blue[j][i] = 0;
            }
        }
    }

    private static void moveLightGreen() {
        int count = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (green[i][j] == 1) {
                    count++;
                    break;
                }
            }
        }
        for (int k = 5; k >= 2; k--) {
            for (int i = 0; i < 4; i++) {
                green[k][i] = green[k - count][i];
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                green[i][j] = 0;
            }
        }
    }

    private static void removeBlue() {
        while (true) {
            boolean run = true;

            for (int col = 5; col > 1; col--) {
                int count = 0;
                for (int row = 0; row < 4; row++) {
                    if (blue[row][col] == 1) {
                        count++;
                    } else {
                        break;
                    }
                }
                if (count == 4) {
                    run = false;
                    score++;
                    for (int i = col - 1; i >= 1; i--) {
                        for (int j = 0; j < 4; j++) {
                            blue[j][i + 1] = blue[j][i];
                        }
                    }
                    for (int j = 0; j < 4; j++) {
                        blue[j][1] = 0;
                    }
                    break;
                }
            }
            if (run) {
                break;
            }
        }
    }

    private static void removeGreen() {

        while (true) {
            boolean run = true;

            for (int i = 5; i > 1; i--) {
                int count = 0;
                for (int j = 0; j < 4; j++) {
                    if (green[i][j] == 1) {
                        count++;
                    } else {
                        break;
                    }
                }
                //한 row라도 꽉 차있으면 우선 해당 row를 제거하고 반복을 계속한다.
                if (count == 4) {
                    run = false;
                    score++;
                    for (int k = i - 1; k >= 1; k--) {
                        for (int j = 0; j < 4; j++) {
                            green[k + 1][j] = green[k][j];
                        }
                    }
                    for (int j = 0; j < 4; j++) {
                        green[1][j] = 0;
                    }
                    break;
                }
            }
            if (run) {
                break;
            }
        }
    }

    private static void moveBlue(int type, int row) {
        int column = 1;
        if (type == 1) {
            //1x1
            for (int i = 2; i < 6; i++) {
                if (blue[row][i] == 1) {
                    break;
                }
                column = i;
            }
            blue[row][column] = 1;

        } else if (type == 2) {
            //1x2
            for (int i = 2; i < 6; i++) {
                if (blue[row][i] == 1 || blue[row][i - 1] == 1) {
                    break;
                }
                column = i;
            }
            blue[row][column] = blue[row][column - 1] = 1;

        } else if (type == 3) {
            //2x1
            for (int i = 2; i < 6; i++) {
                if (blue[row][i] == 1 || blue[row + 1][i] == 1) {
                    break;
                }
                column = i;
            }
            blue[row][column] = blue[row + 1][column] = 1;
        }
    }

    private static void moveGreen(int type, int column) {
        int row = 1;
        if (type == 1) {
            //1x1
            for (int i = 2; i < 6; i++) {
                if (green[i][column] == 1) {
                    break;
                }
                row = i;
            }
            green[row][column] = 1;
        } else if (type == 2) {
            //1x2
            for (int i = 2; i < 6; i++) {
                if (green[i][column] == 1 || green[i][column + 1] == 1) {
                    break;
                }
                row = i;
            }
            green[row][column] = green[row][column + 1] = 1;
        } else if (type == 3) {
            //2x1
            for (int i = 2; i < 6; i++) {
                if (green[i][column] == 1 || green[i - 1][column] == 1) {
                    break;
                }
                row = i;
            }
            green[row][column] = green[row - 1][column] = 1;
        }
    }

    static class Info {
        int row;
        int column;
        int type;

        public Info(int row, int column, int type) {
            this.row = row;
            this.column = column;
            this.type = type;
        }
    }
}
