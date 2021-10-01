package com.androidtest.retroboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ButtonBarLayout;

public class WriteActivity extends Activity {
    Button btnClear, btnWrite;
    EditText etTitle, etWriter, etDesc; //추후 사용할것들
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_view);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnWrite = (Button) findViewById(R.id.btnWrite);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etWriter = (EditText) findViewById(R.id.etWriter);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WriteActivity.this, MainActivity.class);
                intent.putExtra("Title", etTitle.getText().toString());
                intent.putExtra("Writer", etWriter.getText().toString());
                //intent.putExtra("Description", etDesc.getText().toString()); // 추후에 사용
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}
