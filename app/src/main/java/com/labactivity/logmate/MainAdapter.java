package com.labactivity.logmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainModel> mainModelList;
    private Context context;

    public MainAdapter(List<MainModel> mainModelList, Context context) {
        this.mainModelList = mainModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MainModel mainModel = mainModelList.get(position);

        holder.dateTextView.setText(mainModel.getDate());
        holder.nameTextView.setText(mainModel.getName());
        holder.idNumTextView.setText(mainModel.getId_Num());
        holder.timeInTextView.setText(mainModel.getTime_In());
        holder.timeOutTextView.setText(mainModel.getTime_Out());
    }

    @Override
    public int getItemCount() {
        return mainModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView, nameTextView, idNumTextView, timeInTextView, timeOutTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.dateCardView);
            nameTextView = itemView.findViewById(R.id.nameCardView);
            idNumTextView = itemView.findViewById(R.id.idNumCardView);
            timeInTextView = itemView.findViewById(R.id.timeInCardView);
            timeOutTextView = itemView.findViewById(R.id.timeOutCardView);
        }
    }
}

