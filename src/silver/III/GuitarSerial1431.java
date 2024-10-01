package silver.III;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GuitarSerial1431 {
    static Map<Character, Integer> map = new HashMap<>();
    public GuitarSerial1431() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(bf.readLine());
        StringBuilder sb = new StringBuilder();

        String[] array = new String[input];
        for (int i = 0; i < input; i++) {
            array[i] = bf.readLine();
        }
        for (int i = 0; i < 10; i++) {
            map.put((char) (i + '0'), i); //ASCII 값
        }
        MyComparator myComparator = new MyComparator();

        Arrays.sort(array, myComparator);

        for (int i = 0; i < input; i++) {
            sb.append(array[i]).append("\n");
        }
        System.out.println(sb);
    }

    static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            //1. 길이가 짧은 것
            if (o1.length() != o2.length()) {
                return Integer.compare(o1.length(), o2.length()); //o1이 작으면 -1, o2가 작으면 1
            } else {
                int sum1 = 0; int sum2 = 0;

                for (int i = 0; i < o1.length(); i++) {
                    sum1 += map.getOrDefault(o1.charAt(i), 0); //i번째 위치에 있는 아스키 숫자에 대한 정수값을 가져온다.
                }
                for (int i = 0; i < o2.length(); i++) {
                    sum2 += map.getOrDefault(o2.charAt(i), 0);
                }
                //2. 숫자의 합이 작은 것
                if (sum1 != sum2) {
                    return Integer.compare(sum1, sum2); //sum1이 작으면 -1, sum2가 작으면 1
                }
            }
            //3. 사전 순(숫자가 알파벳보다 작음)
            return o1.compareTo(o2);
        }
    }
}
