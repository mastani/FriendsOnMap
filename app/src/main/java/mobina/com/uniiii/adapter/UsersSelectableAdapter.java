package mobina.com.uniiii.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class UsersSelectableAdapter extends BaseAdapter {
    private final ArrayList<User> mData;
    public static ArrayList<User> chechek = new ArrayList<>();

    public UsersSelectableAdapter(ArrayList<User> users) {
        mData = users;
        if (chechek.size() > 0)
            chechek.clear();
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
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_selectable_item, parent, false);
        } else {
            result = convertView;
        }

        final User item = getItem(position);

        TextView name = (TextView) result.findViewById(R.id.name);
        name.setText(item.getName());

        TextView mobile = (TextView) result.findViewById(R.id.mobile);
        mobile.setText(item.getMobile());

        final CheckBox cbBox = (CheckBox) result.findViewById(R.id.cbBox);
        cbBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !chechek.contains(item))
                    chechek.add(item);

                if (!isChecked && chechek.contains(item))
                    chechek.remove(item);
            }
        });

        LinearLayout lstTurn = (LinearLayout) result.findViewById(R.id.lstTurn);
        lstTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbBox.setChecked(!cbBox.isChecked());
            }
        });

        return result;
    }
}
