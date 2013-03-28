package il.ac.huji.todolist;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.database.Cursor;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/* adapter for displaying tasks */
public class ToDoItemAdapter extends SimpleCursorAdapter {

	// ctor
	public ToDoItemAdapter(
			TodoListManagerActivity activity, int layout,Cursor cursor,String[] s, int[] i) {
		super(activity, layout, cursor, s, i);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Cursor item = (Cursor) getItem(position);
		View view = super.getView(position, convertView,parent);
		TextView title = (TextView)view.findViewById(R.id.txtTodoTitle);
		title.setText(item.getString(1));
		TextView date = (TextView)view.findViewById(R.id.txtTodoDueDate);
		Date due;
		if (item.isNull(2)){//////////////////
			date.setText("No due date");
		}
		else{
			due=new Date(item.getLong(2));
			// Check overdue tasks
			Date cur = new Date(System.currentTimeMillis());
			if (due.before(cur)){
				title.setTextColor(Color.RED);
				date.setTextColor(Color.RED);
			}
			else {
				title.setTextColor(Color.BLACK);
				date.setTextColor(Color.BLACK);
				
			}
			String formatedDate;
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
			formatedDate=df.format(due);
			date.setText(formatedDate);
		}
		return view;
	}
}