package com.mee.sangsil.common;

import java.util.Date;
import java.util.TimerTask;
public class DaylySearch extends TimerTask {

	 @Override
	 public void run() {
	  System.out.println("dailySearch!:"+ new Date());
	 }
	 
	}
