package com.zhy.Util;

import android.app.Activity;
import android.content.SharedPreferences;

public class MySharedPreferences {
	
	public MySharedPreferences(Activity activity){
	}
	/**
	 * ��ȡ��Ӧ�ļ�ֵ
	 * @param key
	 * @return String
	 */
	public static int readMessage(Activity activity, String key, int value) {
		//��õ�ǰ��SharedPreferences����
		SharedPreferences message = activity.getPreferences(Activity.MODE_PRIVATE);
		//��ȡ��Ϣ
		int tmp = message.getInt(key, value);
		return tmp;
	}
	/**
	 * ����ֵ��д�������ļ�
	 * @param key
	 * @param value
	 */
	public static void writeMessage(Activity activity, String key, int value) {
		//����һ��SharedPreferences����
		SharedPreferences message = activity.getPreferences(0);
		//�༭SharedPreferences����
		SharedPreferences.Editor editor = message.edit();
		//����һ�����
		editor.putInt(key, value);
		//�ύ���
		editor.commit();
	}
	public static String readMessage(Activity activity, String key, String value) {
		SharedPreferences message = activity.getSharedPreferences("user", Activity.MODE_PRIVATE);
		String text = message.getString(key, value);
		return text;
	}
	public static void writeMessage(Activity activity, String key, String value) {
		SharedPreferences message = activity.getSharedPreferences("user", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = message.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
}
