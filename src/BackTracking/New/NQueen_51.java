package BackTracking.New;

import java.util.*;

public class NQueen_51 {
    List<List<String>> Result;
    LinkedList<String> Path;

    public List<List<String>> solveNQueens(int n) {
        Result = new ArrayList<>();
        Path = new LinkedList<>();
        BackTracking(n);
        return Result;
    }
    private void BackTracking(int n) {
        if (Path.size() == n) {
            Result.add(new ArrayList<>(Path));
        }
        for (int i = 0; i < n; i ++) {
            char[] aLine = new char[n];
            Arrays.fill(aLine, '.');
            aLine[i] = 'Q';
            if (isValidPosition(Path.size(), i, n, aLine)) {
                Path.addLast(new String(aLine));
                BackTracking(n);
                Path.removeLast();
            }
        }
    }
    private boolean isValidPosition(int x, int y,int n, char[] aLine) {
        char[][] Board = new char[n][n];
        for (int i = 0; i < Path.size(); i++) {
            char[] chars = Path.get(i).toCharArray();
            char[] chars1 = Board[i];
            for (int j = 0; j < chars1.length; j++) {
                chars1[j] = chars[j];
            }
        }
        for (int i = Path.size(); i < n; i++) {
            char[] chars = Board[i];
            if (i == Path.size()) {
                for (int j = 0; j < chars.length; j++) {
                    chars[j] = aLine[j];
                }
            }
            else {
                for (int j = 0; j < chars.length; j++) {
                    chars[j] = '.';
                }
            }
        }
        // 检查同一列,同一行肯定已经避免了
        for (int i = 0; i < Board.length; i++) {
            if (i != x && Board[i][y] == 'Q') {
                return false;
            }
        }
        // 检查45度斜线往上
        int xPos = x - 1, yPos = y + 1;
        while (xPos >= 0 && yPos < n) {
            if (Board[xPos][yPos] == 'Q') {
                return false;
            }
            xPos --;
            yPos ++;
        }
        // 检查45度斜线往下
        xPos = x + 1;
        yPos = y - 1;
        while (yPos >= 0 && xPos < n) {
            if (Board[xPos][yPos] == 'Q') {
                return false;
            }
            yPos --;
            xPos ++;
        }
        // 135度对角线往下
        xPos = x + 1;
        yPos = y + 1;
        while (yPos < n && xPos < n) {
            if (Board[xPos][yPos] == 'Q') {
                return false;
            }
            yPos ++;
            xPos ++;
        }
        // 135度对角线往下
        xPos = x - 1;
        yPos = y - 1;
        while (yPos >= 0 && xPos >= n) {
            if (Board[xPos][yPos] == 'Q') {
                return false;
            }
            yPos --;
            xPos --;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println((new NQueen_51()).solveNQueens(3));
    }
}
