package com.srdt.myguruji.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.srdt.myguruji.repository.AutoSeqRepository;

public class Generation {
	
	 private static String dtpatern = "yyyy-MM-dd HH:mm:ss.SSS";
	 
	 
	 public static String generatePassword(int length) {
		 
		    String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	        StringBuilder returnValue = new StringBuilder(length);

	        for (int i = 0; i < length; i++) {
	            returnValue.append(ALPHABET.charAt(new Random().nextInt(ALPHABET.length())));
	        }
	        return new String(returnValue);
	    }
	 
	 public static String generatePassword() 
	 {
		 int length = 10;
		 String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	     StringBuilder returnValue = new StringBuilder(length);

	     for (int i = 0; i < length; i++) {
	         returnValue.append(ALPHABET.charAt(new Random().nextInt(ALPHABET.length())));
	     }
	     return new String(returnValue);
	 }
	 
	 
	public static Date getCurrentDate()
	{
		String dtstr = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dtpatern));
		DateFormat dateFormat = new SimpleDateFormat(dtpatern);
		Date date = null;
		try {
			date = dateFormat.parse(dtstr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;		
	}
	 public static String getDefaultPwd()
	 {
		 return "Qwe@123";
	 }
	 public static String getSecretKey()
	 {
		 return "San@123456789012";
	 }
	 
	 public static String getTerm()
	 {
		 Date date = new Date();
		 LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		 int month = localDate.getMonthValue();
		 if(month >= 1 && month <= 6)
		 {
			 return "01";
		 }
		 else
		 {
			 return "02";
		 }
	 }
	 
	 public static String getCurrentYear()
	 {
		 return Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
	 }
	 
	 public static long getTemplateSeq(AutoSeqRepository repository )
	 {		 
		 long seq = repository.findAutoSeqById().getTmplSeq();
		 repository.SetTmplSeq();
		 return seq;
	 }
	 
	 public static long getAssessmentSeq(AutoSeqRepository repository )
	 {
		 long seq = repository.findAutoSeqById().getAssessSeq();
		 repository.SetAssessSeq();
		 return seq;
	 }
	 
	 public static Date toDate(String date,String patern) throws ParseException
	 {
		 DateFormat dateFormat = new SimpleDateFormat(patern);
		 return dateFormat.parse(date);
		 
	 }
	 
	public static String dateAsString(Date date)
	{
		DateFormat df = new SimpleDateFormat(dtpatern);		
		return df.format(date);
	}
}

