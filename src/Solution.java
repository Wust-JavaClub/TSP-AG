import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author DURUII
 * @date 2024/1/28 13:51
 */
public class Solution {
    public static List<Integer> getMinLengthTour(Problem p) {
        List<Integer> tour = new ArrayList<>();

        // your implementation here...
        for (int i = 1; i <= p.getDimension(); i++) {
            tour.add(i);
        }

        return tour;
    }
}
