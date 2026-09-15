class Solution {
    public int maxPalindromes(String s, int k) {
     int n = s.length();
     boolean [][]  palindrome =   new boolean[n][n];
     for(int i = 0; i < n; i++){
        palindrome[i][i] = true;
     } 
     for(int len = 2; len <= n; len++){
        for(int i = 0; i + len - 1< n; i++){
            int j = i + len - 1;
            if(s.charAt(i) == s.charAt(j)){
                if(len == 2){
                    palindrome [i][j] = true;
                } else {
                    palindrome [i][j] = palindrome[i + 1][j - 1];
                }
            }
        }
     }
     int [] dp = new int [n];
     for(int i = 0; i < n; i++){
        if(i > 0){
            dp[i] = dp[i - 1];
        }
        for(int j= 0; j <= i; j++){
            int length = i - j + 1;
            if(length >= k && palindrome[j][i]){
                int previous = (j == 0) ? 0 : dp[ j - 1];
                dp[i] = Math.max(dp[i] , previous + 1);
             }
        }
     }
     return dp[n - 1];
    }
}