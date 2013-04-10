package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/* Main activity */
public class TodoListManagerActivity extends Activity {
	final static int ADD = 11;
	// adapter for displaying task

	ToDoItemAdapter _adapter;
	// input text box
	EditText _et;
	// list of 'todo's
	ListView _lv;

	private TodoDAL _tododal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		_tododal=new TodoDAL(this);
		
		setContentView(R.layout.activity_todo_list_manager);
		_et = (EditText)findViewById(R.id.edtNewItem);
		_lv=(ListView)findViewById(R.id.lstTodoItems);
		registerForContextMenu(_lv); 

	
		String[] from = { "title", "due" };
		int[] to = { R.id.txtTodoTitle, R.id.txtTodoDueDate };
		 _adapter= new ToDoItemAdapter(this,
				R.layout.row, _tododal.getCursor(), from, to);
		_lv.setAdapter(_adapter);	
		
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuinfo) { 
			super.onCreateContextMenu(menu, v, menuinfo); 
			getMenuInflater().inflate(R.menu.task_menu, menu); 
		
			AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuinfo;
			String title=((Cursor)_adapter.getItem(info.position)).getString(1);
			
			menu.setHeaderTitle(title);
			// set menu according to selected task
			if (!title.startsWith("Call ")){
				menu.removeItem(R.id.menuItemCall);	
			}
			else {
				menu.findItem(R.id.menuItemCall).setTitle(title);
			}
		
	} 
	public boolean onContextItemSelected(MenuItem item) { 
		AdapterContextMenuInfo info = 
		 (AdapterContextMenuInfo)item.getMenuInfo(); 
		
		Cursor c =(Cursor)_adapter.getItem(info.position);
		ToDoItem tditem=new ToDoItem(c.getString(1),new Date(c.getLong(2)));
		 switch (item.getItemId()){
		 case R.id.menuItemDelete:
			_tododal.delete(tditem);
				break;
		case R.id.menuItemCall:
			// use dialer to execute the call
			Intent dial = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+c.getString(1).substring(5)));
			startActivity(dial);
		}
		 return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}

	/* what to do when select menu items */
	public boolean onOptionsItemSelected(MenuItem item) { 

		switch (item.getItemId()){
		// add task
		case (R.id.menuItemAdd):
			Intent intent = new Intent(this,AddNewTodoItemActivity.class);
			startActivityForResult(intent, ADD);

		}
		return true;
		
	} 
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK){
			ToDoItem tditem=new ToDoItem(data.getExtras().getString("title"), ((Date)data.getExtras().get("dueDate")));
			_tododal.insert(tditem);
		}
	}
}
