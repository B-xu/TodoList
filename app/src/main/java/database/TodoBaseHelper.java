package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import database.TodoSchema.TodoTable;

public class TodoBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String databaseName = "Todobase.db";

    public TodoBaseHelper(Context context) {
        super(context, databaseName, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" Create table "+ TodoTable.NAME+ " ("
        +" _id integer primary key autoincrement, "
        +        TodoTable.cols.TITLE
        + "," + TodoTable.cols.DATE
        + " ," + TodoTable.cols.UUID
        + " ," + TodoTable.cols.URGENCY
        + " ," + TodoTable.cols.POMOS +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
