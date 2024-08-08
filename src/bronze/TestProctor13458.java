package bronze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * i번 시험장에 있는 응시자 수 Ai
 * 총감독 : 한 시험장에 B명 감시, 한명만
 * 부감독 : 한 시험장에 C명 감시, 여러명 가능
 * <p>
 * 필요한 감독관의 최솟값
 */
public class TestProctor13458 {
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] room;
    static int N, B, C;
    static long count = 0; //최악의 경우 1조, int는 최대 20억이다.
    public TestProctor13458() throws IOException {
        N = Integer.parseInt(bf.readLine());

        room = new int[N]; //시험장의 개수
        st = new StringTokenizer(bf.readLine());
        for (int n = 0; n < N; n++) {
            room[n] = Integer.parseInt(st.nextToken()); //한 시험장의 응시생 수를 저장한다.
        }

        st = new StringTokenizer(bf.readLine());
        B = Integer.parseInt(st.nextToken()); //총감독관이 감시가능한 응시생 수
        C = Integer.parseInt(st.nextToken()); //부감독관이 감시가능한 응시생 수

        for (int i = 0; i < N; i++) {
            room[i] -= B;
            count += 1; //총감독관 1명 추가}
            if (room[i] <= 0) {
                //총감독관 만으로 관리가능하면 부감독관 필요가 없다.
                continue;
            }
            count += room[i] / C; //부감독관 몫의 값 만큼 추가
            if (room[i] % C != 0) {
                count += 1;
            }
        }
        System.out.println(count);
    }
}
