package DynamicPlaning;

public class Fibonacci_509 {
    public int fib(int n) {
        int[] FibArr = new int[n + 1];
        FibArr[0] = 0;
        FibArr[1] = 1;
        for (int i = 2; i < n + 1; i ++) {
            FibArr[i] = FibArr[i - 1] + FibArr[i - 2];
        }
        return FibArr[n];
    }

    public static void main(String[] args) {
        (new Fibonacci_509()).fib(3);
    }
}
