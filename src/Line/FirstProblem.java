package Line;

import java.util.*;

public class FirstProblem {
    // 문제별 그룹 점수 분배 경우의 수를 모두 구한다.
    private static List<List<Integer>> getGroupCases(int total, int group) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(result, new ArrayList<>(), total, group, 1);
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
            Set<Integer> newSums = new HashSet<>(sums);
            for (int prev : sums) {
                newSums.add(prev + score);
            }
            sums = newSums;
        }
        return sums;
    }

    // 모든 문제별 그룹 분배 조합을 product로 순회
    public static int solution(int[][] problems) {
        List<List<Set<Integer>>> allProblemScores = new ArrayList<>();
        for (int[] problem : problems) {
            int total = problem[0];
            int group = problem[1];
            List<List<Integer>> groupCases = getGroupCases(total, group);
            List<Set<Integer>> groupSumsList = new ArrayList<>();
            for (List<Integer> groupScore : groupCases) {
                groupSumsList.add(getAllSubsetSums(groupScore));
            }
            allProblemScores.add(groupSumsList);
        }

        // 모든 문제별 그룹 분배 조합(product) 순회
        int n = problems.length;
        int[] idx = new int[n];
        int[] sizes = new int[n];
        for (int i = 0; i < n; i++) sizes[i] = allProblemScores.get(i).size();

        int maxCount = 0;
        while (true) {
            // 현재 조합에서 가능한 점수 집합 누적
            Set<Integer> scoreSet = new HashSet<>();
            scoreSet.add(0);
            for (int i = 0; i < n; i++) {
                Set<Integer> groupSums = allProblemScores.get(i).get(idx[i]);
                Set<Integer> nextScoreSet = new HashSet<>();
                for (int prev : scoreSet) {
                    for (int add : groupSums) {
                        nextScoreSet.add(prev + add);
                    }
                }
                scoreSet = nextScoreSet;
            }
            maxCount = Math.max(maxCount, scoreSet.size());

            // 다음 조합으로 이동
            int pos = n - 1;
            while (pos >= 0) {
                idx[pos]++;
                if (idx[pos] < sizes[pos]) break;
                idx[pos] = 0;
                pos--;
            }
            if (pos < 0) break;
        }
        return maxCount;
    }

    public static void main(String[] args) {
        int[][] input1 = {{6, 2}, {4, 2}};
        int[][] input2 = {{10, 1}, {1, 1}, {3, 2}};

        System.out.println(solution(input1));  // 출력: 11
        System.out.println(solution(input2));  // 출력: 10
    }
}
