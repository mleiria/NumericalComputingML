/**
 *
 */
package pt.mleiria.machinelearning.preprocess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author manuel
 *
 */
public class ConvertToNumericDummy {

    private final List<String> lst;

    public ConvertToNumericDummy() {
        lst = new ArrayList<>();
    }

    /**
     *
     * @param realValue
     * @return
     */
    public double put(final String realValue) {
        if (lst.contains(realValue)) {
            return lst.indexOf(realValue);
        } else {
            lst.add(realValue);
            return lst.size() - 1;
        }
    }

    /**
     *
     * @param dummyValue
     * @return
     */
    public String getRealValue(final double dummyValue) {
        return lst.get((int) dummyValue);
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return lst.size();
    }

    /**
     *
     * @return 
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lst.size(); i++) {
            sb.append(i).append(":").append(lst.get(i)).append("\n");
        }
        return sb.toString();
    }

}
