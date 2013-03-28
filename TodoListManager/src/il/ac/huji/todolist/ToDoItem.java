package il.ac.huji.todolist;

import java.util.Date;

/* basic task object */
public class ToDoItem implements ITodoItem{
	private String _title;
	private Date _date;
	public ToDoItem(String title, Date date){ _title=title; _date=date;}
	public String getTitle(){ return _title; }
	@Override
	public Date getDueDate() {
		return _date;
	}
}
