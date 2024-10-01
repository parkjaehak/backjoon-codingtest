package silver.IIIII;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Coordinate2_11651 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int N;
    static Info[] array;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        array = new Info[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            array[i] = new Info(x, y);
        }

        //Comparator를 사용하면 Comparable에 비해 동일한 클래스라도 필요에 따라 다양한 정렬 기준을 적용할 수 있기 때문에, 코드의 재사용성과 유연성이 높다.
        Arrays.sort(array, new Comparator<Info>() {
            @Override
            public int compare(Info o1, Info o2) {
                if (o1.y == o2.y) {
                    return Integer.compare(o1.x, o2.x);
                }
                return Integer.compare(o1.y, o2.y);
            }
        });

        for (int i = 0; i < array.length; i++) {
            sb.append(array[i].x).append(" ").append(array[i].y).append('\n');
        }
        bw.write(String.valueOf(sb));
        bw.flush();
    }

    static class Info {
        int x, y;

        public Info(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
