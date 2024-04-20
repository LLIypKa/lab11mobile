package com.example.laba8;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button addButton, deleteButton, clearButton, readButton, updateButton;
    EditText editId, editEmail, editName;
    DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editId = findViewById(R.id.editId);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(this);

        clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(this);

        readButton = findViewById(R.id.readButton);
        readButton.setOnClickListener(this);

        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(this);

        helper = new DBHelper(this);
    };

    @Override
    public void onClick(View view) {
        String id = editId.getText().toString();
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();

        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Integer id1 = view.getId();
        if(id1 == addButton.getId()) {
            Log.d("mLog", "hihihaha");
            contentValues.put(DBHelper.KEY_ID, id);
            contentValues.put(DBHelper.KEY_NAME, name);
            contentValues.put(DBHelper.KEY_MAIL, email);
            database.insert(DBHelper.TABLE_PERSONS, null, contentValues);
        }
        //addButton, deleteButton, clearButton, readButton, updateButton
        if(id1 == deleteButton.getId()){
            if (id.equalsIgnoreCase("")) {
                helper.close();
                return;
            }
            int delCount = database.delete(DBHelper.TABLE_PERSONS, DBHelper.KEY_ID + "= " + id, null);
            Log.d("mLog", "Удалено строк = " + delCount);
        }

        if(id1 == clearButton.getId()){
            database.delete(DBHelper.TABLE_PERSONS, null, null);
        }

        if(id1 == readButton.getId()){
            Cursor cursor = database.query(DBHelper.TABLE_PERSONS, null, null, null,
                    null, null, null);
            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                do {
                    Log.d("mLog", "ID =" + cursor.getInt(idIndex) +
                            ", name = " + cursor.getString(nameIndex) +
                            ", email = " + cursor.getString(emailIndex));
                } while (cursor.moveToNext());
            } else
                Log.d("mLog", "0 rows");

            cursor.close();
        }

        if(id1 == updateButton.getId()){
            if (id.equalsIgnoreCase("")) {
                helper.close();
                return;
            }
            contentValues.put(DBHelper.KEY_MAIL, email);
            contentValues.put(DBHelper.KEY_NAME, name);
            int updCount = database.update(DBHelper.TABLE_PERSONS, contentValues,
                    DBHelper.KEY_ID + "= ?", new String[] {id});
            Log.d("mLog", "Обновлено строк = " + updCount);

        }

        helper.close();
        return;
    }
}