package br.com.guilhermeapc.goyote.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.guilhermeapc.goyote.R;

public class NumericDefinition extends AppCompatActivity {
    public String[] numbers = {"0","1","2","3","4","5","6","7","8","9"};
    EditText passwordField;
    //In that line have the first password
    private String password1 = "";
    //In that line have the confirmation password
    private String password2 = "";
    //In that line has the menu position
    private int positionOfMenu;
    //String to the value of password field
    private String field = "";
    private void setPassword(String number, int menu){

     if(menu==1){
         this.password1 += number;
     }
     else if(menu==2){
         this.password2 += number;
     }
      field = field + number;

      passwordField.setText(field);
    }
    private String getPassword(int menu){
        if(menu==1){
            return this.password1;

        }
        else if(menu==2){
            return this.password2;
        }
        return null;


    }
    private int getMenuPosition(){
        //In that line  is recovering the value from menu
        Intent recoveryIntent = getIntent();
        this.positionOfMenu = recoveryIntent.getIntExtra("menuPosition",3);
        return this.positionOfMenu;


    }
    private String getPassword1(){
        //In that line is recovering the value
        Intent recoveryIntent = getIntent();
        String password1 = recoveryIntent.getStringExtra("password4");
        return password1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.numeric_activity);
        passwordField = findViewById(R.id.passwordField);
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
        final Button confirmation = findViewById(R.id.confirm_button);
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
        super.onCreate(savedInstanceState);
        btn0.setOnClickListener(new Button.OnClickListener(){
        public void onClick(View v){
            setPassword(btn0.getText().toString(),getMenuPosition());
              }
        });
        btn1.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn1.getText().toString(),getMenuPosition());

                     }
        });
        btn2.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn2.getText().toString(),getMenuPosition());
            }
        });
        btn3.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn3.getText().toString(),getMenuPosition());
            }
        });
        btn4.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn4.getText().toString(),getMenuPosition());
            }
        });
        btn5.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn5.getText().toString(),getMenuPosition());
            }
        });
        btn6.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn6.getText().toString(),getMenuPosition());
            }
        });
        btn7.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn7.getText().toString(),getMenuPosition());
            }
        });
        btn8.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn8.getText().toString(),getMenuPosition());
            }
        });
        btn9.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                setPassword(btn9.getText().toString(),getMenuPosition());
            }
        });
        confirmation.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                //In that lines the application goes back to the settings
                Intent intent = new Intent(NumericDefinition.this,NumericList.class);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("password").append(getMenuPosition());
                intent.putExtra(stringBuilder.toString(),getPassword(getMenuPosition()));
                intent.putExtra("password4",getPassword1());
                startActivity(intent);
            }
        });
    }
}
