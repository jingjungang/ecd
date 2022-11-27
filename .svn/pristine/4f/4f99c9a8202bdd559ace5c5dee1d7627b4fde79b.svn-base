package com.ukang.clinic.db;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

public class DBAdapater {
	static final String DATABASE_NAME = "DocDatabase.db";
	static final int DATABASE_VERSION = 1;
	static final String DATA = "data/temp";
	static final String DATA2 = "data/temp2";
	static final String DATA3 = "data/temp3";
	static final String SOFTWARE = "KidneytransplantDoc";

	public SQLiteDatabase db;
	Context context;
	myDbHelper dbHelper;

	private class myDbHelper extends SQLiteOpenHelper {
		Context mContext;

		public myDbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			this.mContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			Log.w("onCreate", "datebase onCreate");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w("update", "datebase update");
		}

	}

	public void initTable() {
		InputStream in = null;
		BufferedReader reader = null;
		try {
			in = context.getAssets().open("mytable.sql");
			reader = new BufferedReader(new InputStreamReader(in));
			String sqlUpdate = null;
			while (reader != null && (sqlUpdate = reader.readLine()) != null
					&& !sqlUpdate.equals("")) {
				try {
					if (!TextUtils.isEmpty(sqlUpdate)) {
						db.execSQL(sqlUpdate);
						System.out.println(sqlUpdate.toString());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			reader.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveToFile(String fileName, InputStream in) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		int BUFFER_SIZE = 1024;
		byte[] buf = new byte[BUFFER_SIZE];
		int size = 0;
		bis = new BufferedInputStream(in);
		fos = new FileOutputStream(fileName);
		while ((size = bis.read(buf)) != -1) {
			fos.write(buf, 0, size);
		}
		fos.close();
		bis.close();
	}

	public DBAdapater(Context con) {
		this.context = con;
		dbHelper = new myDbHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public DBAdapater open() {
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		// TODO Auto-generated method stub
		db.close();
	}

	public boolean exeJB(String sqlStr) {
		boolean flag;
		db.beginTransaction();
		try {
			String[] sqlSS = sqlStr.split(";#");
			for (int i = 0; i < sqlSS.length; i++) {
				db.execSQL(sqlSS[i].replace(",)", ",'')") + ";");
			}
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
			e.printStackTrace();
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		return flag;
	}

	public Cursor getEntry(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		Cursor myc = db.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);
		return myc;
	}

	public boolean insertArray(String table, ContentValues[] cv) {
		boolean flag;
		db.beginTransaction();
		try {
			for (int i = 0; i < cv.length; i++) {
				db.insert(table, null, cv[i]);
			}
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		return flag;
	}

	public boolean insert(String table, ContentValues cv) {
		boolean flag;
		try {
			db.insert(table, null, cv);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
		}
		return flag;
	}

	/*
	 * table������ cv[]��Ҫ���µ�ContentValues���� selection����ʾWHERE֮����������
	 * selectionArgs��ռλ��
	 */
	public boolean updates(String table, ContentValues[] cv,
			String[] selection, String[] selectionArgs) {
		boolean falg;
		db.beginTransaction();
		try {
			for (int i = 0; i < cv.length; i++) {
				db.update(table, cv[i], selection[i], selectionArgs);
			}
			falg = true;
		} catch (Exception e) {
			// TODO: handle exception
			falg = false;
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		return falg;
	}

	public void exeSql(String sql) {
		// TODO Auto-generated method stub
		db.execSQL(sql);
	}

	public void update(String table, ContentValues cv, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		db.update(table, cv, selection, selectionArgs);
	}

	public Cursor rawQuery(String sql, String[] str) {
		return db.rawQuery(sql, str);
	}

	public boolean removeEntry(String table, String selection,
			String[] selectionArgs) {
		return db.delete(table, selection, selectionArgs) > 0;
	}

	public Cursor getEntry1(String sql, String[] ss) {
		return db.rawQuery(sql, ss);
	}

	public void begin() {
		db.beginTransaction();
	}

	public void end() {
		db.setTransactionSuccessful();
		db.endTransaction();
	}
}
