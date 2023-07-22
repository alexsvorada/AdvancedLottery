package com.gmail.ianlim224.advancedlottery.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gmail.ianlim224.advancedlottery.AdvancedLottery;

public class TimeParser {
	private final String input;
	private int seconds;
	private int minutes;
	private int hours;
	private int days;
	private boolean isValid;
	private final AdvancedLottery plugin;
	
	private static final Pattern TIME_PATTERN = Pattern.compile("[1-9][0-9]*[smhd]");
	private static final Pattern NUMBER_PATTERN = Pattern.compile("[1-9][0-9]*");
	private static final Pattern ALPHABET_PATTERN = Pattern.compile("[smhd]");
	
	public TimeParser(String input, AdvancedLottery plugin) {
		this.input = input.toLowerCase().replaceAll("\\s+", "");
		this.seconds = 0;
		this.minutes = 0;
		this.hours = 0;
		this.days = 0;
		this.plugin = plugin;
		parse();
	}

	private void parse() {
		Matcher matcher = TIME_PATTERN.matcher(input);

		if (!matcher.find()) {
			isValid = false;
			return;
		}

		// reset previous find
		matcher.reset();

		while (matcher.find()) {
			String subString = matcher.group();
			Matcher number = NUMBER_PATTERN.matcher(subString);
			Matcher alphabet = ALPHABET_PATTERN.matcher(subString);
			
			int i = 0;
			if (number.find()) {
				i = Integer.parseInt(number.group());
			} else {
				throw new IllegalStateException("Cannot find number value?!");
			}
			
			String time = null;
			if(alphabet.find()) {
				time = alphabet.group();
			} else {
				throw new IllegalStateException("Cannot find time value?!");
			}

			switch (time.charAt(0)) {
			case 's':
				seconds += i;
				break;
			case 'm':
				minutes += i;
				break;
			case 'h':
				hours += i;
				break;
			case 'd':
				days += i;
				break;
			default:
				throw new IllegalStateException("Invalid time parsed");
			}
		}

		isValid = true;
	}
	
	public boolean isValid() {
		return isValid;
	}

	public Time getTime() {
		return new Time(days, hours, minutes, seconds, false, plugin);
	}
	
	@Override
	public String toString() {
		return input;
	}
}
