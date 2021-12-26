package br.com.guilhermeapc.goyote.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;

import java.util.List;

import br.com.guilhermeapc.goyote.R;

public class PatternDefinition extends AppCompatActivity {
    private PatternLockView mPatternLockView;
    //In that line the first pattern is going to be add
    private String password1;
    //In that line the confirmation pattern is going to be add
    private String password2;
    //In that line is defining the position of menu
    private int positionOfMenu;
    private void setPassword(String password, int menu){

        if(menu==0){
            this.password1 = password;


        }
        else if(menu==1){
            this.password2 = password;
        }
    }
    private String getPassword(int menu){
        if(menu==0){
            return this.password1;
        }
        else if(menu==1){
            return this.password2;
        }
        return null;


    }
    private int getMenuPosition(){
        //In that line  is recovering the value from menu
        Intent recoveryIntent = getIntent();
        this.positionOfMenu = recoveryIntent.getIntExtra("menuPosition",2);
        return this.positionOfMenu;


    }
    private String getPassword1(){
        //In that line is recovering the value
        Intent recoveryIntent = getIntent();
        String password1 = recoveryIntent.getStringExtra("password3");
        return password1;
    }


    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener(){
        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {

        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            setPassword(pattern.toString(),getMenuPosition());




            Intent intent = new Intent(PatternDefinition.this,PatternList.class);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("password").append(getMenuPosition());
            intent.putExtra("password3",getPassword1());
            intent.putExtra(stringBuilder.toString(),getPassword(getMenuPosition()));
            startActivity(intent);

        }

        @Override
        public void onCleared() {

        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pattern_activity);
        mPatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_view);
        mPatternLockView.setDotCount(4);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);


    }
}
