package net.csthings.antreminder.websoc;

public final class WebSocUtils {
	public final static int NUM_CATEGORIES = 19;
	
	public enum Categories{
		TERM(0), DISPLAY(1), GE(2), DEPARTMENT(3), COURSE_NUMBER(4), COURSE_LEVEL(5), COURSE_CODE(6),
		INSTRUCTOR(7), COURSE_TITLE(8), COURSE_TYPE(9), UNITS(10), DAYS(11), START_TIME_AFTER(12), 
		END_TIME_BEFORE(13), MAX_CAPACITY(14), COURSES_FULL_OPTION(15), WEB_FONT_SIZE_PERCENTAGE(16), 
		CANCELLED_COURSES(17), MEETING_PLACE(18);

		private int code;
		
		Categories(int code){
			this.code = code;
		}
		
		public int getValue(){
			return code;
		}
	}
}
