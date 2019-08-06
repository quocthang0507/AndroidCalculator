package vn.edu.itdlu.a1610207.calculator.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
	private static final String TAG = "1610207_Calculator";
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "Calculations";
	private static final String TABLE_NAME = "Calculation";
	private static final String COL_ID = "ID";
	private static final String COL_EXP = "Expression";
	private static final String COL_RESULT = "Result";
	
	public Database(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Database is being created...");
		String creationScript = "CREATE TABLE " + TABLE_NAME + " ( " +
				COL_ID + " INTEGER PRIMARY KEY, " +
				COL_EXP + " TEXT, " +
				COL_RESULT + " TEXT ); ";
		db.execSQL(creationScript);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion) {
		Log.i(TAG, "Database is being upgraded...");
		String upgradeScript = "DROP TABLE IF EXISTS " + DB_NAME;
		db.execSQL(upgradeScript);
		onCreate(db);
	}
	
	public void executeSQL(String sql) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
		db.close();
	}
	
	public void deleteAll() {
		Log.i(TAG, "Database is being deleted data...");
		String deleteScript = " DELETE FROM " + TABLE_NAME;
		executeSQL(deleteScript);
	}
	
	public void delete(Calculation calc) {
		Log.i(TAG, "This row is being deleted...");
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, COL_ID + " =? ", new String[]{String.valueOf(calc.getId())});
		db.close();
	}
	
	public void deleteOthers(Calculation calc) {
		Log.i(TAG, "Other rows is being deleted...");
		String deleteScript = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " NOT IN " +
				"( SELECT " + COL_ID + " FROM " + TABLE_NAME + " WHERE " + COL_ID + " = '" + calc.getId() + "' ); ";
		executeSQL(deleteScript);
	}
	
	public void add(Calculation calc) {
		Log.i(TAG, "Database is being added data...");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_EXP, calc.getExpression());
		values.put(COL_RESULT, calc.getResult());
		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	
	public void update(Calculation calc) {
		Log.i(TAG, "Database is being updating data...");
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_EXP, calc.getExpression());
		values.put(COL_RESULT, calc.getResult());
		db.update(TABLE_NAME, values, COL_ID + " = " + calc.getId(), null);
		db.close();
	}
	
	public List<Calculation> getAll() {
		Log.i(TAG, "Database is being gotten all data...");
		List<Calculation> result = new ArrayList<>();
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Calculation calc = new Calculation();
				calc.setId(Integer.parseInt(cursor.getString(0)));
				calc.setExpression(cursor.getString(1));
				calc.setResult(cursor.getString(2));
				result.add(calc);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return result;
	}
	
	public int getCount() {
		Log.i(TAG, "Database is being counted all data...");
		String countQuery = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
}
