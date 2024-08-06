package gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 도시의 크기 : N
 * 살아있는 치킨 집 수 : M (1이상 13이하)
 * 폐업시킨 치킨 집 수 : 2인 곳 count - M
 * 0: 빈 칸, 1: 집, 2: 치킨집
 * 치킨 거리 : |r1-r2| + |c1-c2|
 */
public class ChickenDelivery15686 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int[][] city;
    static int N, M;
    static int minSumOfDistance = Integer.MAX_VALUE;
    static List<int[]> chickenLocation = new ArrayList<>();
    static List<int[]> homeLocation = new ArrayList<>();
    static List<int[]> foundLocation = new ArrayList<>();

    public ChickenDelivery15686() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //도시의 크기
        M = Integer.parseInt(st.nextToken()); //생존시킬 치킨집 수

        city = new int[N][N];
        for (int r = 0; r < N; r++) {
            st = new StringTokenizer(br.readLine());
            for (int c = 0; c < N; c++) {
                city[r][c] = Integer.parseInt(st.nextToken());
                if (city[r][c] == 2) {
                    //1. 우선 현재 치킨집과 집의 위치를 저장한다.
                    chickenLocation.add(new int[]{r, c});
                }
                if (city[r][c] == 1) {
                    homeLocation.add(new int[]{r, c});
                }
            }
        }
        //2. currentChicken 중 M개만 임의로 선택한다.
        choose(0, M); //시작, 선택할 개수

        System.out.println(minSumOfDistance);
    }
    public static void choose(int start, int count) {
        if (count == 0) {
            //5. 집기준으로 모든 치킨집과의 거리를 비교해 최소거리의 합을 저장한다.
            int sum = calculate();
            //6. recursive를 통해 모든 조합의 최소거리의 합과 비교해 최솟값을 유지 후 출력한다.
            minSumOfDistance = Math.min(minSumOfDistance, sum);

        } else {
            //3. 치킨 집 중 한 곳을 선택해서 찾은 치킨 집을 저장한다.
            for (int m = start; m < chickenLocation.size(); m++) {
                foundLocation.add(chickenLocation.get(m)); //4. 선택된 치킨집의 위치를 저장해 놓는다.
                choose(m+1, count-1);
                foundLocation.remove(chickenLocation.get(m));
            }
        }
    }

    private static int calculate() {
        int sum = 0;

        //일반집 기준으로 치킨집과 거리 비교 후 최소값을 저장한다.
        for (int i = 0; i < homeLocation.size(); i++) {
            int minDistance = Integer.MAX_VALUE;

            for (int j = 0; j < foundLocation.size(); j++) {
                int hr = homeLocation.get(i)[0];
                int hc = homeLocation.get(i)[1];
                int fr = foundLocation.get(j)[0];
                int fc = foundLocation.get(j)[1];

                int distance = Math.abs(hr - fr) + Math.abs(hc - fc);
                minDistance = Math.min(minDistance, distance);
            }
            sum += minDistance;
        }
        return sum;
    }
}
