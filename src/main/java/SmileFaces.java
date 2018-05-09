import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SmileFaces {

    public static final Pattern PATTERN = Pattern.compile("[:;]{1}[-~]?[)D]{1}");

    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add(":)"); a.add("XD"); a.add(":0}"); a.add("x:-"); a.add("):-"); a.add("D:");
        System.out.println(countSmileys(a));
    }

    public static int countSmileys(List<String> arr) {
        return (int)arr.stream().filter(smile -> PATTERN.matcher(smile).matches()).count();
    }

}
