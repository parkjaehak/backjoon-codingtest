package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 3 * 3 배열
 * R연산 : 행 >= 열
 * C연산 : 행 < 열
 * <p>
 * 1. 수의 등장 횟수 오름차순 : 1 -> 2
 * 2. 수의 크기 오름차순 : 1 -> 2
 * <p>
 * [수] -> [수, 횟수]
 * 가장 큰 열, 행 기준 나머지 열, 행은 0으로 채운다.
 * <p>
 * r : row
 * c : column
 * k : 값
 * <p>
 * A[r][c] = k, r,c에 k가 되기 위한 연산의 최소 시간 : 1초마다 배열 연산
 */
public class ArrayAndCalculation_17140 {

    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int R = 0, C = 0, K = 0;
    static int[][] A = new int[101][101]; //배열은 1부터 시작한다.

    static int rowCount = 3, columnCount = 3;

    public ArrayAndCalculation_17140() throws IOException {
        st = new StringTokenizer(bf.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());


        //1.  2차원 배열 인덱스(1~3)에 값을 삽입한다.
        for (int r = 1; r < 4; r++) {
            st = new StringTokenizer(bf.readLine());
            for (int c = 1; c < 4; c++) {
                A[r][c] = Integer.parseInt(st.nextToken());
            }
        }

        boolean found = false;
        for (int time = 0; time <= 100; time++) {
            if (A[R][C] == K) {
                System.out.println(time);
                found = true;
                break;
            }
            operation();
        }

        // 목표 값이 목표 시간 안에 발견되지 않았을 경우 -1 출력
        if (!found) {
            System.out.println(-1);
        }
    }
    public static void operation() {

        //R연산을 진행한다.
        if (rowCount >= columnCount) {
            for (int i = 1; i <= rowCount; i++) {
                operationR(i);
            }

        } else {
            //C연산을 진행한다.
            for (int i = 1; i <= columnCount; i++) {
                operationC(i);
            }
        }
    }

    public static void operationR(int row) {
        PriorityQueue<Info> info = new PriorityQueue<>(); // 정의한 compareTo에 따라 정렬한다.
        Map<Integer, Integer> map = new HashMap<>(); // number와 count를 key, value 쌍으로 관리한다.

        for (int i = 1; i <= columnCount; i++) {
            int key = A[row][i];
            if (key == 0) {
                continue;
            }
            Integer count = map.get(key);
            if (count == null) {
                map.put(key, 1);
            } else {
                map.put(key, count + 1);
            }
        }

        map.forEach((k, v) -> info.add(new Info(k, v)));

        int i = 1;
        while (!info.isEmpty()) {
            Info polled = info.poll();
            A[row][i++] = polled.number;
            A[row][i++] = polled.count;
            //121 --> 2111
        }

        columnCount = Math.max(columnCount, i - 1);

        while (i <= 99) {
            A[row][i++] = 0;
            A[row][i++] = 0;
        }
    }
    public static void operationC(int column) {
        PriorityQueue<Info> info = new PriorityQueue<>();
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 1; i <= rowCount; i++) {
            int key = A[i][column];
            if (key == 0) {
                continue;
            }
            Integer count = map.get(key);
            if (count == null) {
                map.put(key, 1);
            } else {
                map.put(key, count + 1);
            }
        }

        // 모든 key-value 쌍 집합을 꺼내어 Info객체를 생성 후 우선순위 큐에 저장한다.
        map.forEach((k, v) -> info.add(new Info(k, v)));

        int i = 1;
        while (!info.isEmpty()) {
            Info polled = info.poll();
            A[i++][column] = polled.number;
            A[i++][column] = polled.count;
            //121 --> 2111
        }

        rowCount = Math.max(rowCount, i - 1);

        while (i <= 99) {
            A[i++][column] = 0;
            A[i++][column] = 0;
        }
    }

    static class Info implements Comparable<Info> {
        int number; //숫자
        int count; //숫자가 한 열 또는 행에 존재하는 횟수

        public Info(int number, int count) {
            this.number = number;
            this.count = count;
        }

        @Override
        public int compareTo(Info o) {
            if (this.count > o.count) {
                return 1; //현재 수의 횟수가 더 많을 경우 뒤로 보낸다.
            } else if (this.count == o.count) {
                return this.number - o.number; //횟수가 같은 경우 큰 숫자를 뒤로 보낸다.
            } else {
                return -1;
            }
        }
    }
}
