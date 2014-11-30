package com.claudio.passwords;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends Activity{
    EditText tvUsername;
    EditText tvPassword;
    EditText tvDescription;
    private int passwordId = 0;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        tvDescription = (EditText) findViewById(R.id.tvDescription);
        tvUsername = (EditText) findViewById(R.id.tvUsername);
        tvPassword = (EditText) findViewById(R.id.tvPassword);

        Intent intent = getIntent();
        passwordId =intent.getIntExtra("id", 0);

        PasswordDao dao = new PasswordDao(this);
        Password password = new Password();

        if (passwordId > 0)
            password = dao.getPasswordById(passwordId);

        tvUsername.setText(password.getUsername());
        tvPassword.setText(password.getPassword());
        tvDescription.setText(password.getDescription());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                boolean dataOk = true;

                if (tvUsername.getText().length() == 0) {
                    tvUsername.setError(getString(R.string.error_null_username));
                    dataOk = false;
                }

                if (tvDescription.getText().length() == 0) {
                    tvDescription.setError(getString(R.string.error_null_description));
                    dataOk = false;
                }

                if (tvPassword.getText().length() == 0) {
                    tvPassword.setError(getString(R.string.error_null_password));
                    dataOk = false;
                }

                if (dataOk) {
                    PasswordDao dao = new PasswordDao(this);
                    Password password = new Password();
                    password.setDescription(tvDescription.getText().toString());
                    password.setUsername(tvUsername.getText().toString());
                    password.setPassword(tvPassword.getText().toString());
                    password.setId(passwordId);

                    if (passwordId == 0) {
                        passwordId = dao.insert(password);
                        Toast.makeText(this, getString(R.string.msg_insert_ok), Toast.LENGTH_SHORT).show();
                    } else {
                        dao.update(password);
                        Toast.makeText(this, getString(R.string.msg_update_ok), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}