package com.example.nao.nao_connect;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nao.nao_connect.model.Command;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity{


    private ObjectListArrayAdapter objectListAdapter;

    private MainContentListArrayAdapter mainContentAdapter;

    private ClipData dragData;

    private DynamicListView mainContentListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.lv_main_content).setOnDragListener(new MyDragListener());

        //Array ObjectList
        final ListView objectListListView = (ListView) findViewById(R.id.lv_object_list);



        objectListAdapter = new ObjectListArrayAdapter(this, R.layout.object_list, Command.getAviableCommands());
        objectListListView.setAdapter(objectListAdapter);


        //Array Maincontent
        mainContentListView = (DynamicListView) findViewById(R.id.lv_main_content);

        final ArrayList<Command> mainContentList = new ArrayList<>();
        mainContentAdapter = new MainContentListArrayAdapter(this, R.layout.main_content, mainContentList);

        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(mainContentAdapter);
        animationAdapter.setAbsListView(mainContentListView);


        mainContentListView.setAdapter(animationAdapter);

        mainContentListView.enableDragAndDrop();
        mainContentListView.setDraggableManager(new TouchViewDraggableManager(R.id.lv_main_content));





    }

    //Drop
    class MyDragListener implements OnDragListener {


        @Override
        public boolean onDrag(View v, DragEvent event) {
            //Log.i("TEST", "onDrag-------------------------");
            int action = event.getAction();
            switch (event.getAction()) {

                case DragEvent.ACTION_DROP:


                    String index = dragData.getDescription().getLabel().toString();



                    int x = Integer.parseInt(index);
                    Log.i("x_TEST",x + "");


                    //int y = 1;
                    Log.i("TEST", "Element wurde gedroppt");
                    mainContentAdapter.add(Command.getAviableCommands().get(x).clone());
                    break;
            }

            return true;
        }
    }

    //ArrayAdapter für Object List
    private class ObjectListArrayAdapter extends ArrayAdapter<Command> {


        public ObjectListArrayAdapter(Context context, int textViewResourceId, List<Command> values) {
            super(context, textViewResourceId, values);
        }

        //Drag and Drop
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View result = inflater.inflate(R.layout.object_list, parent, false);

            final Command command = getItem(position);

            TextView label = (TextView) result.findViewById(R.id.tv_ObjectList_label);
            label.setText(command.getLabel());

            Button dragButton = (Button) result.findViewById(R.id.btn_ObjectList_Drag);
            dragButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int index = Command.getAviableCommands().indexOf(command);
                    Log.i("INDEX_TEST", String.valueOf(index));

                    ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                    dragData = new ClipData(String.valueOf(index), mimeTypes, item);

                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(result);

                    result.startDrag(dragData, myShadow, null, 0);
                    //Log.i("TEST", String.valueOf(event.getAction()));
                    return false;
                }
            });

            return result;
        }


    }



    //Array Adapter für Main Content
    private class MainContentListArrayAdapter extends ArrayAdapter<Command> {

        public MainContentListArrayAdapter(Context context, int textViewResourceId, List<Command> objects) {
            super(context, textViewResourceId, objects);
        }

        //Drag and Drop
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View result = inflater.inflate(R.layout.main_content, parent, false);

            final Command command = getItem(position);

            TextView label = (TextView) result.findViewById(R.id.tv_MainContent_label);
            label.setText(command.getLabel());

            //Remove List Object
            ImageButton rmv_button = (ImageButton) result.findViewById(R.id.btn_MainContent_remove);
            rmv_button.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(final View v, MotionEvent event) {

                    result.animate().setDuration(200).alpha(0).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    mainContentAdapter.remove(command);
                                    mainContentAdapter.notifyDataSetChanged();
                                    result.setAlpha(1);
                                }
                    });

                    return false;
                }

            });
            return result;
        }

        @Override
        public long getItemId(int position) {
            return super.getItem(position).hashCode();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);


        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
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
}