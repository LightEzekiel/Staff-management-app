package com.potentnetwork.phrankstars.PHS;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.potentnetwork.phrankstars.PCA;
import com.potentnetwork.phrankstars.R;


import java.util.Collections;
import java.util.List;


public class PHSTeacherAdapter extends RecyclerView.Adapter<PHSTeacherAdapter.ViewHolder> {

    Context mContext;
    List<PHS> phsTeacher;

    PHSTeacherAdapter.OnItemClickListener mOnItemClickListener;
    ImageView staff_imageView;


    public PHSTeacherAdapter(Context mContext, List<PHS> phsTeacher, PHSTeacherAdapter.OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.phsTeacher = phsTeacher;
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @NonNull
    @Override
    public PHSTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.phs_teachers_list_layout,parent,false);

        return new ViewHolder(view,mOnItemClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull PHSTeacherAdapter.ViewHolder holder, int position) {


        Glide.with(mContext).load(phsTeacher.get(position).getTeacherImageUri())
                .centerCrop()
                .placeholder(R.drawable.ic_glide_person)
                .into(staff_imageView);

        Collections.sort(phsTeacher, PHS.staffName);
        holder.teacherName.setText(phsTeacher.get(position).getStaff_name1());

    }

    @Override
    public int getItemCount() {
        return phsTeacher.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView teacherName;
        CardView teacherCardView;
        PHSTeacherAdapter.OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, PHSTeacherAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);

            teacherCardView = itemView.findViewById(R.id.phsteacherView);
            teacherName = itemView.findViewById(R.id.phs_teacherName);
            staff_imageView = itemView.findViewById(R.id.phs_staff_imageView);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            teacherCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemclick(getAdapterPosition());
        }


    }
    public interface OnItemClickListener {
        void onItemclick(int position);
    }


}
