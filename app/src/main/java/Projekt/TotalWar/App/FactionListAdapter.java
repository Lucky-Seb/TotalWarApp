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
            holder.factionIdTextView  = convertView.findViewById(R.id.tv_faction_id);
            holder.factionNameTextView = convertView.findViewById(R.id.tv_faction_name);
            holder.heroesCountTextView = convertView.findViewById(R.id.tv_heroes_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FactionModel faction = mFactionList.get(position);

        holder.factionNameTextView.setText("Name: "+faction.factionName);
        holder.factionIdTextView.setText("ID: "+ faction.factionId.toString());
        if (faction.heroes != null) {
            holder.heroesCountTextView.setText("Heroes: " + faction.heroes.size());
        } else {
            holder.heroesCountTextView.setText("Heroes: 0"); // Or any default value you want to display
        }

        return convertView;
    }

    static class ViewHolder {
        TextView factionIdTextView;
        TextView factionNameTextView;
        TextView heroesCountTextView;
    }
    // Custom method to add a single item to the adapter
    public void addItem(FactionModel factionModel) {
        mFactionList.add(factionModel);
        notifyDataSetChanged();
    }

    // Custom method to clear adapter data
    public void clear() {
        mFactionList.clear();
        notifyDataSetChanged();
    }
    public void removeItem(FactionModel factionModel) {
        mFactionList.remove(factionModel);
        notifyDataSetChanged();
    }
    public ArrayList<FactionModel> getFactionList() {
        return mFactionList;
    }
    public void removeAt(int position) {
        mFactionList.remove(position);
        notifyDataSetChanged();
    }
}
