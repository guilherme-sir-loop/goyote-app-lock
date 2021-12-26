package br.com.guilhermeapc.goyote.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import br.com.guilhermeapc.goyote.R;
import br.com.guilhermeapc.goyote.dao.DatabaseBaseObject;

public class Pattern extends AppCompatActivity {
    private PatternLockView mPatternLockView;
    private DatabaseBaseObject databaseAcessObject;

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {

        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {



        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            PatternLockUtils.patternToString(mPatternLockView,pattern);
            databaseAcessObject.verifyOnDB(pattern.toString());
        }

        @Override
        public void onCleared() {

        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.pattern_activity);
        mPatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_view);
        mPatternLockView.setDotCount(4);
        databaseAcessObject = new DatabaseBaseObject(this,null,null,1);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);


    }

}
