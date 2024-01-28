/**
 * Created with IntelliJ IDEA.
 *
 * @author DURUII
 * @date 2024/1/28 14:24
 */
public class Main {
    private static final String[] BENCHMARKS = new String[]{
            "a280",
    };

    private static final int[] VALS = new int[]{
            2579,
    };

    public static void main(String[] args) {
        for (int i = 0; i < BENCHMARKS.length; i++) {
            Problem p = new Problem(BENCHMARKS[i]);
            int length = p.calculateLength(Solution.getMinLengthTour(p));
            System.out.println("Length: " + length +
                    " Error Rate: " + 1.0 * (length - VALS[i]) / VALS[i]);
        }
    }
}
