package com.ubs.opsit.interviews;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that converts <a href="https://en.wikipedia.org/wiki/24-hour_clock">24-hour clock (HH:MM:SS) time</a>
 *  to <a href="https://en.wikipedia.org/wiki/Mengenlehreuhr">berlin-clock time</a>.<br>
 * Refer examples here: {@link src/test/resources/stories/berlin-clock.story}
 * 
 * @author sukrut
 */
public class BerlinClockTimeConverter implements TimeConverter {
	
	private static String MIDNIGHT_24 = "24:00:00";
	//Pattern to validate input 24 hour string.
	private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
	private Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
	private Matcher matcher;
	
	/**
	 * TODO:
	 * JBehave is parsing Multi-line string with escape characters \r\n 
	 * Suspecting OS specific problem
	 */
	private static String NEWLINE = "\r\n";
	private static char LAMP_RED = 'R';
	private static char LAMP_YELLOW = 'Y';
	
	/* (non-Javadoc)
	 * @see com.ubs.opsit.interviews.TimeConverter#convertTime(java.lang.String)
	 */
	@Override
	public String convertTime(String aTime) {
		StringBuilder convertedTime = new StringBuilder();
		if(isValidTime(aTime)) {
			String[] timeParts = aTime.split(":");
			convertedTime.append(getSeconds(Integer.parseInt(timeParts[2]))).append(NEWLINE)
						.append(getTopHours(Integer.parseInt(timeParts[0]))).append(NEWLINE)
						.append(getBottomHours(Integer.parseInt(timeParts[0]))).append(NEWLINE)
						.append(getTopMinutes(Integer.parseInt(timeParts[1]))).append(NEWLINE)
						.append(getBottomMinutes(Integer.parseInt(timeParts[1])));
		}
		//convertedTime = "Y\r\nOOOO\r\nOOOO\r\nOOOOOOOOOOO\r\nOOOO";
		return convertedTime.toString();
	}
	
	protected String getTopHours(int num) {
		StringBuilder lamps = new StringBuilder("OOOO");//Default all lights to OFF
		int countOfONLamps = num / 5;
		if(countOfONLamps > 0) {
			for(int i=0; i<countOfONLamps; i++) {
				lamps.setCharAt(i, LAMP_RED);
			}
		}
		return lamps.toString();
	}
	
	protected String getBottomHours(int num) {
		StringBuilder lamps = new StringBuilder("OOOO");//Default all lights to OFF
		int countOfONLamps = num % 5;
		if(countOfONLamps > 0) {
			for(int i=0; i<countOfONLamps; i++) {
				lamps.setCharAt(i, LAMP_RED);
			}
		}
		return lamps.toString();
	}
	
	protected String getTopMinutes(int num) {
		StringBuilder lamps = new StringBuilder("OOOOOOOOOOO");//Default all lights to OFF
		int countOfONLamps = num / 5;
		if(countOfONLamps > 0) {
			for(int i=0; i<countOfONLamps; i++) {
				if(i==2||i==5||i==8) {
					lamps.setCharAt(i, LAMP_RED);
				} else {
					lamps.setCharAt(i, LAMP_YELLOW);
				}
			}
		}
		return lamps.toString();
	}
	
	protected String getBottomMinutes(int num) {
		StringBuilder lamps = new StringBuilder("OOOO");//Default all lights to OFF
		int countOfONLamps = num % 5;
		if(countOfONLamps > 0) {
			for(int i=0; i<countOfONLamps; i++) {
				lamps.setCharAt(i, LAMP_YELLOW);
			}
		}
		return lamps.toString();
	}
	
	protected String getSeconds(int num) {
		return num %2 == 0 ? "Y" : "O";
	}
	
	/**
	   * Validates <a href="https://en.wikipedia.org/wiki/24-hour_clock">24-hour clock (HH:MM:SS) time</a>
	   * @param time time for validation
	   * @return boolean
	   */
	  public boolean isValidTime(final String time){
		  if(MIDNIGHT_24.equals(time)) {
			  return true;
		  }
		  matcher = pattern.matcher(time);
		  return matcher.matches();
	  }

}
