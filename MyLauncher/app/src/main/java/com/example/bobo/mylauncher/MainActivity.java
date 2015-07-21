package com.example.bobo.mylauncher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    GridView appsGrid;
    private List<ResolveInfo> apps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadApps();
        appsGrid = (GridView) findViewById(R.id.apps_list);
        appsGrid.setAdapter(new AppsAdapter());
        appsGrid.setOnItemClickListener(clickListener);
    }
    private AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ResolveInfo info = apps.get(i);

            String pkg = info.activityInfo.packageName;

            String cls = info.activityInfo.name;
            ComponentName componet = new ComponentName(pkg, cls);

            Intent intent = new Intent();
            intent.setComponent(componet);
            startActivity(intent);
        }
    };
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        new ImageView(MainActivity.this);

        apps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }
    public class AppsAdapter extends BaseAdapter {

        public AppsAdapter(){
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public Object getItem(int i) {
            return apps.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView iv;

            if(view == null){
                iv = new ImageView(MainActivity.this);
                iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
                iv.setLayoutParams(new GridView.LayoutParams(150, 150));
            } else {
                iv = (ImageView) view;
            }
            ResolveInfo info = apps.get(i);
            iv.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            return iv;
        }
    }
}
