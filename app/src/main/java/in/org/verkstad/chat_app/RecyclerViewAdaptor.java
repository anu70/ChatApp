package in.org.verkstad.chat_app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by anu on 3/12/2016.
 */
public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {
    LayoutInflater inflater;
    String[] messages,sender_id;
    Context context;

    public RecyclerViewAdaptor(Context context,String[] messages,String[] sender_id){
        inflater=LayoutInflater.from(context);
        this.messages=messages;
        this.sender_id=sender_id;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("positioncvghjkl",""+position);
        int viewType;
        ContextWrapper contextWrapper = new ContextWrapper(context);
        SharedPreferences sharedPreferences = contextWrapper.getSharedPreferences("data",Context.MODE_PRIVATE);
        if(sender_id[position].equals(sharedPreferences.getString("regID", null))){
            viewType = 0;
        }
        else {
            viewType = 1;
        }
        return viewType;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==0){
            view = inflater.inflate(R.layout.chat_row2,parent,false);
        }
        else {
            view=inflater.inflate(R.layout.chat_row,parent,false);
        }

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String message = messages[position];
        holder.chat_msg_received.setText(message);
    }

    @Override
    public int getItemCount() {
        return messages.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView chat_msg_received;

        public ViewHolder(View itemView) {
            super(itemView);
            chat_msg_received = (TextView) itemView.findViewById(R.id.chat_message_received);
        }
    }
}
