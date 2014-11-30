package com.claudio.passwords;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class MainActivity extends ListActivity {


    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvId = (TextView) view.findViewById(R.id.tvId);
                String passwordId = tvId.getText().toString();
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                intent.putExtra("id", Integer.parseInt(passwordId));
                startActivity(intent);
            }
        });

        registerForContextMenu(lv);
    }

    @Override
    public void onResume() {
        super.onResume();
        populateList();
    }

    private void populateList() {
        PasswordDao dao = new PasswordDao(this);
        List<Password> passwords = dao.getAllPasswords();
        PasswordAdapter adapter = new PasswordAdapter(this, passwords);
        setListAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(getString(R.string.menu_password_title));
        getMenuInflater().inflate(R.menu.menu_listview_password, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_add:
                intent = new Intent(this, PasswordActivity.class);
                intent.putExtra("id", 0);
                startActivity(intent);
                return true;

            case R.id.action_export:
                intent = new Intent();
                intent.setAction(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, "Contraseñas");
                startActivityForResult(intent, READ_REQUEST_CODE);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        Password password = (Password) getListAdapter().getItem(info.position);

        switch (item.getItemId()){
            /*
            case R.id.action_edit:
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                intent.putExtra("id", password.getId());
                startActivity(intent);
                break;
            */
            case R.id.action_delete:
                PasswordDao dao = new PasswordDao(this);
                dao.delete(password.getId());
                populateList();
                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                PasswordDao dao = new PasswordDao(this);
                List<Password> passwords = dao.getAllPasswords();
                StringBuilder sb = new StringBuilder();

                sb.append("descripcion, usuario, password");
                sb.append(System.getProperty("line.separator"));

                for(Password p:passwords) {
                    sb.append("\"");
                    sb.append(p.getDescription());
                    sb.append("\",\"");
                    sb.append(p.getUsername());
                    sb.append("\",\"");
                    sb.append(p.getPassword());
                    sb.append("\"");
                    sb.append(System.getProperty("line.separator"));
                }

                Uri uri = resultData.getData();

                try{
                    ParcelFileDescriptor file = this.getContentResolver().openFileDescriptor(uri, "w");
                    FileOutputStream fileout = new FileOutputStream(file.getFileDescriptor());

                    fileout.write(sb.toString().getBytes());
                    fileout.close();
                    file.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
