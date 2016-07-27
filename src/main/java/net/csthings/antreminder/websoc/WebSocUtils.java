package net.csthings.antreminder.websoc;

import java.util.ArrayList;
import java.util.List;

import net.csthings.common.utils.Pair;

public final class WebSocUtils {
    public final static int NUM_CATEGORIES = 19;

    public enum Category {
        TERM(1), DISPLAY(2), GE(3), DEPARTMENT(4), COURSE_NUMBER(5), COURSE_LEVEL(6), COURSE_CODE(7), INSTRUCTOR(
                8), COURSE_TITLE(9), COURSE_TYPE(10), UNITS(11), DAYS(12), START_TIME_AFTER(13), END_TIME_BEFORE(
                        14), MAX_CAPACITY(15), COURSES_FULL_OPTION(
                                16), WEB_FONT_SIZE_PERCENTAGE(17), CANCELLED_COURSES(18), MEETING_PLACE(19);

        private int code;

        Category(int code) {
            this.code = code;
        }

        public int getValue() {
            return code;
        }
    }

    public static Pair<String, String> parseDeptElement(String dept) {
        String[] result = dept.split("\\.");
        return new Pair<String, String>(result[0], result[result.length - 1]);
    }

    public static List<Pair<String, String>> parseDeptElement(List<String> dept) {
        List<Pair<String, String>> result = new ArrayList<>();
        for (int i = 1; i < dept.size(); ++i) {
          result.add(parseDeptElement(dept.get(i)));
        }
        return result;

    }
}
