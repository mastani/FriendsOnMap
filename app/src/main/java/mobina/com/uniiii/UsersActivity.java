package mobina.com.uniiii;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import mobina.com.uniiii.Utility.Utilies;
import mobina.com.uniiii.adapter.UserAdapter;

public class UsersActivity extends AppCompatActivity {
    ListView listV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        listV = (ListView) findViewById(R.id.listView);

        UserAdapter adapter = new UserAdapter(Utilies.syncedUsers);
        listV.setAdapter(adapter);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(UsersActivity.this, TrackActivity.class);
                Bundle bundle = new Bundle();
                String val = listV.getAdapter().getItem(position).toString();
                //Add your data to bundle
                bundle.putString("meghdar", val);
                //Add the bundle to the intent
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

}
