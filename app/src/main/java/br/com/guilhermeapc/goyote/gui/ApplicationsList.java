package br.com.guilhermeapc.goyote.gui;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.guilhermeapc.goyote.R;
import br.com.guilhermeapc.goyote.dao.DatabaseBaseObject;
import br.com.guilhermeapc.goyote.services.ApplicationWatcher;

public class ApplicationsList extends AppCompatActivity {

    ImageButton locking_app;
    DatabaseBaseObject databaseAcessObject = new DatabaseBaseObject(this,null,null,1);
    List applicationConfiguration;
    ListView listView;
    Context actualContext;
    ApplicationWatcher applicationWatcher = new ApplicationWatcher();
    //This method is used to extract the class name
    private String extractClassFromApplicationInfo(String applicationList){
        Matcher matcher = Pattern.compile("\\w{2,3}\\.\\w{2,15}\\..{1,15}\\..{1,15}.*\\}").matcher(applicationList);
        //That string going to recieve the result of search and return to the method
        String className = "";
        while (matcher.find()){
            //The result of search
            className = matcher.group();
            className = className.substring(0,(className.length())-1);

        }
        return className;


    }
    private boolean isApplicationOnTheList(String className){
       boolean resultFromQuery = false;
       if(databaseAcessObject.viewApplicationsColumns()==null) {
           resultFromQuery = false;
       }
       else {
           String comaSeparatedApplicationList = databaseAcessObject.viewApplicationsColumns();
           if(comaSeparatedApplicationList.contains(",")==true) {
               String applicationList[] = comaSeparatedApplicationList.split(",");
               for (int i = 0; i <= (applicationList.length - 1); i++) {
                   if (applicationList[i].equals(className)) {
                       resultFromQuery = true;
                   }
               }
           }else{
               if(comaSeparatedApplicationList.equals(className)){
                 resultFromQuery = true;
               }
           }

       }
       return resultFromQuery;
    }
    private int applicationPositionFromDatabase(String className){
        applicationConfiguration = new ArrayList<String>();
        String comaSeparatedApplicationList = databaseAcessObject.viewApplicationsColumns();
        int resultFromQuery = 0;
        if(databaseAcessObject.viewApplicationsColumns()!=null){
        String applicationList[] = comaSeparatedApplicationList.split(",");
        for(int i=0;i<=(applicationList.length-1);i++) {
            //In that line is adding content to the list
            applicationConfiguration.add(applicationList[i]);
            //Search inside of the list for the class name
            if (applicationList[i].equals(className)) {
                //In that linha is defining the position
                resultFromQuery = i;
            }
        }
        }
        return resultFromQuery;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.application_list_activity);
        listView = findViewById(R.id.applications_list);
        super.onCreate(savedInstanceState);
        List applicationsList = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
        listView.setAdapter(new MyListAdapter(this,R.layout.application_list,applicationsList));
        actualContext = this;

    }
    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;
        public MyListAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder mainViewHolder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout,parent,false);
                ViewHolder viewHolderList = new ViewHolder();
                viewHolderList.iconView = (ImageView) convertView.findViewById(R.id.app_icon);
                viewHolderList.applicationName = (TextView) convertView.findViewById(R.id.app_name);
                viewHolderList.locking_app = (ImageButton) convertView.findViewById(R.id.locking_app);
                viewHolderList.iconView.setVisibility(View.VISIBLE);
                convertView.setTag(viewHolderList);


            }else{

                mainViewHolder = (ViewHolder)convertView.getTag();
                //In that line is creating a secondary holder
                final ViewHolder secondaryHolder = mainViewHolder;

                final List applicationsList = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
                mainViewHolder.iconView.setImageDrawable(getPackageManager().getApplicationIcon((ApplicationInfo) applicationsList.get(position)));
                mainViewHolder.applicationName.setText(getPackageManager().getApplicationLabel((ApplicationInfo)applicationsList.get(position)));
                String classNameFromMethodList = extractClassFromApplicationInfo(applicationsList.get(position).toString());

                if(!isApplicationOnTheList(classNameFromMethodList)) {
                    mainViewHolder.locking_app.setImageResource(R.drawable.disabled_button);
                }else if(isApplicationOnTheList(classNameFromMethodList)){
                    mainViewHolder.locking_app.setImageResource(R.drawable.enabled_button);
                }

                mainViewHolder.locking_app.setOnClickListener(
                            new ImageButton.OnClickListener(){
                                public void onClick(View v){
                                    DatabaseBaseObject databaseAcessObject = new DatabaseBaseObject(getContext(),null,null,1);
                                    String classNameFromMethod = extractClassFromApplicationInfo(applicationsList.get(position).toString());
                                    if(!isApplicationOnTheList(classNameFromMethod)) {
                                        if (databaseAcessObject.viewApplicationsColumns()==null) {
                                            //In that line the method is adding the application as the first of block list
                                            databaseAcessObject.insertFirstApplication(classNameFromMethod);

                                        } else{
                                            //In that line the method is adding the application to the block list
                                            databaseAcessObject.insertNewApplicationToTheList(classNameFromMethod);
                                        }
                                      //And the button is set as enabled temporarily to confirm
                                      secondaryHolder.locking_app.setImageResource(R.drawable.enabled_button);
                                      //And the service is reloaded
                                      applicationWatcher.setContext(actualContext);

                                    }else if(isApplicationOnTheList(classNameFromMethod)){

                                      //Get application index from the list
                                      int applicationPosition = applicationPositionFromDatabase(classNameFromMethod);

                                      //Remove the entire database
                                      databaseAcessObject.removeApplicationList();

                                      //Remove application from the list
                                      applicationConfiguration.remove(applicationPosition);
                                      //In that line is verifying with the database becomed null
                                      if(!applicationConfiguration.isEmpty()){
                                          //In that lines is adding the new list to the database
                                          for(int i = 0;i <= (applicationConfiguration.size()-1);i++){
                                            if(i == 0){
                                                databaseAcessObject.insertFirstApplication(applicationConfiguration.get(0).toString());
                                            }
                                            else{
                                                databaseAcessObject.insertNewApplicationToTheList(applicationConfiguration.get(0).toString());
                                            }
                                          }
                                      }
                                      secondaryHolder.locking_app.setImageResource(R.drawable.disabled_button);
                                      //And the service list is reloaded
                                      applicationWatcher.setContext(actualContext);
                                    }

                                }
                            }
                    );
                }
            return convertView;

        }



    }
    public class ViewHolder{
        ImageView iconView;
        TextView applicationName;
        ImageButton locking_app;
    }
}
