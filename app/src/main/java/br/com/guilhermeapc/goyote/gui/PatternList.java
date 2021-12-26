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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.guilhermeapc.goyote.R;
import br.com.guilhermeapc.goyote.dao.DatabaseBaseObject;
import br.com.guilhermeapc.goyote.dao.EncryptionMethod;

public class PatternList extends AppCompatActivity {
    List menuList;
    ListView listView;
    Context actualContext;
    DatabaseBaseObject databaseAcessObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        menuList = new ArrayList<String>();
        actualContext = this;
        setContentView(R.layout.activity_list);
        super.onCreate(savedInstanceState);
        menuList.add("First pattern");
        menuList.add("Second pattern");

        listView = new MenuListView(this,menuList);
        listView = findViewById(R.id.configuration_list);
        listView.setAdapter(new MyListAdapter(this,R.layout.menu_items,menuList));
        Intent patternDefinitionIntent=getIntent();
        final Intent intent = new Intent(PatternList.this,PatternDefinition.class);

        if((patternDefinitionIntent.getStringExtra("password0")!=null)) {
            intent.putExtra("password3",patternDefinitionIntent.getStringExtra("password0"));
        }else if(patternDefinitionIntent.getStringExtra("password1")!=null){
            if(!patternDefinitionIntent.getStringExtra("password3").equals(patternDefinitionIntent.getStringExtra("password1"))){
                Toast.makeText(this,"The password must to be equal", Toast.LENGTH_LONG).show();
            }else if(patternDefinitionIntent.getStringExtra("password3").equals(patternDefinitionIntent.getStringExtra("password1"))){
                EncryptionMethod encryptionMethod = new EncryptionMethod();
                databaseAcessObject = new DatabaseBaseObject(this,null,null,1);
                databaseAcessObject.subscribeOnDB(encryptionMethod.encrypt(patternDefinitionIntent.getStringExtra("password3")));

            }

        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0||i==1){
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
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
                menuListDescription.add("Type the first password");
                menuListDescription.add("Confirm the pattern");


                mainViewHolder.title.setText(menuList.get(position).toString());

                mainViewHolder.description.setText(menuListDescription.get(position).toString());





            }

            return convertView;
        }
        public class ViewHolder{
            TextView title;
            TextView description;
        }
    }
}

