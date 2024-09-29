package silver;

import java.io.*;
import java.util.StringTokenizer;

public class EgyptFraction4587 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();
    static int M, N;
    public static void main(String[] args) throws IOException {

        while (true) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken()); //분자
            N = Integer.parseInt(st.nextToken()); //분모
            if (M == 0 && N == 0) {
                break;
            }
            findEgyptianFraction(M, N);
            sb.append("\n");
        }
        bw.write(sb.toString());
        bw.flush();
    }

    private static void findEgyptianFraction(long m, long n) {
        while (m != 1) {
            int nextN = (int) Math.ceil((double) n / m); // 다음 단위 분수의 분모

            while (true) {
                // N과 nextN의 최소공배수를 구합니다.
                long gcd = getGcd(n, nextN);
                long lcm = n * nextN / gcd;

                long newM1 = (lcm / n) * m;
                long newM2 = lcm / nextN;
                long newM = newM1 - newM2;

                // 새로 나온 분자와 분모를 약분
                gcd = getGcd(newM, lcm);
                newM /= gcd;
                lcm /= gcd;

                if (lcm >= 1000000) {
                    nextN++; // 분모가 1,000,000보다 크면 nextN을 증가
                } else {
                    // 결과 출력
                    sb.append(nextN).append(" ");
                    n = lcm; // 업데이트
                    m = newM; // 업데이트
                    break;
                }
            }
        }
        // 마지막 단위 분수가 1인 경우
        sb.append(n).append(" ");
    }

    private static long getGcd(long a, long b) {
        while (b != 0) {
            long temp = a;
            a = b;
            b = temp % b;
        }
        return a;
    }
}
