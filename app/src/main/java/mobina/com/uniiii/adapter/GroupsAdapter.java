package mobina.com.uniiii.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import mobina.com.uniiii.R;
import mobina.com.uniiii.abstracts.Group;
import mobina.com.uniiii.abstracts.User;
import mobina.com.uniiii.activities.TrackActivity;

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

        final Group item = getItem(position);

        TextView name = (TextView) result.findViewById(R.id.name);
        name.setText(item.getName());

        TextView usersCount = (TextView) result.findViewById(R.id.users_count);
        usersCount.setText(String.valueOf(item.getMembers().size()) + " عضو");

        final Button view = (Button) result.findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(result.getContext(), TrackActivity.class);

                Bundle bundle = new Bundle();
                //bundle.putDouble("lat", Double.valueOf(item.getLatitude()));
                //bundle.putDouble("lon", Double.valueOf(item.getLongitude()));
                i.putExtras(bundle);
                result.getContext().startActivity(i);
            }
        });

        return result;
    }
}