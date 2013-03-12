package il.ac.huji.todolist;
import java.util.List;

import android.graphics.Color;
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
		TextView title = (TextView)view.findViewById(R.id.txtTitle);
		title.setText(item.getTitle());
		// set colors of task according to position
		if (position % 2 == 0) {
			title.setTextColor(Color.RED);
		}
		else {
			title.setTextColor(Color.BLUE);
		}
		return view;
	}
}