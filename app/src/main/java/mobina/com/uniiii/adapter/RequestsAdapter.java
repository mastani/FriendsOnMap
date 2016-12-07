package mobina.com.uniiii.adapter;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobina.com.uniiii.R;
import mobina.com.uniiii.Utility.ApplicationController;
import mobina.com.uniiii.Utility.Utilies;
import mobina.com.uniiii.abstracts.User;

public class RequestsAdapter extends BaseAdapter {
    private final ArrayList<User> mData;

    public RequestsAdapter(ArrayList<User> users) {
        mData = users;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public User getItem(int position) {
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
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.requests_adapter, parent, false);
        } else {
            result = convertView;
        }

        final User item = getItem(position);

        TextView name = (TextView) result.findViewById(R.id.name);
        name.setText(item.getName());

        TextView mobile = (TextView) result.findViewById(R.id.mobile);
        mobile.setText(item.getMobile());

        final Button accept = (Button) result.findViewById(R.id.accept);
        final Button deny = (Button) result.findViewById(R.id.deny);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = Utilies.URL + "friend.php";
                StringRequest req = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                accept.setVisibility(View.GONE);
                                deny.setVisibility(View.GONE);
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
                        params.put("email", Utilies.me.getEmail());
                        params.put("target", item.getEmail());
                        params.put("type", "accept");
                        return params;
                    }
                };

                req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                req.setShouldCache(false);
                ApplicationController.getInstance().addToRequestQueue(req);
            }
        });

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = Utilies.URL + "friend.php";
                StringRequest req = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                result.setVisibility(View.GONE);
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
                        params.put("email", Utilies.me.getEmail());
                        params.put("target", item.getEmail());
                        params.put("type", "deny");
                        return params;
                    }
                };

                req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                req.setShouldCache(false);
                ApplicationController.getInstance().addToRequestQueue(req);
            }
        });

        return result;
    }
}