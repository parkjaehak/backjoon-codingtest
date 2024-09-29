package silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CufOffTree2805 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] trees;
    static int N, M;
    static int high = Integer.MIN_VALUE;
    static int low = 0;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); //나무의 개수
        M = Integer.parseInt(st.nextToken()); //필요한 나무의 총 길이의 합

        //설정할 수 있는 높이의 최댓값
        trees = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int n = 0; n < N; n++) {
            trees[n] = Integer.parseInt(st.nextToken());
            high = Math.max(high, trees[n]); //높이가 최대인 나무를 찾는다.
        }

        int result = findMaxHeight();
        System.out.println(result);
    }

    private static int findMaxHeight() {
        //높이는 임의로 설정한다. --> 높이는 중간으로 선택한다. 이분탐색
        int answer = 0;
        while (true) {
            int mid = (low + high) / 2;

            if (calculate(mid)) {
                //더 높은 높이를 탐색해본다.
                answer = mid;
                low = mid + 1;
            } else {
                //더 낮은 높이를 탐색해본다.
                high = mid - 1;
            }
            if (low > high) {
                break;
            }
        }
        return answer;
    }

    private static boolean calculate(int mid) {
        //목표치 이상의 나무를 얻을 수 있는가?
        long total = 0;
        for (int i = 0; i < trees.length; i++) {
            if (trees[i] > mid) {
                total += (trees[i] - mid); //나무길이가 중간보다 큰 나무들에 대해 길이의 차의 합을 구한다.
            }
        }
        if (total >= M) {
            return true; //원하는 목표치 이상이면 true 반환한다.
        } else {
            return false;
        }
    }
}
