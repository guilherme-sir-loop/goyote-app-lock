package br.com.guilhermeapc.goyote.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

import br.com.guilhermeapc.goyote.R;
import br.com.guilhermeapc.goyote.dao.DatabaseBaseObject;

public class Numeric extends AppCompatActivity {
    private String password = "";
    protected EditText passwordField;
    //DatabaseAcessObject databaseAcessObject = new DataBaseObject();

    public String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
    //Test value
    DatabaseBaseObject databaseAcessObject;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        passwordField = findViewById(R.id.passwordField);
        //Intente with binded service that started on boot
        /**Intent i = new Intent(this,ApplicationWatcher.class);
         bindService(i,applicationBlockerConnection,Context.BIND_AUTO_CREATE);**/

        passwordField.setTextIsSelectable(true);
        final Button btn1 = findViewById(R.id.firstButton);
        final Button btn2 = findViewById(R.id.secondButton);
        final Button btn3 = findViewById(R.id.thirdButton);
        final Button btn4 = findViewById(R.id.forthButton);
        final Button btn5 = findViewById(R.id.fifthButton);
        final Button btn6 = findViewById(R.id.sixthButton);
        final Button btn7 = findViewById(R.id.seventhButton);
        final Button btn8 = findViewById(R.id.eighthButton);
        final Button btn9 = findViewById(R.id.ninethButton);
        final Button btn0 = findViewById(R.id.zeroButton);

        if(databaseAcessObject.getRandomNumber() == true){
            //Randomized numbers
            String[] randomized_numbers = numbers;
            shuffleArray(randomized_numbers);
            btn0.setText(randomized_numbers[0]);
            btn1.setText(randomized_numbers[1]);
            btn2.setText(randomized_numbers[2]);
            btn3.setText(randomized_numbers[3]);
            btn4.setText(randomized_numbers[4]);
            btn5.setText(randomized_numbers[5]);
            btn6.setText(randomized_numbers[6]);
            btn7.setText(randomized_numbers[7]);
            btn8.setText(randomized_numbers[8]);
            btn9.setText(randomized_numbers[9]);

        }
        else{
            btn0.setText(numbers[0]);
            btn1.setText(numbers[1]);
            btn2.setText(numbers[2]);
            btn3.setText(numbers[3]);
            btn4.setText(numbers[4]);
            btn5.setText(numbers[5]);
            btn6.setText(numbers[6]);
            btn7.setText(numbers[7]);
            btn8.setText(numbers[8]);
            btn9.setText(numbers[9]);

        }


        btn0.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn0.getText().toString());
                    }
                }
        );
        btn1.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn1.getText().toString());
                    }
                }
        );
        btn2.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn2.getText().toString());
                    }
                }
        );
        btn3.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn3.getText().toString());
                    }
                }
        );
        btn4.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn4.getText().toString());
                    }
                }
        );
        btn5.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn5.getText().toString());
                    }
                }
        );
        btn6.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn6.getText().toString());
                    }
                }
        );
        btn7.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn7.getText().toString());
                    }
                }
        );
        btn8.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn8.getText().toString());
                        }
                }
        );
        btn9.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        passwordVerifyier(btn9.getText().toString());
                    }
                }
        );
        databaseAcessObject = new DatabaseBaseObject(this,null,null,1);
        //Test value
        //databaseAcessObject.testValues();



    }
    private void passwordVerifyier(String passwordKey){
        passwordField = findViewById(R.id.passwordField);

        password = password +passwordKey;
        passwordField.setText(password);

        databaseAcessObject.verifyOnDB(password);


    }
    // Implementing Fisher–Yates shuffle
    //Adapted from StachOverFlow:
    static void shuffleArray(String[] ar)
    {

        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

}
