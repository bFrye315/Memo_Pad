package edu.jsu.mcis.cs408.memopad;

import androidx.recyclerview.widget.RecyclerView;
import edu.jsu.mcis.cs408.memopad.databinding.MemoItemBinding;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private MemoItemBinding binding;
    private List<Memo> data;

    public RecyclerViewAdapter(List<Memo> data){
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        binding = MemoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setMemo(data.get(position));
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private Memo memo;
        private TextView numLabel;
        private TextView memoLabel;

        public ViewHolder(View itemView){
            super(itemView);
        }

        public Memo getMemo(){
            return memo;
        }
        public void setMemo(Memo memo){
            this.memo = memo;
        }
        public void bindData(){
            if (numLabel == null){
                numLabel = (TextView) itemView.findViewById(R.id.memoNumLabel);
            }
            if(memoLabel == null){
                memoLabel = (TextView) itemView.findViewById(R.id.memoMemoLabel);
            }
            numLabel.setText(String.valueOf(memo.getId())+ ": ");
            memoLabel.setText(memo.getMemo());
        }
    }
}
