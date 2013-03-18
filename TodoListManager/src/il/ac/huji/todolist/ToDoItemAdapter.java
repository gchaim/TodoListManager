package il.ac.huji.todolist;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/* adapter for displaying tasks */
public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

	// ctor
	public ToDoItemAdapter(
			TodoListManagerActivity activity, List<ToDoItem> items) {
		super(activity, android.R.layout.simple_list_item_1, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ToDoItem item = getItem(position);
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View view = inflater.inflate(R.layout.row, null);
		TextView title = (TextView)view.findViewById(R.id.txtTodoTitle);
		title.setText(item.getTitle());
		TextView date = (TextView)view.findViewById(R.id.txtTodoDueDate);

		if (item.getDate()==null){
			date.setText("No due date");
		}
		else{
			// Check overdue tasks
			Date cur = new Date(System.currentTimeMillis());
			if (item.getDate().before(cur)){
				title.setTextColor(Color.RED);
				date.setTextColor(Color.RED);
			}

			String formatedDate;
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");  
			formatedDate=df.format(item.getDate());
			date.setText(formatedDate);
		}
		return view;
	}
}