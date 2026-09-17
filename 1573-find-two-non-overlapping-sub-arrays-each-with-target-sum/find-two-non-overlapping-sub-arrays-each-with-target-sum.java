class Solution {
    public int minSumOfLengths(int [] a, int t){
        int n = a.length, inf = n + 1, ans = inf , sum = 0, l = 0;
        int [] dp = new int[n + 1];
        java.util.Arrays.fill(dp , inf);
        for(int r = 0; r < n; r++){
            sum += a[r];
            while(sum > t) sum -= a[l++];
            dp[r + 1] = dp[r];
            if(sum == t){
                int len = r - l + 1;
                if(dp[l] < inf) ans = Math.min(ans, len + dp[l]);
                dp[r + 1] = Math.min(dp[r + 1], len);
            }
        }
        return ans == inf ? -1 : ans;
    }
}