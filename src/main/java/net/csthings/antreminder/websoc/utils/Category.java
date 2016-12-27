package net.csthings.antreminder.websoc.utils;

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
