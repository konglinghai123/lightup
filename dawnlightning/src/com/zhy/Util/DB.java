package com.zhy.Util;

import java.util.ArrayList;
import java.util.List;

import com.zhy.Bean.ConsultMessageBean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DB extends SQLiteOpenHelper{
    // ��ݿ���Ƴ���
    private static final String DATABASE_NAME = "comsult.db";
    // ��ݿ�汾����
    private static final int DATABASE_VERSION = 2;
    // ����Ƴ���
    public static final String TABLES_NAME = "newsTbl";
	// ���췽��
	public DB(Context context) {
		// ������ݿ�
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("creat DB");
	}

	// ����ʱ���ã�����ݿ�����򲻵���
	public void onCreate(SQLiteDatabase db) {
		System.out.println("onCreat DB");
	
		db.execSQL("CREATE TABLE " + TABLES_NAME + " ("
                + "_ID" + " INTEGER PRIMARY KEY,"
				+ "title" +  " TEXT,"
				+ "content" +  " TEXT,"
                + "date" + " TEXT,"
                + "img" + " TEXT,"
                + "replynum" + " TEXT,"
                + "viewnum" + " TEXT,"
                + "bwztid" + " TEXT,"
                + "messagetype" + " TEXT,"
                + "status" + " TEXT,"
                + "uid" +  " TEXT"
                + ");");
	}

	// �汾����ʱ����
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ɾ���
		System.out.println("delete DB");
		db.execSQL("DROP TABLE IF EXISTS newsTbl");	
        onCreate(db);	//�����±�
	}
	
	public void insert(List<ConsultMessageBean> list){
		SQLiteDatabase db = this.getWritableDatabase();
		for(ConsultMessageBean item : list){
			ContentValues values = new ContentValues();
			values.put("title", item.getSubject());
			values.put("content", item.getMessage());
			values.put("date", item.getDateline());
			values.put("img", item.getAvatar_url());
			values.put("uid", item.getUid());
			values.put("replynum", item.getReplynum());
			values.put("viewnum", item.getViewnum());
			values.put("messagetype", item.messagetype);
			values.put("status", item.getStatus());
			values.put("bwztid", item.getBwztid());
			db.insert("newsTbl", null, values);
		}
	}
	public void delete(String newsType){
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from newsTbl where  messagetype= '" + newsType + "'");
	}
	public List<ConsultMessageBean> query(String messagetype){
		List<ConsultMessageBean> list = new ArrayList<ConsultMessageBean>();
		SQLiteDatabase db = this.getReadableDatabase();
		String SQL = "select * from newsTbl where messagetype = '" +messagetype + "'";
		Cursor cursor = db.rawQuery(SQL, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
	        do{
	        	ConsultMessageBean item = new ConsultMessageBean();
	        	item.setSubject(cursor.getString(1));
	        	item.setMessage(cursor.getString(2));
	        	item.dateline=cursor.getString(3);
	        	item.setAvatar_url(cursor.getString(4));
	        	item.setReplynum(cursor.getString(5));
	        	item.setViewnum(cursor.getString(6));
	        	item.setBwztid(cursor.getString(7));
	        	item.setStatus(cursor.getString(9));
	        	item.setUid(cursor.getString(10));
	        	list.add(item);
	        }while(cursor.moveToNext());
        }
        cursor.close();
        return list;
	}
}
	
