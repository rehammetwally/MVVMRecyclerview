package com.rehammetwally.mvvmrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rehammetwally.mvvmrecyclerview.adapter.RecyclerAdapter;
import com.rehammetwally.mvvmrecyclerview.models.NicePlace;
import com.rehammetwally.mvvmrecyclerview.viewmodels.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView recyclerview;
    private ProgressBar progressbar;
    private RecyclerAdapter adapter;
    private MainActivityViewModel mainActivityViewModel;
    private NicePlace nicePlace;
    /*
    *** MVVM Model View ViewModel ***
    * is the best way to structure code for these reasons
    * 1-ui component are kept away from the business logic
    * 2-business logic is kept away from database operations
    * 3-easy to understand and read
    * 4-a lot less worry when it comes to manage life cycle events
    * (screen rotations and if the user closes app and then comes back to it several hours later)
    *
    * Benefits of MVVM:
    * 1- life cycle state of the app will be maintained
    * 2- the app will be in the same position the same exact state as when the user left it
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab=findViewById(R.id.fab);
        recyclerview=findViewById(R.id.recyclerview);
        progressbar=findViewById(R.id.progressbar);

        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init();
        mainActivityViewModel.getNicePlaces().observe(this, new Observer<List<NicePlace>>() {
            @Override
            public void onChanged(List<NicePlace> nicePlaces) {
                adapter.notifyDataSetChanged();
            }
        });
        mainActivityViewModel.getIsUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    showProgressBar();
                else
                    hideProgressBar();
                recyclerview.smoothScrollToPosition(mainActivityViewModel.getNicePlaces().getValue().size()-1);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nicePlace=new NicePlace(
                        "https://i.imgur.com/ZcLLrkY.jpg",
                        "Washington"
                );
                mainActivityViewModel.addNewValue(nicePlace);
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter=new RecyclerAdapter(mainActivityViewModel.getNicePlaces().getValue(),this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(adapter);
    }

    private void showProgressBar(){
        progressbar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        progressbar.setVisibility(View.GONE);
    }
}
