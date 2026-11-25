import java.util.*;
class Solution {
    static final long MOD1 = 1000000007L;
    static final long MOD2 = 1000000009L;
    static final long BASE = 911382323L;
    long[] h1, h2, p1, p2;
    void build(int[] nums) {
        int n = nums.length;
        h1 = new long[n + 1];
        h2 = new long[n + 1];
        p1 = new long[n + 1];
        p2 = new long[n + 1];
        p1[0] = 1;
        p2[0] = 1;
        for (int i = 0; i < n; i++) {
            h1[i + 1] = (h1[i] * BASE + nums[i]) % MOD1;
            h2[i + 1] = (h2[i] * BASE + nums[i]) % MOD2;
            p1[i + 1] = (p1[i] * BASE) % MOD1;
            p2[i + 1] = (p2[i] * BASE) % MOD2;
        }
    }
    long getHash(int l, int r) {
        int len = r - l + 1;
        long a = (h1[r + 1] -
                h1[l] * p1[len] % MOD1 + MOD1) % MOD1;
        long b = (h2[r + 1] -
                h2[l] * p2[len] % MOD2 + MOD2) % MOD2;
        return (a << 32) | b;
    }
    boolean check(int[] nums, int len) {
        HashMap<Long, Integer> map = new HashMap<>();
        for (int i = 0; i + len <= nums.length; i++) {
            long hash = getHash(i, i + len - 1);

            map.put(hash, map.getOrDefault(hash, 0) + 1);
        }
        for (int count : map.values()) {
            if (count == 1) {
                return true;
            }
        }
        return false;
    }
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        build(nums);
        int low = 1, high = n;
        int ans = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (check(nums, mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return ans;
    }
}