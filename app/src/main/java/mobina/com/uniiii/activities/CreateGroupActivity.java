package mobina.com.uniiii.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mobina.com.uniiii.R;
import mobina.com.uniiii.Utility.ApplicationController;
import mobina.com.uniiii.Utility.Utilies;
import mobina.com.uniiii.abstracts.Group;
import mobina.com.uniiii.abstracts.User;
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
                    return;
                }

                String URL = Utilies.URL + "groups.php";
                StringRequest req = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject mainObject = new JSONObject(response);
                                    if (mainObject.has("success") && mainObject.getBoolean("success")) {
                                        Toast.makeText(getBaseContext(), "گروه با موفقیت ایجاد شد", Toast.LENGTH_LONG).show();
                                        Utilies.groups.add(new Group(mainObject.getInt("gp_id"), groupName.getText().toString(), UsersSelectableAdapter.chechek, true));
                                        UsersSelectableAdapter.chechek.clear();
                                        finish();
                                    }

                                } catch (JSONException e) {

                                }
                            }
                        }
                        ,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getBaseContext(), R.string.internet_error, Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("command", "create");
                        params.put("email", Utilies.me.getEmail());

                        String members = ",";
                        for (User member : UsersSelectableAdapter.chechek) {
                            members += member.getId() + ",";
                        }
                        params.put("members", members);
                        params.put("name", groupName.getText().toString());
                        return params;
                    }
                };

                req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                req.setShouldCache(false);
                ApplicationController.getInstance().addToRequestQueue(req);
            }
        });
    }
}
