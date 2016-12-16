package mobina.com.uniiii.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import mobina.com.uniiii.R;
import mobina.com.uniiii.Utility.Utilies;
import mobina.com.uniiii.adapter.GroupsAdapter;
import mobina.com.uniiii.adapter.UsersAdapter;
import mobina.com.uniiii.adapter.UsersSelectableAdapter;

public class CreateGroupActivity extends AppCompatActivity {
    ListView listV;
    UsersSelectableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        listV = (ListView) findViewById(R.id.listView);
        adapter = new UsersSelectableAdapter(Utilies.friendsUsers);
        listV.setAdapter(adapter);

        final EditText groupName = (EditText) findViewById(R.id.group_name);

        Button create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (groupName.getText().length() < 3) {
                    Toast.makeText(getBaseContext(), "نام گروه کوتاه است", Toast.LENGTH_LONG).show();
                    return;
                }

                if (UsersSelectableAdapter.chechek.size() == 0) {
                    Toast.makeText(getBaseContext(), "حداقل یک عضو انتخاب کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
