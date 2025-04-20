package Line;

import java.util.*;

public class SecondProblem {
    public static int solution(String[] code) {
        Map<String, Integer> patternCount = new HashMap<>();

        for (String c : code) {
            StringBuilder pattern = new StringBuilder();

            for (char ch : c.toCharArray()) {
                if (Character.isLetter(ch)) {
                    pattern.append(ch);
                } else {
                    pattern.append("*");  // 숫자는 * 로 표현
                }
            }

            patternCount.put(pattern.toString(), patternCount.getOrDefault(pattern.toString(), 0) + 1);
        }

        int modifyCount = 0;
        for (int count : patternCount.values()) {
            if (count > 1) {
                modifyCount += count - 1;
            }
        }

        return modifyCount;
    }

    public static void main(String[] args) {
        String[] input1 = {"AAA057", "AAA031", "BBB777"};// 출력값 1
        String[] input2 = {"A01", "A22", "A23", "43A", "BB1", "BB2"}; // 출력값 3
        String[] input3 = {"1A1AA", "2B2BB", "3C3CC", "4D4DD", "56789"};// 출력값 0

        System.out.println(solution(input1));  // 출력: 1
        System.out.println(solution(input2));  // 출력: 3
        System.out.println(solution(input3));  // 출력: 0
    }
}
