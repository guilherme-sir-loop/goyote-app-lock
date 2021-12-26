package br.com.guilhermeapc.goyote.services;

import android.service.dreams.DreamService;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import java.util.Random;

import br.com.guilhermeapc.goyote.R;
import br.com.guilhermeapc.goyote.dao.PhrasesDAO;


public class PhrasesDreamService extends DreamService {
    View phraseViewer;
    TextView motivationalView;
    Handler mHandler = new Handler();
    PhrasesDAO phrasesDAO;
    Random random = new Random();
    private final Runnable mPhraserRunner = new Runnable() {
        int PHRASES_INDEX = 46;
        long PHRASES_TIME = 720000;

        @Override
        public void run() {
            int phraseIndex = random.nextInt(PHRASES_INDEX);
            motivationalView.setText(phrasesDAO.getCitation(phraseIndex)+" \n \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t \t  \t"+phrasesDAO.getAuthor(phraseIndex));
            mHandler.removeCallbacks(mPhraserRunner);
            mHandler.postDelayed(mPhraserRunner,PHRASES_TIME);

        }
    };
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setScreenBright(true);
        setContentView(R.layout.simple_phrases_view);
        //In that line is seeing with the window exists
        phraseViewer = getWindow().getDecorView().getRootView();
        if(phraseViewer != null){
            motivationalView = findViewById(R.id.citation);

        }
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        mPhraserRunner.run();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        phrasesDAO = new PhrasesDAO(this);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mPhraserRunner);
    }
}
