package mobina.com.uniiii.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import mobina.com.uniiii.R;
import mobina.com.uniiii.Utility.Utilies;
import mobina.com.uniiii.adapter.UsersAdapter;

public class UsersActivity extends AppCompatActivity {
    ListView listV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        listV = (ListView) findViewById(R.id.listView);

        UsersAdapter adapter = new UsersAdapter(Utilies.syncedUsers);
        listV.setAdapter(adapter);
    }

}
