package gregory.reading;

/**
 * Created by oracle on 16.01.2017.
 */
public class StrToInt {

    public static int stringToInt(String val, int defaultVal) {
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }
}
