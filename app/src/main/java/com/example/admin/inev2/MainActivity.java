package com.example.admin.inev2;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    UserSessionManager session;

    private Toolbar appbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    ExpandableListView elv;
    ListView lvmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        navView = (NavigationView)findViewById(R.id.navview);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.itmem1:
                                fragment = new Fragment1();
                                fragmentTransaction = true;
                                break;
                            case R.id.item2:
                                fragment = new Fragment2();
                                fragmentTransaction = true;
                                break;
                            case R.id.item3:
                                fragment = new Fragment3();
                                fragmentTransaction = true;
                                break;
                            case R.id.item4:
                                Log.i("NavigationView", "Pulsada opción 4");
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle(menuItem.getTitle());

                                break;
                            case R.id.item5:
                                Log.i("NavigationView", "Pulsada opción 5");
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle(menuItem.getTitle());

                                break;
                            case R.id.item6:
                                Log.i("NavigationView", "Pulsada opción 6");
                                menuItem.setChecked(true);
                                getSupportActionBar().setTitle(menuItem.getTitle());

                                break;
                            case R.id.nav_log_out:
                                session.logoutUser();
                                Log.i("NavigationView", "Cerrar session");
                                break;
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        TextView lblName = (TextView) findViewById(R.id.username);
        TextView lblEmail = (TextView) findViewById(R.id.email);



        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_SHORT).show();



        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if(session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        String name = user.get(UserSessionManager.KEY_NAME);

        // get email
        String email = user.get(UserSessionManager.KEY_EMAIL);

        // Show user data on activity
        // lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        //lblEmail.setText(Html.fromHtml("Email: <b>" + email + "</b>"));

        lblEmail.setText("");
        lblName.setText("");
        lblName.setText( name);
        lblEmail.setText(email);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void prueba(View v){
        //metodo del boton glotante
        //Intent act=new Intent(this,Main2Activity.class);
        Toast.makeText(this,"flotante1",Toast.LENGTH_SHORT).show();
       // startActivity(act);

    }
    //primer comentarios desde mi cuenta marco
    public void prueba_metodo(){
        String comentario="agregando codigo";
    }

}
