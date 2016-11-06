package mobina.com.uniiii;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {
    String namayesh;
    ArrayList <String>  mylist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        final ListView listV = (ListView) findViewById(R.id.listView);
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist);
        adapter.setNotifyOnChange(true);
        listV.setAdapter(adapter);
//mylist.addAll(Arrays.asList("dafsdf","khoobi?" ,"ok","are","ok mishe"))
        /////////////////////////////////////////////////////////////////////////////////
        //   mydb.execSQL("INSERT INTO location4 (username ,tool, arz ) VALUES('reza','b','85')");
        //  mydb.execSQL("INSERT INTO location4 (username ,tool, arz ) VALUES('akhari'," + g + ",'85')");
        //   Toast.makeText(MainActivity.this, "careated!!", Toast.LENGTH_SHORT).show();
        //

        ApplicationController.getInstance().mydb = openOrCreateDatabase(ApplicationController.getInstance().DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor allrows = ApplicationController.getInstance().mydb.rawQuery("SELECT username FROM final ", null);
        //  Cursor allrowsa = mydb.rawQuery("SELECT arz FROM location3 ", null);
        // Cursor allrowsc  = mydb.rawQuery("SELECT username FROM proje1", null);
        while (allrows.moveToNext()) {
            namayesh = allrows.getString(allrows.getColumnIndex("username"));
            mylist.add(namayesh);
            adapter.setNotifyOnChange(true);
            listV.setAdapter(adapter);

        }
        ApplicationController.getInstance().mydb.close();


        //    while (allrowsa.moveToNext()){
        //   String arz = allrowsa.getString(allrowsa.getColumnIndex("arz"));
        //     mylist.add(arz);



        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(UsersActivity.this , TrackActivity.class) ;
                Bundle bundle = new Bundle();
                String val= listV.getAdapter().getItem(position).toString();
                //Add your data to bundle
                bundle.putString("meghdar",val);
                //Add the bundle to the intent
                i.putExtras(bundle);
                startActivity(i);
            }
        });



    }

}
