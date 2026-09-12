import java.util.*;

class Solution {

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // arr[i] = {start, end, weight, originalIndex}
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        // Sort by end time, then start time
        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        // dp[i][j] = best list of indices using first i intervals
        // and selecting at most j intervals
        List<Integer>[][] dp = new ArrayList[n + 1][5];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= 4; j++) {
                dp[i][j] = new ArrayList<>();
            }
        }

        for (int i = 1; i <= n; i++) {

            // Don't take current interval
            for (int j = 1; j <= 4; j++) {
                dp[i][j] = new ArrayList<>(dp[i - 1][j]);
            }

            // Find last non-overlapping interval
            int prev = findPrevious(arr, i - 1);

            // Try taking current interval
            for (int j = 1; j <= 4; j++) {

                List<Integer> candidate =
                    new ArrayList<>(dp[prev + 1][j - 1]);

                candidate.add(arr[i - 1][3]);

                if (isBetter(candidate, dp[i][j], intervals)) {
                    dp[i][j] = candidate;
                }
            }
        }

        // Get answer
        List<Integer> answer = dp[n][4];

        Collections.sort(answer);

        int[] result = new int[answer.size()];

        for (int i = 0; i < answer.size(); i++) {
            result[i] = answer.get(i);
        }

        return result;
    }

    // Find the last interval whose end < current interval's start
    private int findPrevious(int[][] arr, int index) {

        int start = arr[index][0];

        int left = 0;
        int right = index - 1;
        int answer = -1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (arr[mid][1] < start) {
                answer = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return answer;
    }
    private boolean isBetter(
        List<Integer> candidate,
        List<Integer> current,
        List<List<Integer>> intervals
    ) {
        long candidateWeight = getWeight(candidate, intervals);
        long currentWeight = getWeight(current, intervals);
        if (candidateWeight != currentWeight) {
            return candidateWeight > currentWeight;
        }
        return lexicographicallySmaller(candidate, current);
    }
    private long getWeight(
        List<Integer> list,
        List<List<Integer>> intervals
    ) {
        long sum = 0;
        for (int index : list) {
            sum += intervals.get(index).get(2);
        }
        return sum;
    }
    private boolean lexicographicallySmaller(
        List<Integer> a,
        List<Integer> b
    ) {
        List<Integer> x = new ArrayList<>(a);
        List<Integer> y = new ArrayList<>(b);
        Collections.sort(x);
        Collections.sort(y);
        int size = Math.min(x.size(), y.size());
        for (int i = 0; i < size; i++) {
            if (!x.get(i).equals(y.get(i))) {
                return x.get(i) < y.get(i);
            }
        }
        return x.size() < y.size();
    }
}