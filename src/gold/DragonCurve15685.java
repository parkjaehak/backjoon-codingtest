package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class DragonCurve15685 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static int[] dy = {0, -1, 0, 1}; //0, 1, 2, 3 (동 북 서 남)
    static int[] dx = {1, 0, -1, 0};
    static boolean [][] check = new boolean[101][101]; //해당 지점에 드래곤 커브지점인지 체크


    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            //x,y를 기준으로 d방향으로 g세대만큼 커브를 작성
            curve(y, x, d, g);
        }

        int count = 0;
        // 맨 오른쪽, 아래쪽은 탐색에서 제외한다.
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                //네 꼭지점을 거쳐간 경우 사각형이다.
                if (check[i][j] && check[i + 1][j] && check[i][j + 1] && check[i + 1][j + 1]) {
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    private static void curve(int row, int column, int dir, int gen) {
        //시작점
        check[row][column] = true;

        //0세대가 완료되면 d 방향으로 한칸 이동한다.
        int endRow = row + dy[dir];
        int endColumn = column + dx[dir];
        int endDir = (dir + 1) % 4;

        List<Info> infos = new ArrayList<>();
        infos.add(new Info(endRow, endColumn, endDir));
        check[endRow][endColumn] = true;

        for (int i = 0; i < gen; i++) {
            for (int j = infos.size() - 1; j >= 0; j--) { //마지막 인덱스부터 0까지
                Info last = infos.get(j);

                //기존 점에서 계속 그려나간다.
                endRow += dy[last.dir];
                endColumn += dx[last.dir];
                endDir = (last.dir + 1) % 4;
                check[endRow][endColumn] = true; //해당 지점이 생성된 경우 드래곤 커브지점이라는 뜻이다.

                infos.add(new Info(endRow, endColumn, endDir));
            }
        }
    }

    static class Info {
        int row, column, dir;

        public Info(int row, int column, int dir) {
            this.row = row;
            this.column = column;
            this.dir = dir;
        }
    }
}
