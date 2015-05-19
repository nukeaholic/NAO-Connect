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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity{

    private ArrayList<Command> avialableCommands;

    private OptionListArrayAdapter adapterol;

    private MainContentListArrayAdapter adaptermc;

    private ClipData dragData;

    private ListView listviewmc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.lv_main_content).setOnDragListener(new MyDragListener());

        //Array ObjectList
        final ListView listviewol = (ListView) findViewById(R.id.lv_object_list);

        avialableCommands = new ArrayList<>();
        avialableCommands.add(new Command("Nach vorne gehen"));
        avialableCommands.add(new Command("Nach Links gehen"));
        avialableCommands.add(new Command("Nach rechts gehen"));
        avialableCommands.add(new Command("Rückwärts gehen"));
        avialableCommands.add(new Command("Sich nach links drehen"));
        avialableCommands.add(new Command("Sich nach rechts drehen"));
        avialableCommands.add(new Command("Sich umdrehen"));
        avialableCommands.add(new Command("Winken"));
        avialableCommands.add(new Command("Hallo sagen"));
        avialableCommands.add(new Command("Linker Arm hoch"));
        avialableCommands.add(new Command("Rechter Arm hoch"));
        avialableCommands.add(new Command("Beide Arme hoch"));
        avialableCommands.add(new Command("Rechter Arm runter"));
        avialableCommands.add(new Command("Linker Arm runter"));
        avialableCommands.add(new Command("Beide Arme runter"));

        adapterol = new OptionListArrayAdapter(this, R.layout.object_list, avialableCommands);
        listviewol.setAdapter(adapterol);


        //Array Maincontent
        listviewmc = (ListView) findViewById(R.id.lv_main_content);

        final ArrayList<Command> listmc = new ArrayList<>();
        adaptermc = new MainContentListArrayAdapter(this, R.layout.main_content, listmc);
        listviewmc.setAdapter(adaptermc);


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
                    adaptermc.add(avialableCommands.get(x).clone());
                    break;
            }

            return true;
        }
    }

    //ArrayAdapter für Object List
    private class OptionListArrayAdapter extends ArrayAdapter<Command> {


        public OptionListArrayAdapter(Context context, int textViewResourceId, List<Command> values) {
            super(context, textViewResourceId, values);
        }

        //Drag and Drop
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View result = inflater.inflate(R.layout.object_list, parent, false);

            final Command command = getItem(position);

            TextView label = (TextView) result.findViewById(R.id.tvol_label);
            label.setText(command.getLabel());

            Button dragButton = (Button) result.findViewById(R.id.btn_Drag_ol);
            dragButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int index = avialableCommands.indexOf(command);
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

            TextView label = (TextView) result.findViewById(R.id.tvmc_label);
            label.setText(command.getLabel());

            //Remove List Object
            ImageButton rmv_button = (ImageButton) result.findViewById(R.id.btn_remove);
            rmv_button.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(final View v, MotionEvent event) {

                    result.animate().setDuration(200).alpha(0).withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    adaptermc.remove(command);
                                    adaptermc.notifyDataSetChanged();
                                    result.setAlpha(1);
                                }
                    });

                    return false;
                }

            });
            return result;
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