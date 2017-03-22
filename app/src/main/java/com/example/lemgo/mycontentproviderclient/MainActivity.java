package com.example.lemgo.mycontentproviderclient;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String AUTHORITY="com.example.lemgo.MyContentProvider";
    private Button btn = null;
    //访问ContentProvider的uri
    Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView show = (TextView) findViewById(R.id.show);
        StringBuilder stringBuilder = new StringBuilder("");
        //得到ContentProvider对应表的数据，以游标形式保存
        Cursor cursor  = getContentResolver().query(CONTENT_URI, new String[]{"_id", "USER_NAME"}, null, null, null);
        //循环输出ContentProvider的数据
        if(cursor.moveToFirst()){
            String _id = null;
            String user_name = null;
            do{
                //得到id列，username列
                _id = cursor.getString(cursor.getColumnIndex("_id"));
                user_name = cursor.getString(cursor.getColumnIndex("USER_NAME"));
                stringBuilder.append("_id="+_id+",user_name="+user_name+"\n");
            }while (cursor.moveToNext());
        }
        show.setText(stringBuilder);

        btn  = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText in = (EditText) findViewById(R.id.insertname);
                String username = in.getText().toString();

                ContentResolver contentResolver=getContentResolver();

                ContentValues contentValues = new ContentValues();

                contentValues.put("USER_NAME",username);
                //插入数据
                contentResolver.insert(CONTENT_URI,contentValues);
            }
        });
    }
}
