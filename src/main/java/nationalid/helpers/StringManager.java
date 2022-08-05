package nationalid.helpers;

/**
 * String Manager class wraps static methods for widely used algorythms for
 * Strings
 */
public class StringManager {

    /**
     * Wrapper for String.substring() function
     * by making sure that the provided integer values
     * do not cause a runtime exception
     * 
     * @param target string to be substringed
     * @param from   index of symbol to start the substring
     * @param to     index of symbol before which the substring ends
     * @return substring based on values:
     *         <ul>
     *         <li>if from is out of bounds, empty String is
     *         returned;</li>
     *         <li>if to is out of bounds, the substring goes till the end of
     *         string;</li>
     *         <li>Otherwise, return defined substring</li>
     *         </ul>
     */
    public static String SafeSubstring(String target, int from, int to) {
        if (from > target.length()) {
            return "";
        }

        if (to > target.length()) {
            to = target.length();
        }

        return target.substring(from, to);

    }

}
