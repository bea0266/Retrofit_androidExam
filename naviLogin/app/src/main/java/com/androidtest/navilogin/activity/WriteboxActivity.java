package com.androidtest.navilogin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidtest.navilogin.R;
import com.androidtest.navilogin.fragment.BoardFragment;

public class WriteboxActivity extends AppCompatActivity {
    private ActionBar actionBar;
    EditText etTitle, etDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writebox);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etDesc = (EditText) findViewById(R.id.etDesc);

        setSupportActionBar(toolbar);
        actionBar=getSupportActionBar();

        actionBar.setTitle("게시글 작성");






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.write_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_cancle:
                finish();
                break;
            case R.id.action_write:
                String title = etTitle.getText().toString();
                String description = etDesc.getText().toString();
                Intent intent = new Intent(getApplicationContext(), BoardFragment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                setResult(RESULT_OK,intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
