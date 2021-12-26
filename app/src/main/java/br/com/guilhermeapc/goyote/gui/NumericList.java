package br.com.guilhermeapc.goyote.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.guilhermeapc.goyote.R;
import br.com.guilhermeapc.goyote.dao.DatabaseBaseObject;
import br.com.guilhermeapc.goyote.dao.EncryptionMethod;

public class NumericList extends AppCompatActivity {
    NumericDefinition numericDefinition;
    List menuList;
    MenuListView listView;
    //Initializing the switch
    Switch randomSwitch;
    DatabaseBaseObject databaseAcessObject;
    Context actualContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        menuList = new ArrayList<String>();
        actualContext = this;


        setContentView(R.layout.numeric_list);

        //Instatiating the switch
        randomSwitch = findViewById(R.id.random_switch);
        super.onCreate(savedInstanceState);
        // In that lines are defining the title of the menu list
        menuList.add("Random password");
        menuList.add("First password");
        menuList.add("Second password");
        listView = new MenuListView(this,menuList);
        listView =  findViewById(R.id.numeric_configuration_list);
        databaseAcessObject = new DatabaseBaseObject(this,null,null,1);


        




        listView.setAdapter(new MyListAdapter(this,R.layout.menu_items,menuList));
        randomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              databaseAcessObject = new DatabaseBaseObject(actualContext,null,null,1);
              databaseAcessObject.setRandomNumber(b);

            }
        });

            if(databaseAcessObject.getRandomNumber()==true){
                randomSwitch.setChecked(true);
            }

        Intent numericDefinitionIntent = getIntent();
        final Intent intent = new Intent(NumericList.this,NumericDefinition.class);



        if((numericDefinitionIntent.getStringExtra("password1") != null)) {
            intent.putExtra("password4", numericDefinitionIntent.getStringExtra("password1"));
        }else if((numericDefinitionIntent.getStringExtra("password2")!=null) && (numericDefinitionIntent.getStringExtra("password4")!=null)){
            if(!numericDefinitionIntent.getStringExtra("password4").equals(numericDefinitionIntent.getStringExtra("password2"))){
                Toast.makeText(this,"The password must to be equal", Toast.LENGTH_LONG).show();
            }else if(numericDefinitionIntent.getStringExtra("password4")==numericDefinitionIntent.getStringExtra("password2")){
                EncryptionMethod encryptionMethod = new EncryptionMethod();
                databaseAcessObject.subscribeOnDB(encryptionMethod.encrypt(numericDefinitionIntent.getStringExtra("password2")));
            }
        }





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==1||i==2){
                    intent.putExtra("menuPosition",i);
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
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            List menuListDescription = new ArrayList<String>();
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
                ViewHolder viewHolderList = new ViewHolder();
                viewHolderList.title = (TextView)convertView.findViewById(R.id.mode);
                viewHolderList.description = (TextView)convertView.findViewById(R.id.mode_discription);
                convertView.setTag(viewHolderList);


            }else{
                mainViewHolder = (ViewHolder)convertView.getTag();
                menuListDescription.add("Define if you want to change the number order in every new screen");
                menuListDescription.add("Type the first password");
                menuListDescription.add("Confirm password");


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

