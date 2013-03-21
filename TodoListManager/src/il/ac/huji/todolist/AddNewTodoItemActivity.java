package il.ac.huji.todolist;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

/* represents the activity of adding a new task to the list */
public class AddNewTodoItemActivity extends Activity{

	public void onCreate(Bundle unused) { 
		super.onCreate(unused); 
		setContentView(R.layout.add_item_activity); 
		setTitle(R.string.add_item_title);

		// OK button
		findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText edtNewItem = (EditText)findViewById(R.id.edtNewItem);
				String itemName = edtNewItem.getText().toString();
				DatePicker datePicker = (DatePicker)findViewById(R.id.datePicker);
				Date date =new Date(datePicker.getYear() - 1900,
						datePicker.getMonth(), datePicker.getDayOfMonth());
				if (itemName == null || "".equals(itemName)) {
					setResult(RESULT_CANCELED);
					finish();
				} else {
					// send back results
					Intent resultIntent = new Intent();
					resultIntent.putExtra("title", itemName);
					resultIntent.putExtra("dueDate", date);
					setResult(RESULT_OK, resultIntent);
					finish();
				}
			}
		});
		
		// Cancel button
		findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

	} 

}
