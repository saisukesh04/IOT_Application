package com.iot.iotapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final List<Reading> readingList;
    private final int type;
    private Context context;

    public FeedAdapter(List<Reading> readingList, int type) {
        this.readingList = readingList;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.feed_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reading feed = readingList.get(position);
        holder.entry_id.setText(String.valueOf(feed.getEntry_id()));
        holder.created_at.setText(getTimeInString(feed.getCreated_at().replace("Z","")));

        if (type == 0) {
            if (!feed.getField1().equals("null"))
                holder.temp_field.setText(feed.getField1() + context.getString(R.string.celsius));
            else
                holder.temp_field.setText("N/A");
        } else {
            holder.type.setText("Gas : ");
            if (!feed.getField2().equals("null"))
                holder.temp_field.setText(feed.getField2() + " ppm");
            else
                holder.temp_field.setText("N/A");
        }
    }

    @Override
    public int getItemCount() {
        return readingList == null ? 0 : readingList.size();
    }

    public static String getTimeInString(String timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            DateFormat formatter = new SimpleDateFormat("MMM dd yyyy, hh:mm aa");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(dateFormat.parse(timestamp).getTime());
            return formatter.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView entry_id, created_at, temp_field, type;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            entry_id = itemView.findViewById(R.id.entry_id);
            created_at = itemView.findViewById(R.id.created_at);
            temp_field = itemView.findViewById(R.id.temperature_field);
            type = itemView.findViewById(R.id.textView2);
        }
    }
}
