package br.com.guilhermeapc.goyote.gui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import br.com.guilhermeapc.goyote.dao.DatabaseBaseObject;


public class UnifiedDefinition extends Activity {
    //Declare a new intent
    Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseBaseObject databaseAcessObject = new DatabaseBaseObject(this,null,null,1);
        if(databaseAcessObject.getAutenticationMethod().equals("Pattern")){
            intent1 = new Intent(UnifiedDefinition.this, Pattern.class);
            startActivity(intent1);
        }else if(databaseAcessObject.getAutenticationMethod().equals("Password")){
            intent1 = new Intent(UnifiedDefinition.this,Numeric.class);
            startActivity(intent1);
        }
    }
}
