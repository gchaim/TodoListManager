package il.ac.huji.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoListdbHelper extends SQLiteOpenHelper{

		public TodoListdbHelper(Context context) {
			super(context, "todo_db", null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table todo( _id integer primary key autoincrement,"
					+  " title text, due INTEGER );");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}

	
}
