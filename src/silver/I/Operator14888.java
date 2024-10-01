package silver.I;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 주어진 수의 순서는 바꿀 수 없음
 * 앞에서부터 순서대로 연산
 * 나누기는 몫만 취한다. --> '/'
 * 음수 / 양수 --> 양수 / 양수 한 뒤에 음수를 취한다.
 * <p>
 * 출력 : 최댓값, 최솟값
 */
public class Operator14888 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] operator = new int[4]; //덧셈, 뺄셈, 곱셈, 나눗셈 개수
    static int[] numbers;
    static int min = Integer.MAX_VALUE;
    static int max = Integer.MIN_VALUE;
    static int N;

    public Operator14888() throws IOException {
        N = Integer.parseInt(br.readLine());

        numbers = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int n = 0; n < N; n++) {
            numbers[n] = Integer.parseInt(st.nextToken()); //1. 숫자를 저장한다.
        }
        st = new StringTokenizer(br.readLine());
        for (int o = 0; o < 4; o++) {
            operator[o] = Integer.parseInt(st.nextToken()); //2. 연산자를 저장한다.
        }

        //연산자의 위치를 바꿔가며 연산을 진행한다.
        calculate(numbers[0], 1);

        //연산결과를 하나하나 비교하여 최댓값과 최솟값을 찾는다.
        System.out.println(max);
        System.out.println(min);
    }

    public static void calculate(int total, int index) {

        if (index == N) {
            max = Math.max(max, total);
            min = Math.min(min, total);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operator[i] > 0) {
                operator[i] -= 1; //해당 연산자를 사용한다.
                switch (i) {
                    case 0:
                        calculate(total + numbers[index], index+1);
                        break;
                    case 1:
                        calculate(total - numbers[index], index+1);
                        break;
                    case 2:
                        calculate(total * numbers[index], index+1);
                        break;
                    case 3:
                        calculate(total / numbers[index], index+1);
                        break;
                }
                operator[i] += 1;
            }
        }
    }
}
