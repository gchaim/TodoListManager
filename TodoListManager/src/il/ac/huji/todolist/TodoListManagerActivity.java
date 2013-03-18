package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/* Main activity */
public class TodoListManagerActivity extends Activity {
	final static int ADD = 11;
	// adapter for displaying task
	private ArrayAdapter<ToDoItem> _adapter;
	// input text box
	EditText _et;
	// list of 'todo's
	ListView _lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		_et = (EditText)findViewById(R.id.edtNewItem);
		_lv=(ListView)findViewById(R.id.lstTodoItems);
		registerForContextMenu(_lv); 
		List<ToDoItem> items = new ArrayList<ToDoItem>();
		_adapter = 	new ToDoItemAdapter(this, items);
		_lv.setAdapter(_adapter);
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuinfo) { 
			super.onCreateContextMenu(menu, v, menuinfo); 
			getMenuInflater().inflate(R.menu.task_menu, menu); 
		
			AdapterContextMenuInfo info = (AdapterContextMenuInfo)menuinfo;
			String title=_adapter.getItem(info.position).getTitle();
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
		 switch (item.getItemId()){
		 case R.id.menuItemDelete:
			 _adapter.remove(_adapter.getItem(info.position));
				break;
		case R.id.menuItemCall:
			// use dialer to execute the call
			Intent dial = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+_adapter.getItem(info.position)
					.getTitle().substring(5)));
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
			//_adapter.add(new ToDoItem(_et.getText().toString()));
			Intent intent = new Intent(this,AddNewTodoItemActivity.class);
			startActivityForResult(intent, ADD);

		}
		return true;
		
	} 
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK){
			_adapter.add(new ToDoItem(data.getExtras().getString("itemName"), (Date)data.getExtras().get("dueDate")));
		}
	}
}
