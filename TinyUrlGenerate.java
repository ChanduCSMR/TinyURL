package com.tinyurl.demo.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TinyUrlGenerate {

	public String charset = "abcdefghijklmnopqrestuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public final int base = charset.length();
	private AtomicInteger counter = new AtomicInteger(10);
	public Map<Long, String> map = new HashMap<>();

	public String generateUrl(String url) {
		final long nextnumber = getNextNumber();
		String tinyUrl = convertAndGetBase62Code(nextnumber);
		map.put(nextnumber, url);
		return tinyUrl;
	}

	public String reverseTinyurl(String tinyurl) {
		long tinyUrlId = convertToBase10(tinyurl);
		return map.get(tinyUrlId);
	}

	public long convertToBase10(String tinyurlcode) {
		long nBase10 = 0;
		char[] chars = new StringBuilder(tinyurlcode).toString().toCharArray();
		for (int i = chars.length - 1; i >= 0; i--) {
			int index = charset.indexOf(chars[i]);
			nBase10 += index * (long) Math.pow(base, i);
		}
		return nBase10;

	}

	private String convertAndGetBase62Code(long number) {
		StringBuffer sb = new StringBuffer();
		while (number > 0) {

			int remainder = (int) (number % base);
			sb.append(charset.charAt(remainder));
			number = number / base;
		}
		return sb.toString();
	}

	private long getNextNumber() {
		int countervalue = counter.incrementAndGet();
		long systemtime = Long.valueOf("" + countervalue + System.currentTimeMillis());
		return systemtime;
	}

	public static void main(String[] args) {
		TinyUrlGenerate d = new TinyUrlGenerate();
		String tinyUrl = d.generateUrl("http://www.google.com");
		System.out.println("1 : " + tinyUrl);
		
		String tinyUrl1 = d.generateUrl("http://www.google.com");
		System.out.println("1 : " + tinyUrl1);
		
		String url = d.reverseTinyurl(tinyUrl);
		System.out.println("2 : " + url);
	}

}
