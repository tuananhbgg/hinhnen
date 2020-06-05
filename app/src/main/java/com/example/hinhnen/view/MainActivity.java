package com.example.hinhnen.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.hinhnen.BuildConfig;
import com.example.hinhnen.R;

public class MainActivity extends AppCompatActivity {
    private Button btnOffline, btnAlbum1,btnAlbum2;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Offline");

        btnOffline = findViewById(R.id.btnOffline);
        btnAlbum1 = findViewById(R.id.btnAlbum1);
        btnAlbum2 = findViewById(R.id.btnAlbum2);
        frameLayout = findViewById(R.id.frameLayout);

        btnOffline.setOnClickListener(onClick);
        btnAlbum1.setOnClickListener(onClick);
        btnAlbum2.setOnClickListener(onClick);

        btnOffline.setBackgroundResource(R.drawable.mybutton_click);
        ChangeLayout(new Offline_Fragment());

    }

    private void ChangeLayout(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnOffline:
                    ChangeLayout(new Offline_Fragment());
                    getSupportActionBar().setTitle("Offline");
                    btnOffline.setBackgroundResource(R.drawable.mybutton_click);
                    btnAlbum1.setBackgroundResource(R.drawable.mybutton_selector);
                    btnAlbum2.setBackgroundResource(R.drawable.mybutton_selector);
                    break;
                case R.id.btnAlbum1:

                    ChangeLayout(new Album1_Fragment());
                    getSupportActionBar().setTitle("Album 1");
                    btnAlbum1.setBackgroundResource(R.drawable.mybutton_click);
                    btnOffline.setBackgroundResource(R.drawable.mybutton_selector);
                    btnAlbum2.setBackgroundResource(R.drawable.mybutton_selector);
                    break;
                case R.id.btnAlbum2:
                    ChangeLayout(new Album2_Fragment());
                    getSupportActionBar().setTitle("Album 2");
                    btnAlbum2.setBackgroundResource(R.drawable.mybutton_click);
                    btnOffline.setBackgroundResource(R.drawable.mybutton_selector);
                    btnAlbum1.setBackgroundResource(R.drawable.mybutton_selector);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mn_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Tai ung dung tai: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

