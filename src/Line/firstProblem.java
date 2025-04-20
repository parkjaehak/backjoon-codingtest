package Line;

import java.util.*;

public class firstProblem {
    public static int solution(int[][] problems) {
        Set<Integer> scoreSet = new HashSet<>();
        scoreSet.add(0);

        for (int[] problem : problems) {
            int total = problem[0];
            int group = problem[1];
            List<List<Integer>> groupCases = getGroupCases(total, group);
            Set<Integer> nextScoreSet = new HashSet<>();

            for (List<Integer> groupScore : groupCases) {
                // 이 그룹 점수 분배로 만들 수 있는 모든 조합(부분합)
                Set<Integer> groupSums = getAllSubsetSums(groupScore);

                for (int prev : scoreSet) {
                    for (int add : groupSums) {
                        nextScoreSet.add(prev + add);
                    }
                }
            }
            scoreSet = nextScoreSet;
        }
        return scoreSet.size();
    }

    // 그룹 점수 분배 경우의 수 (비내림차순, 자연수)
    private static List<List<Integer>> getGroupCases(int total, int group) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), total, group, 1); // 자연수이므로 1부터 시작
        return result;
    }

    private static void backtrack(List<List<Integer>> result, List<Integer> temp, int remain, int group, int start) {
        if (temp.size() == group) {
            if (remain == 0) result.add(new ArrayList<>(temp));
            return;
        }
        for (int i = start; i <= remain - (group - temp.size() - 1); i++) {
            temp.add(i);
            backtrack(result, temp, remain - i, group, i); // 비내림차순
            temp.remove(temp.size() - 1);
        }
    }

    // 그룹 점수 분배로 만들 수 있는 부분합(0 포함)
    private static Set<Integer> getAllSubsetSums(List<Integer> groupScore) {
        Set<Integer> sums = new HashSet<>();
        sums.add(0);
        for (int score : groupScore) {
            Set<Integer> newSums = new HashSet<>();
            for (int prev : sums) {
                newSums.add(prev);
                newSums.add(prev + score);
            }
            sums = newSums;
        }
        return sums;
    }

    public static void main(String[] args) {
        int[][] input1 = {{6, 2}, {4, 2}};
        int[][] input2 = {{10, 1}, {1, 1}, {3, 2}};

        System.out.println(solution(input1));  // 출력: 11
        System.out.println(solution(input2));  // 출력: 10
    }
}
