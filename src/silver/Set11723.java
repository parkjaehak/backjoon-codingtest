package silver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Set11723 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    static int M;
    static List<Integer> list = new ArrayList<>(); //사이즈는 최대 20 --> 정적인 array 사용해도 됨

    public static void main(String[] args) throws IOException {
        M = Integer.parseInt(br.readLine());

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            String operation = st.nextToken();
            int num = 0;
            if (!operation.equals("all") && !operation.equals("empty")) {
                num = Integer.parseInt(st.nextToken());
            }
            switch (operation) {
                case "add":
                    add(num);
                    break;
                case "remove":
                    remove(num);
                    break;
                case "check":
                    check(num);
                    break;
                case "toggle":
                    toggle(num);
                    break;
                case "all":
                    all();
                    break;
                case "empty":
                    empty();
                    break;
            }
        }
        bw.write(sb.toString());
        bw.flush();
    }

    private static void add(int num) {
        if (!list.contains(num)) {
            list.add(num);
        }
    }

    private static void remove(int num) {
        if (list.contains(num)) {
            list.remove(Integer.valueOf(num));
        }
    }
    private static void check(int num) {
        if (list.contains(num)) {
            sb.append(1).append('\n');
        } else {
            sb.append(0).append('\n');
        }
    }
    private static void toggle(int num) {
        if (list.contains(num)) {
            list.remove(Integer.valueOf(num));
        } else {
            list.add(num);
        }
    }
    private static void all() {
        list.clear(); //모든 요소 제거 후 1~20만 저장한다.
        for (int i = 1; i <= 20; i++) {
            list.add(i);
        }
    }
    private static void empty() {
        list.clear();
    }
}
