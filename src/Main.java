import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int score = 0;
    static int map[][] = {{0, 2, 4, 6, 8, 10}, {10, 13, 16, 19, 25}, {10, 12, 14, 16, 18, 20}, {20, 22, 24, 25}, {20, 22, 24, 26, 28, 30}, {30, 28, 27, 26, 25}, {30, 32, 34, 36, 38, 40}, {25, 30, 35, 40}, {40, 0}};
    static int play[] = new int[10];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        boolean visited[][] = {{false, false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false, false}, {false, false, false, false}, {false, false, false, false, false, false}, {false, false, false, false, false}, {false, false, false, false, false,false}, {false, false, false, false}, {false, false}};
        int yut[][] = {{0,0},{0,0},{0,0},{0,0}};

        for(int i = 0; i < 10; i++) {
            play[i] = Integer.parseInt(st.nextToken());
        }

        game(0, yut, 0, visited);
        System.out.println(score);
    }


    static void game(int ind, int[][] y, int s, boolean visited[][]) {
        if(ind >= 10) {
            score = Math.max(score, s);
            return;
        }

        for(int i = 0; i < 4; i++) {
            if(y[i][0] == 8 && y[i][1] == 1) continue;
            int sr = y[i][0];
            int sc = y[i][1];
            int cr = y[i][0];
            int cc = y[i][1] + play[ind];

            if(cc == map[cr].length-1) {
                if(cr < 6 && cr%2 == 0) { // 0 -> 1, 2 -> 3, 4 -> 5
                    cr++;
                    cc = 0;
                }else if(cr == 6 || cr == 7) { // 6, 7 -> 8
                    cr = 8;
                    cc = 0;
                }else if(cr%2 == 1){ // 1, 3, 5 -> 7
                    cr = 7;
                    cc = 0;
                }else {
                    cc = 1;
                }
            }else if(cc >= map[cr].length) {
                cc = cc - map[cr].length + 1;
                if(cr%2 == 0) { // 0 -> 2, 2 -> 4, 4 -> 6, 6 -> 8, 8 -> 스탑
                    cr += 2;
                    if(cr >= 8) {
                        cr = 8;
                        if(cc >= 1) {
                            cc = 1;
                        }
                    }
                }else if(cr == 7) {
                    cr = 8;
                    if(cc >= 1) {
                        cc = 1;
                    }
                }else {
                    cr = 7;
                    if(cc >= map[cr].length) {
                        cr = 8;
                        cc = 1;
                    }else if(cc == map[cr].length-1){
                        cr = 8;
                        cc = 0;
                    }
                }
            }

            if((cr == 8 && cc == 1) || !visited[cr][cc]) {
                visited[sr][sc] = false;
                visited[cr][cc] = true;
                y[i][0] = cr;
                y[i][1] = cc;

                game(ind + 1, y, s + map[cr][cc], visited);
                visited[cr][cc] = false;
                visited[sr][sc] = true;
                y[i][0] = sr;
                y[i][1] = sc;
            }
        }
    }
}
