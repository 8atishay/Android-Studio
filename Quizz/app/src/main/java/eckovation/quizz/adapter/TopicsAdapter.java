package eckovation.quizz.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import eckovation.quizz.R;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.TopicCustomViewHolder> {

    Context mContext;
    ArrayList<String> mArrayList;
    ArrayList<GradientDrawable> mGradientDrawable;

    public void addTopic(String topic){
        mArrayList.add(topic);
    }

    public TopicsAdapter(Context context, ArrayList<String> arrayList){
        mArrayList = arrayList;
        mContext = context;
        mGradientDrawable = new ArrayList<>();
        fillGradientList(context);
    }
    @NonNull
    @Override
    public TopicsAdapter.TopicCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item_list_view,parent,false);

        return new TopicCustomViewHolder(view);
    }

    private void fillGradientList(Context context){
        mGradientDrawable.add(getTempGradientDrawable(ContextCompat.getColor(context, R.color.gradient_1_start),ContextCompat.getColor(context, R.color.gradient_1_end)));
        mGradientDrawable.add(getTempGradientDrawable(ContextCompat.getColor(context, R.color.gradient_2_start),ContextCompat.getColor(context, R.color.gradient_2_end)));
        mGradientDrawable.add(getTempGradientDrawable(ContextCompat.getColor(context, R.color.gradient_3_start),ContextCompat.getColor(context, R.color.gradient_3_end)));
        mGradientDrawable.add(getTempGradientDrawable(ContextCompat.getColor(context, R.color.gradient_4_start),ContextCompat.getColor(context, R.color.gradient_4_end)));
    }

    private GradientDrawable getTempGradientDrawable(int startColor, int endColor){
        GradientDrawable drawable =new GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[]{startColor,endColor});
        drawable.setDither(true);
        drawable.setGradientCenter(drawable.getIntrinsicWidth()/8,drawable.getIntrinsicHeight()/2);
        drawable.setCornerRadius(20);
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        drawable.setUseLevel(true);
        return drawable;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicsAdapter.TopicCustomViewHolder holder, int position) {
        String topicName = mArrayList.get(position);
        holder.mTextView.setText(topicName);

        holder.mTopicRelativeLayout.setBackground(mGradientDrawable.get(position%4));
        holder.mImageView.setImageResource(R.drawable.menu);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class TopicCustomViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mTextView;
        private RelativeLayout mTopicRelativeLayout;
        public TopicCustomViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView =(ImageView) itemView.findViewById(R.id.topicImageView);
            mTextView = (TextView) itemView.findViewById(R.id.topicTextView);
            mTopicRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.topicRelativeLayout);
        }
    }
}
