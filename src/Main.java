import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 입력 받기
        long A = scanner.nextLong();
        long B = scanner.nextLong();
        long C = scanner.nextLong();

        // 결과 계산
        long result = powerMod(A, B, C);

        // 결과 출력
        System.out.println(result);
    }

    // A^B % C를 계산하는 메서드
    public static long powerMod(long A, long B, long C) {
        if (B == 0) {
            return 1; // A^0 = 1
        }

        long half = powerMod(A, B / 2, C);
        long result = (half * half) % C; // (A^(B/2) % C)^2 % C

        if (B % 2 == 1) {
            result = (result * A) % C; // B가 홀수일 경우 A를 한번 더 곱함
        }

        return result;
    }
}
