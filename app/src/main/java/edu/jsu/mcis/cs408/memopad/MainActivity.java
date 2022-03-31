package edu.jsu.mcis.cs408.memopad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import edu.jsu.mcis.cs408.memopad.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        db = new DatabaseHandler(this, null, null, 1);
        updateRecyclerView();

        binding.AddMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemo();
            }
        });
        binding.DelMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMemo();
            }
        });
    }

    public void addMemo(){
        String memo = binding.MemoInput.getText().toString();
        if (memo.isEmpty()){
            Toast toast = Toast.makeText(binding.getRoot().getContext(), "No memo to add", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            db.addMemo(new Memo(memo));
            updateRecyclerView();
        }

    }

    public void deleteMemo(){
        String memo = binding.MemoDelete.getText().toString();
        db.deleteMemo(Integer.parseInt(memo));
        updateRecyclerView();
    }

    private void updateRecyclerView(){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(db.getAllMemosAsList());
        binding.recyclerView2.setHasFixedSize(true);
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView2.setAdapter(adapter);
    }
}