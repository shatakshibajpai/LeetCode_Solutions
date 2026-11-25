import java.util.*;
class Solution {
    List<String> ans = new ArrayList<>();
    public List<String> addOperators(String num, int target) {
        dfs(num, target, 0, 0, 0, new StringBuilder());
        return ans;
    }
    void dfs(String num, int target, int index,
             long value, long prev, StringBuilder path) {
        if (index == num.length()) {
            if (value == target) {
                ans.add(path.toString());
            }
            return;
        }
        long curr = 0;
        int len = path.length();
        for (int i = index; i < num.length(); i++) {
            if (i > index && num.charAt(index) == '0')
                break;
            curr = curr * 10 + (num.charAt(i) - '0');
            String part = num.substring(index, i + 1);
            if (index == 0) {
                path.append(part);
                dfs(num, target, i + 1,
                    curr, curr, path);
                path.setLength(len);
            } 
            else {
                path.append("+").append(part);
                dfs(num, target, i + 1,
                    value + curr, curr, path);
                path.setLength(len);
                path.append("-").append(part);
                dfs(num, target, i + 1,
                    value - curr, -curr, path);
                path.setLength(len);
                path.append("*").append(part);
                dfs(num, target, i + 1,
                    value - prev + prev * curr,
                    prev * curr, path);
                path.setLength(len);
            }
        }
    }
}
