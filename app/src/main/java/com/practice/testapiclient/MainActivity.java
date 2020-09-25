package com.practice.testapiclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  EditText firstURL;
  EditText secondURL;
  Button btnFirstURL, btnSecondURL, btnSQLite, btnGoogleLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init();
  }

  private void init() {
    firstURL = findViewById(R.id.firstURL);
    secondURL = findViewById(R.id.secondURL);
    btnFirstURL = findViewById(R.id.btnFirstURL);
    btnSecondURL = findViewById(R.id.btnSecondURL);
    btnSQLite = findViewById(R.id.btnSQLite);
    btnGoogleLocation = findViewById(R.id.btnGoogleLocation);
    btnFirstURL.setOnClickListener(this);
    btnSecondURL.setOnClickListener(this);
    btnSQLite.setOnClickListener(this);
    btnGoogleLocation.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    int viewID = v.getId();
    switch (viewID) {
      case R.id.btnFirstURL:
        startActivity(new Intent(MainActivity.this, FirstURLActivity.class));
        break;
      case R.id.btnSecondURL:
        startActivity(new Intent(MainActivity.this, SecondURLActivity.class));
        break;
      case R.id.btnSQLite:
        startActivity(new Intent(MainActivity.this, SQLiteActivity.class));
        break;
      case R.id.btnGoogleLocation:
        startActivity(new Intent(MainActivity.this, GoogleLocationActivity.class));
        break;
    }
  }
}
