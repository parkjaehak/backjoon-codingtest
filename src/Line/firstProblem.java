package Line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//조합 + dfs
public class firstProblem {

    public static int maxUniqueScores(int[][] problems) {
        List<List<int[]>> allDistributions = new ArrayList<>();

        for (int[] problem : problems) {
            int total = problem[0]; //테스트 케이스 그룹 점수
            int groupCount = problem[1]; //그룹 당 개수 (1 or 2)
            List<int[]> distributions = new ArrayList<>();

            if (groupCount == 1) {
                distributions.add(new int[]{total});  // 그룹 하나
            } else {
                for (int i = 1; i < total; i++) {
                    distributions.add(new int[]{i, total - i});  // 두 그룹 자연수 분배
                }
            }
            allDistributions.add(distributions);
        }

        int[] maxCount = {0};
        dfs(allDistributions, 0, new ArrayList<>(), maxCount);
        return maxCount[0];
    }

    private static void dfs(List<List<int[]>> allDistributions, int depth, List<int[]> selected, int[] maxCount) {
        if (depth == allDistributions.size()) {
            Set<Integer> scoreSet = new HashSet<>();
            scoreSet.add(0);

            for (int[] groupScores : selected) {

                Set<Integer> nextSet = new HashSet<>();
                for (int existing : scoreSet) {
                    int groupNum = groupScores.length;
                    int totalCombinations = 1 << groupNum;  // 그룹마다 맞춤 여부 비트마스크

                    for (int mask = 0; mask < totalCombinations; mask++) {
                        int addedScore = 0;
                        for (int i = 0; i < groupNum; i++) {
                            if ((mask & (1 << i)) != 0) { //0이 아니면 더한다
                                addedScore += groupScores[i];
                            }
                        }
                        nextSet.add(existing + addedScore);

                    }
                }
                scoreSet = nextSet;
            }

            maxCount[0] = Math.max(maxCount[0], scoreSet.size());
            return;
        }

        for (int[] distribution : allDistributions.get(depth)) {
            selected.add(distribution);
            dfs(allDistributions, depth + 1, selected, maxCount);
            selected.remove(selected.size() - 1);
        }
    }

    public static void main(String[] args) {
        int[][] input1 = {{6, 2}, {4, 2}};
        int[][] input2 = {{10, 1}, {1, 1}, {3, 2}};

        System.out.println(maxUniqueScores(input1));  // 출력: 11
        System.out.println(maxUniqueScores(input2));  // 출력: 10
    }
}
