package br.com.guilhermeapc.goyote.gui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.List;


public class MenuListView extends ListView {
    private static int numberOfItems = 0;
    private void setNumberOfItems(int numberOfItems){
        this.numberOfItems =  numberOfItems;
    }
   public MenuListView(Context context, List list) {
        super(context);
        //In that lines are defining the item 1 item and multiple lines list
        if(list.get(1).equals("")){
            setNumberOfItems(1);

        }else{
            setNumberOfItems(list.size());
        }


    }

    public MenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //That line is difining the correct width for the screen
        int manuallyWidth = 490;
        int manuallyHeight = heightMeasureSpec;
        if (this.numberOfItems==1){
            super.onMeasure(manuallyWidth,manuallyHeight);
        }
        else if(this.numberOfItems==2){
            manuallyHeight = 130;
            super.onMeasure(manuallyWidth,manuallyHeight);
            setMeasuredDimension(manuallyWidth,manuallyHeight);
        }else if(this.numberOfItems==3){
            manuallyHeight = 193;
            super.onMeasure(manuallyWidth,manuallyHeight);
            setMeasuredDimension(manuallyWidth,manuallyHeight);


        }
    }
}
