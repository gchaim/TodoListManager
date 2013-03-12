package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/* Main activity */
public class TodoListManagerActivity extends Activity {
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
		List<ToDoItem> items = new ArrayList<ToDoItem>();
		_adapter = 	new ToDoItemAdapter(this, items);
		_lv.setAdapter(_adapter);
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
			_adapter.add(new ToDoItem(_et.getText().toString()));
		// delete task
		case (R.id.menuItemDelete):
			_adapter.remove((ToDoItem) _lv.getSelectedItem());
		}
		return true;
	} 
}
