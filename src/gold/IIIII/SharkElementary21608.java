package gold.IIIII;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 입력
 * N : 교실의 크기, 각 칸마다 학생 배치
 * N * N : 학생 수
 * 한 학생 당 좋아하는 학생의 번호 : 4개가 주어짐
 * <p>
 * 인접하다 : 상하좌우 한칸만 떨어져 있다
 * <p>
 * 규칙
 * 1. 인접한 칸에 좋아하는 학생이 가장 많은 칸에 배치한다.
 * 2. 같으면, 비어있는 칸이 많은 칸에 배치한다.
 * 3. 같으면, 행의 번호가 가장 작은 칸에 먼저 배치
 * 4. 같으면, 열의 번호가 가장 작은 칸에 먼저 배치
 * <p>
 * 출력
 * 배치 된 모든 학생의 만족도의 합 --> 좋아하는 인접한 학생 수 (0:0, 1:1, 2:10, 3:100, 4:1000) 10의 제곱
 */
public class SharkElementary21608 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N;
    static int[][] room;
    static int dx[] = {0, 1, 0, -1};
    static int dy[] = {-1, 0, 1, 0}; //북 동 남 서
    static Map<Integer, int[]> hashMap = new HashMap<>();
    static int total = 0;

    public SharkElementary21608() throws IOException {
        N = Integer.parseInt(br.readLine());
        room = new int[N][N];
        for (int i = 0; i < N * N; i++) {
            st = new StringTokenizer(br.readLine());
            int student = Integer.parseInt(st.nextToken());
            int[] a = new int[4]; //0, 1, 2, 3
            for (int j = 0; j < 4; j++) {
                a[j] = Integer.parseInt(st.nextToken());
            }
            hashMap.put(student, a); //key: 학생 번호, value: 좋아하는 학생 번호 배열
            setStudent(student);
        }

        satisfaction();
        System.out.println(total);
    }

    public static void satisfaction() {

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int[] likeStudents = hashMap.get(room[i][j]);
                int count = 0;
                for (int k = 0; k < 4; k++) {
                    int row = i + dy[k];
                    int column = j + dx[k];
                    if (!(row >= 0 && row < N && column >= 0 && column < N)) {
                        continue;
                    }
                    int findRoom = room[row][column];

                    for (int l = 0; l < 4; l++) {
                        if (findRoom == likeStudents[l]) {
                            count++;
                        }
                    }
                }
                switch (count) {
                    case 1:
                        total += 1;
                        break;
                    case 2:
                        total += 10;
                        break;
                    case 3:
                        total += 100;
                        break;
                    case 4:
                        total += 1000;
                        break;
                }
            }
        }
    }

    public static void setStudent(int student) {

        //1. 인접한 칸에 좋아하는 학생이 가장 많은 칸에 배치한다.
        //2. 같으면, 비어있는 칸이 많은 칸에 배치한다.
        //3. 같으면, 행의 번호가 가장 작은 칸에 먼저 배치
        //4. 같으면, 열의 번호가 가장 작은 칸에 먼저 배치

        int[] likeStudents = hashMap.get(student);
        int f1 = likeStudents[0];
        int f2 = likeStudents[1];
        int f3 = likeStudents[2];
        int f4 = likeStudents[3];

        //1. 모든 칸에 대해 좋아하는 학생수, 빈칸의 수를 리스트에 저장한다.
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 현재 칸 기준으로 좋아하는친구수, 빈칸의 수를 list에 저장한다.
                int likefriend = 0; //좋아하는 친구 수
                int blank = 0; //빈칸의 수
                for (int k = 0; k < 4; k++) {
                    // 내 기준 북 동 남 서 순서대로 좋아하는 학생, 빈킨 수 count
                    int row = i + dy[k];
                    int column = j + dx[k];
                    if (!(row >= 0 && row < N && column >= 0 && column < N)) {
                        //찾으려는 칸이 범위를 초과하면 다음 칸으로 반복문을 넘긴다.
                        continue;
                    }
                    int findRoom = room[row][column];
                    if (findRoom == f1 || findRoom == f2 || findRoom == f3 || findRoom == f4) {
                        likefriend++;
                    }
                    if (findRoom == 0) {
                        blank++;
                    }
                }
                list.add(new int[]{likefriend, blank, i, j});
            }
        }

        //2. 저장한 리스트를 규칙에 맞게 정렬한다.
        list.sort(new Comparator<>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] < o2[0]) {
                    return 1; //좋아하는 친구 수가 더 많은 것
                } else if (o1[0] == o2[0]) {
                    if (o1[1] < o2[1]) { //빈 공간이 더 많은 것
                        return 1;
                    } else if (o1[1] == o2[1]) {
                        if (o1[2] > o2[2]) { //row, column은 작은 것
                            return 1;
                        } else if (o1[2] == o2[2]) {
                            if (o1[3] > o2[3]) {
                                return 1;
                            }
                        }
                    }
                }
                return -1;
            }
        });

        //3. 정렬된 N*N개의 칸에 대해 빈 칸인 곳에 학생을 배치한다.
        for (int n = 0; n < N * N; n++) {
            int row = list.get(n)[2];
            int column = list.get(n)[3];
            if (room[row][column] == 0) {
                room[row][column] = student;
                return; //우선순위에 따라 최초로 찾을 경우 바로 return 한다.
            }
        }
    }
}
