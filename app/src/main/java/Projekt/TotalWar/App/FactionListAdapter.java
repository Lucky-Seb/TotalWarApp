package Projekt.TotalWar.App;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class FactionListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FactionModel> mFactionList;

    public FactionListAdapter(Context context, ArrayList<FactionModel> factionList) {
        mContext = context;
        mFactionList = factionList;
    }

    @Override
    public int getCount() {
        return mFactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item_faction, parent, false);

            holder = new ViewHolder();
            holder.nameTextView = convertView.findViewById(R.id.tv_faction_name);
            holder.heroesCountTextView = convertView.findViewById(R.id.tv_heroes_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FactionModel faction = mFactionList.get(position);

        holder.nameTextView.setText(faction.factionName);
        if (faction.heroes != null) {
            holder.heroesCountTextView.setText("Heroes: " + faction.heroes.size());
        } else {
            holder.heroesCountTextView.setText("Heroes: 0"); // Or any default value you want to display
        }

        return convertView;
    }

    static class ViewHolder {
        TextView nameTextView;
        TextView heroesCountTextView;
    }
}
