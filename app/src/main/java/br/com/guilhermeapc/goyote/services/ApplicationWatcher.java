package br.com.guilhermeapc.goyote.services;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import br.com.guilhermeapc.goyote.dao.DatabaseBaseObject;
import br.com.guilhermeapc.goyote.gui.UnifiedDefinition;

/**
 * Created by Guilherme on 07/04/2019.
 * This class is resposible by close every application that are on the list
 */

public class ApplicationWatcher extends IntentService {
    private int lastApplicationID;
    public DatabaseBaseObject databaseAcessObject;
    //In that line has the context of the database
    private Context context;
    //In that line is difining the service intent
    Intent launchIntent;
    //The list of application UID
    ArrayList AppListUID;
    private int processGetLastApplicationUID(){
        java.lang.Process UID = null;
        int lastProcessUID = 0;
        try{
            UID = Runtime.getRuntime().exec("ps -o UID");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(UID.getInputStream()));
            lastProcessUID = Integer.parseInt(bufferedReader.readLine());
        }catch (IOException e){
            Log.d("Goyote/AppWatch",e.toString());
        }

        return lastProcessUID;
    }
    //The requeired api is 24 not 16
    @TargetApi(Build.VERSION_CODES.N)
    public void onHandleIntent(Intent intent) {
        //In that line is initialiazing the database
        databaseAcessObject = new DatabaseBaseObject(this.context,null,null,1);
        //In that line is initialiazing the Intent
        Intent unifiedIntent = intent;
        //In that line is defining the array as int
        AppListUID = new ArrayList<Integer>();
        //Those lines of code are resposible by to separate the value saved on the database
        String comaSeparatedString =  databaseAcessObject.viewApplicationsColumns();
        //In those lines is difinning the array for 1 item
        if(!comaSeparatedString.contains(",")){
            try {
                //Getting the app GID and UID  : PackageManager.GET_GIDS
                AppListUID.add(0,context.getPackageManager().getPackageUid(comaSeparatedString,PackageManager.GET_GIDS));
                //In case if the UID is different than the xml one
                Log.d("Goyote/AppWatcher", Integer.toString(this.context.getPackageManager().getPackageUid(comaSeparatedString, PackageManager.GET_GIDS)));
            } catch (PackageManager.NameNotFoundException e) {
                try {
                    throw new Exception(e);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        else if(comaSeparatedString.contains(",")) {
            //And the list of applications are parsed to a Array
            String[] spacedLineString = comaSeparatedString.split(",");
            //Those lines are use to get UID of application and compare with current apps and save the last app of the list on a variable
            for (int i = 0; (i <= spacedLineString.length-1); i++) {

                try {
                    //Getting the app GID and UID  : PackageManager.GET_GIDS
                    AppListUID.add(context.getPackageManager().getPackageUid(spacedLineString[i], PackageManager.GET_GIDS));
                    //In case if the UID is different than the xml one
                    Log.d("Goyote/AppWatcher", Integer.toString(this.context.getPackageManager().getPackageUid(spacedLineString[i], PackageManager.GET_GIDS)));
                } catch (PackageManager.NameNotFoundException e) {
                    try {
                        throw new Exception(e);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

        //Running a loop to scan the new process
        boolean test = false;
        while(test = false) {
            for (int i = 1;i <= (AppListUID.size()-1);i++) {
                //Inicializing another variable to not erase the UID every new time and avoid accidentaly open the wrong app
                int lastApplicationUID = Integer.parseInt(AppListUID.get(i).toString());
                if (processGetLastApplicationUID() == lastApplicationUID) {

                    try {
                        //Starting the variable as zero to be replace by the desired process PID
                        int lastApplicationPID = 0;
                        //That method get the application PID by running a Linux command inside the Runtime Process
                        java.lang.Process PID = Runtime.getRuntime().exec("pidof -s " + this.context.getPackageManager().getNameForUid(Process.LAST_APPLICATION_UID));
                        //That line get the result of research of command line
                        DataOutputStream outputStream = new DataOutputStream(PID.getOutputStream());
                        //Saving the UID of the last app
                        lastApplicationID = Process.LAST_APPLICATION_UID;
                        //That line convert the research of command line on a variable
                        outputStream.writeInt(lastApplicationPID);
                        //That method kill that process
                        Process.killProcess(lastApplicationPID);
                        //In those lines are launching the app screen
                        unifiedIntent = new Intent(this.context, UnifiedDefinition.class);
                        startActivity(unifiedIntent);
                    } catch (IOException e) {
                        try {
                            throw new Exception(e);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    public ApplicationWatcher(){
        super("ApplicationWatcher");
    }
    public void setContext(Context context){
        this.context = context;
        launchIntent = new Intent();
        onHandleIntent(launchIntent);

    }

    public void openLastApp(Context context){
        //Get the name of the last app and launch it
        //Applicaton launcher intent declared
        Intent launchIntent;
        //Application laucher context iniciliazed
        Context contextLaunch = context;
        launchIntent = new Intent();
        if (launchIntent != null) {
            //Converting the lastApplication UID for a String
            String lastApplicationName = contextLaunch.getPackageManager().getNameForUid(lastApplicationID);
            launchIntent = contextLaunch.getPackageManager().getLaunchIntentForPackage(lastApplicationName);
            context.startActivity(launchIntent);
        }


    }


}


