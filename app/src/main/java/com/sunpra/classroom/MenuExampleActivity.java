package com.sunpra.classroom;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_example);
        Button clickMeBtn = findViewById(R.id.clickMeBtn);

        clickMeBtn.setOnClickListener((view) -> {
            PopupMenu popupMenu = new PopupMenu(MenuExampleActivity.this, view);

            popupMenu.setOnMenuItemClickListener((item) -> {
                if(item.getItemId() == R.id.deleteStudent){
                    Toast.makeText(this, "Delete Clicked", Toast.LENGTH_SHORT).show();
                }
                return true;
            });

            popupMenu.inflate(R.menu.student_litem_menu);
            popupMenu.show();
        });


        //region Context Menu
        Button clickMeAnotherBtn = findViewById(R.id.clickMeBtnAnother);
        registerForContextMenu(clickMeAnotherBtn);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_litem_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.deleteStudent){
            Toast.makeText(this, "Delete Clicked", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            return super.onContextItemSelected(item);
        }
    }
}