package mobina.com.uniiii.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import mobina.com.uniiii.adapter.FriendsAdapter;
import mobina.com.uniiii.adapter.GroupsAdapter;

public class GroupsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listV;
    GroupsAdapter adapter;
    Button createGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        listV = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        Utilies.groups.clear();
        adapter = new GroupsAdapter(Utilies.groups);
        listV.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                fetchGroups();
            }
        });

        createGroup = (Button) findViewById(R.id.create_group);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GroupsActivity.this, CreateGroupActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onRefresh() {
        fetchGroups();
    }

    private void fetchGroups() {
        swipeRefreshLayout.setRefreshing(true);

        String URL = Utilies.URL + "loadGroups.php";
        StringRequest req = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainObject = new JSONObject(response);
                            if (mainObject.has("success") && mainObject.getBoolean("success")) {
                                JSONArray usersArray = mainObject.getJSONArray("users");
                                Utilies.friendsUsers.clear();

                                for (int i = 0; i < usersArray.length(); i++) {
                                    JSONObject user = usersArray.getJSONObject(i);
                                    int id = user.getInt("id");
                                    String name = user.getString("name");
                                    String email = user.getString("email");
                                    String mobile = user.getString("mobile");
                                    String latitude = user.getString("latitude");
                                    String longitude = user.getString("longitude");
                                    String update_time = user.getString("update_time");

                                    Utilies.friendsUsers.add(new User(id, name, email, mobile, latitude, longitude, update_time));
                                }
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {

                        }

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
                ,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), R.string.internet_error, Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", Utilies.me.getEmail());
                return params;
            }
        };

        req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setShouldCache(false);
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    private void CreateGroup() {

    }
}
