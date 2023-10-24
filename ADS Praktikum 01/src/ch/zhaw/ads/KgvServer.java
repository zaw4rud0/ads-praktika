package ch.zhaw.ads;

public class KgvServer implements CommandExecutor {

    @Override
    public String execute(String s) {
        String[] numbers = s.split("[ ,]+");
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1]);
        return Integer.toString(kgv(a, b));
    }

    int kgv(int a, int b) {
        int ggT = ggT(a, b);
        return Math.abs(a * b) / ggT;
    }

    private int ggT(int a, int b) {
        if (a == 0) return b;
        while (b != 0) {
            int c = a % b;
            a = b;
            b = c;
        }
        return a;
    }
}