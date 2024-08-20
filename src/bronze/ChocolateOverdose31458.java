package bronze;

import java.io.*;


public class ChocolateOverdose31458 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringBuilder sb = new StringBuilder();
    static int T;
    public static void main(String[] args) throws IOException {
        T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            String input = br.readLine();

            char lastC = input.charAt(input.length() - 1);
            int num;
            if (lastC== '!') {
                //마지막 문자에 느낌표가 있으면 뒤에는 볼필요도 없이 1
                num = 1;
            } else {
                num = Character.getNumericValue(lastC);
            }

            //앞에서부터 최초 숫자인덱스를 만날때까지 횟수를 카운팅한다.
            int count = 0; //앞쪽 느낌표 횟수
            int result = 0;
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '0' || input.charAt(i) == '1') {
                    if (count % 2 == 0) {
                        //짝수라면 숫자가 그대로 유지된다.
                        result = num;
                    } else {
                        //홀수라면 0은 1로 1은 0으로 바뀐다.
                        if (num == 0) {
                            result = 1;
                        }
                    }
                    //숫자라면 거기서 멈춘다.
                    break;
                } else {
                    count++;
                }
            }
            sb.append(result).append('\n');
        }
        bw.write(sb.toString());
        bw.flush();
    }
}
