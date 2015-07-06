package org.beiwe.app.survey;

import org.beiwe.app.BackgroundProcess;
import org.json.JSONException;
import org.json.JSONObject;

/**Some handy functions for scheduling surveys.
 * @author Josh Zagorsky*/

//TODO: Eli. Rewrite day of week logic (looks like this is a rewrite of the entire class...) to grab a list from the json and parse it (possibly just pass it directly, buuuut that seems unsafe.  validate input at least.)  
//TODO: Eli. document.
public class SurveyScheduler {
	
	public static void scheduleSurvey(String jsonSurveyString) {
		
		int hour = hourOfDayToAskSurvey(jsonSurveyString);

		if (dayOfWeekToAskSurvey(jsonSurveyString) == -1) {
			// Schedule a daily survey
			BackgroundProcess.setDailySurvey(hour);
		}
		else {
			// Schedule a weekly survey
			int dayOfWeek = dayOfWeekToAskSurvey(jsonSurveyString);
			BackgroundProcess.runWeeklySurveyStart(hour, dayOfWeek);
		}
	}
	
	
	private static int hourOfDayToAskSurvey(String jsonSurveyString) {
		try {
			JSONObject surveyObject = new JSONObject(jsonSurveyString);
			return surveyObject.getInt("hour_of_day");
		} catch (JSONException e) {
			// If JSON parsing fails, schedule the survey for 19:00 (i.e., 7:00 p.m.)
			return 19;
		}
	}
	
	
	private static int dayOfWeekToAskSurvey(String jsonSurveyString) {
		try {
			JSONObject surveyObject = new JSONObject(jsonSurveyString);
			return surveyObject.getInt("day_of_week");
		} catch (JSONException e) {
			// If attribute "day_of_week" doesn't exist, it's a daily survey; return -1
			return -1;
		}
	}
	
}
