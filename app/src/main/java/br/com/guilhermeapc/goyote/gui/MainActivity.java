package br.com.guilhermeapc.goyote.gui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import br.com.guilhermeapc.goyote.R;
import br.com.guilhermeapc.goyote.services.ApplicationWatcher;
import br.com.guilhermeapc.goyote.dao.DatabaseBaseObject;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List menuList;
    List menuListDescription;
    Context actualContext;
    DatabaseBaseObject databaseAcessObject;
    ApplicationWatcher applicationWatcher;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        actualContext = this;
        applicationWatcher = new ApplicationWatcher();
        menuList = new ArrayList<String>();
        menuListDescription = new ArrayList<String>();
        setContentView(R.layout.activity_list);
        super.onCreate(savedInstanceState);
        menuList.add("Method of authentication");
        //In that lines are defining the one item ListView
        menuList.add("");
        menuListDescription.add("");
        databaseAcessObject = new DatabaseBaseObject(this, null, null, 1);
        //In that lines are defining the item of application list and the subtitle of the password method
        try {
            if (databaseAcessObject.getAutenticationMethod() != null) {
                menuListDescription.add(0, databaseAcessObject.getAutenticationMethod());
                //In that lines are removing the one item ListView
                menuList.remove(1);
                menuListDescription.remove(1);
                //In that lines are adding new one
                menuList.add(1, "Applications List");
                menuListDescription.add(1, "List of applications for choose which have to be blocked");
            }
        }catch (CursorIndexOutOfBoundsException e){
            menuListDescription.add(0, "None");
        }

        listView = new MenuListView(this,menuList);
        listView = findViewById(R.id.configuration_list);
        listView.setAdapter(new MyListAdapter(this,R.layout.menu_items,menuList));



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    //That is storing the selected option
                    final ArrayList which = new ArrayList();
                    try {
                        Intent intent;
                        if (databaseAcessObject.getAutenticationMethod().equals("Password")) {
                            intent = new Intent(MainActivity.this, NumericDefinition.class);
                            startActivity(intent);
                        } else if (databaseAcessObject.getAutenticationMethod().equals("Pattern")) {
                            intent = new Intent(MainActivity.this, PatternDefinition.class);
                            startActivity(intent);
                        }
                        databaseAcessObject = new DatabaseBaseObject(actualContext, null, null, 1);


                    }catch (CursorIndexOutOfBoundsException e){
                        AlertDialog.Builder builder = new AlertDialog.Builder(actualContext);
                        builder.setTitle("Authetication options")
                                .setSingleChoiceItems(R.array.method, -1, new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        which.add(i);


                                    }
                                })
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent;
                                        if (which.get(0).equals(0)) {
                                            databaseAcessObject.setAutenticationMethod("Password");
                                            intent = new Intent(MainActivity.this, NumericList.class);
                                            startActivity(intent);
                                        } else if (which.get(0).equals(1)) {
                                            databaseAcessObject.setAutenticationMethod("Pattern");
                                            intent = new Intent(MainActivity.this, PatternDefinition.class);
                                            startActivity(intent);
                                        }
                                        databaseAcessObject = new DatabaseBaseObject(actualContext, null, null, 1);

                                    }
                                }).show();
                        databaseAcessObject = new DatabaseBaseObject(actualContext, null, null, 1);
                    }




                }else if(i==1){
                    Intent intent = new Intent(MainActivity.this,ApplicationsList.class);
                    startActivity(intent);
                }
            }
        });




    }
    private class MyListAdapter extends ArrayAdapter<String> {
        int layout;
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewHolder = null;

            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
                ViewHolder viewHolderList = new ViewHolder();
                viewHolderList.title = (TextView)convertView.findViewById(R.id.mode);
                viewHolderList.description = (TextView)convertView.findViewById(R.id.mode_discription);
                convertView.setTag(viewHolderList);
            }else{
                mainViewHolder = (ViewHolder)convertView.getTag();
                mainViewHolder.title.setText(menuList.get(position).toString());
                mainViewHolder.description.setText(menuListDescription.get(position).toString());

            }
            return convertView;
        }

    }

    public class ViewHolder{
        TextView title;
        TextView description;
    }
}
