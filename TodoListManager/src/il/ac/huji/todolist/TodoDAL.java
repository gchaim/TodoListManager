package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//ctor
public class TodoDAL {
	private static final int FAILURE=-1;
	private static final int TITLE_COL=1;
	private static final int DUE_COL=2;
	private SQLiteDatabase db;
	private Cursor cursor;
	
	//ctor
	public TodoDAL(Context context) {
		
		Resources r = context.getResources();
		
		// Parse.com cloud interface registration
		Parse.initialize(context, r.getString(R.string.parseApplication), r.getString(R.string.clientKey));
		ParseUser.enableAutomaticUser();
		TodoListdbHelper helper = new TodoListdbHelper(context);
		db = helper.getWritableDatabase();
		
		cursor = db.query("todo",
				new String[] { "_id", "title", "due" },
				null, null, null, null, null);
	}
	
	// return the cursor
	public Cursor getCursor(){ return cursor; }
	
	// insert item to db/parse
	 public boolean insert(ITodoItem todoItem) {
		 	Date date=todoItem.getDueDate();
			ContentValues values = new ContentValues();
			values.put("title", todoItem.getTitle());
			if (date!=null){
			values.put("due",todoItem.getDueDate().getTime());
			}
			else{
				values.putNull("due");
			}
			long succ =db.insert("todo", null, values);
			if (succ==FAILURE) return false;
			cursor.requery();

			try{
			ParseObject parseObject=new ParseObject("todo");
			parseObject.put("title",  todoItem.getTitle());
			if (date!=null){
			parseObject.put("due",  todoItem.getDueDate().getTime());
			}
			else{
				parseObject.put("due", JSONObject.NULL);
			}
			parseObject.saveInBackground();
			}
			catch (Exception e){
				return false;
			}
			return true;
	 }
	 
	 // update item from the db/parse
	 public boolean update(ITodoItem todoItem) {
		 final Date date =todoItem.getDueDate();
		 ContentValues values = new ContentValues();
		if (date!=null){ 
			values.put("due",todoItem.getDueDate().getTime());
		}
		else {
			values.putNull("due");
		}
		long succ=db.update("todo", values, "title=?", new String[] {todoItem.getTitle()}); 
		if (succ==FAILURE){
			return false;
		}
		cursor.requery();
		
		try{
		ParseQuery query=new ParseQuery("todo");
		query.whereEqualTo("title", todoItem.getTitle());
		query.findInBackground(new FindCallback() {
			
			@Override
			public void done(List<ParseObject> mathces, ParseException e) {
				if (e==null){
					for (ParseObject parseObject : mathces) {
						if (date!=null){							
							parseObject.put("due",date.getTime());
						}
						else {
							parseObject.put("due",JSONObject.NULL);
						}
						parseObject.saveInBackground();
					}
				}
			}
		});
		}
		catch (Exception e){
			return false;
		}
		return true;
	 }
	 
	 // delete item from the db/parse
	 public boolean delete(ITodoItem todoItem) {
			long succ=db.delete("todo", "title=?"
					,new String[] {todoItem.getTitle()});
			if (succ==FAILURE){
				return false;
			}
			cursor.requery();
			
			try{
			ParseQuery query=new ParseQuery("todo");
			query.whereEqualTo("title", todoItem.getTitle());

			query.findInBackground(new FindCallback() {
				
				@Override
				public void done(List<ParseObject> mathces, ParseException e) {
					if (e==null){
						for (ParseObject parseObject : mathces) {
							parseObject.deleteInBackground();
						}
					}
				}
			});
			}
			catch (Exception e){
				return false;
			}
			return true;
	 }
	 
	 //return all items
	 public List<ITodoItem> all() {
		 List<ITodoItem> list = new ArrayList<ITodoItem>();
		 if (cursor.moveToFirst()) {
				do {
				if (!cursor.isNull(DUE_COL)){
					list.add(new ToDoItem(cursor.getString(TITLE_COL), new Date(cursor.getLong(DUE_COL))));
				}
				else{
					list.add(new ToDoItem(cursor.getString(TITLE_COL), null));
				}
				} while (cursor.moveToNext());
		 }
		 return list;

	 }
}
