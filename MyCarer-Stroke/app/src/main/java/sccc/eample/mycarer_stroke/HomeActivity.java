package sccc.eample.mycarer_stroke;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sqLiteHelper = new SQLiteHelper(this, "DATABASE.sqlite", null, 1);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.home_container, new HomeFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Cursor homedrawer = sqLiteHelper.getRowFromIDPatient();
        if (homedrawer.moveToNext()) {
            String name = homedrawer.getString(1);
            byte[] blah = homedrawer.getBlob(4);

            View header = navigationView.getHeaderView(0);

            TextView namedrawer = (TextView) header.findViewById(R.id.usernamedrawer);
            namedrawer.setText(name);

            ImageView imageView = (ImageView) header.findViewById(R.id.imgdrawer);
            Bitmap bitmap = BitmapFactory.decodeByteArray(blah, 0, blah.length);
            imageView.setImageBitmap(bitmap);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new HomeFragment());
                        fragmentTransaction.commit();
                        /*getSupportActionBar().setTitle("Home");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.profile_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new ProfileFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Profile");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.SOS_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new SOSFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("SOS");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.strokeinfo_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new StrokeInfoFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Stroke Info");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.relatives_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new RelationListFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Relatives");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.texttospeech_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new TextToSpeechFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Text-To-Speech");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.memorygame_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new MemoryGameFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Memory Game");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.physiotherapy_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new PhysiotherapyFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Physiotherapy");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.dwd_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new DepressionFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Physiotherapy");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;

                    case R.id.help_id:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_container, new HelpFragment());
                        fragmentTransaction.commit();
                        fragmentTransaction.addToBackStack(null);
                        /*getSupportActionBar().setTitle("Physiotherapy");*/
                        item.setChecked(true);
                        drawer.closeDrawers();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    /**/
}
