package mobina.com.uniiii.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobina.com.uniiii.R;
import mobina.com.uniiii.Utility.ApplicationController;
import mobina.com.uniiii.Utility.Utilies;
import mobina.com.uniiii.abstracts.Group;
import mobina.com.uniiii.abstracts.User;
import mobina.com.uniiii.activities.TrackActivity;
import mobina.com.uniiii.activities.TrackGroupActivity;

public class GroupsAdapter extends BaseAdapter {
    private final ArrayList<Group> mData;

    public GroupsAdapter(ArrayList<Group> groups) {
        mData = groups;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Group getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.groups_adapter, parent, false);
        } else {
            result = convertView;
        }

        final Group group = getItem(position);

        TextView name = (TextView) result.findViewById(R.id.name);
        name.setText(group.getName());

        TextView usersCount = (TextView) result.findViewById(R.id.users_count);
        usersCount.setText(String.valueOf(group.getMembers().size() + 1) + " عضو");

        final Button btnViewDetails = (Button) result.findViewById(R.id.view_details);
        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(result.getContext(), TrackGroupActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("group", group);
                i.putExtras(bundle);
                result.getContext().startActivity(i);
            }
        });

        final Button btnLeft = (Button) result.findViewById(R.id.left);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = Utilies.URL + "groups.php";
                StringRequest req = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject mainObject = new JSONObject(response);
                                    if (mainObject.has("success") && mainObject.getBoolean("success")) {
                                        Toast.makeText(result.getContext(), "با موفقیت خارج شدید", Toast.LENGTH_LONG).show();
                                        result.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {

                                }
                            }
                        }
                        ,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(result.getContext(), R.string.internet_error, Toast.LENGTH_LONG).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("command", "left");
                        params.put("email", Utilies.me.getEmail());
                        params.put("gp_id", String.valueOf(group.getId()));
                        if (group.getCreator())
                            params.put("creator", "true");
                        else
                            params.put("user_id", String.valueOf(Utilies.me.getId()));
                        return params;
                    }
                };

                req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                req.setShouldCache(false);
                ApplicationController.getInstance().addToRequestQueue(req);
            }
        });

        if (!group.getCreator()) {
            btnViewDetails.setVisibility(View.GONE);
        }

        return result;
    }
}