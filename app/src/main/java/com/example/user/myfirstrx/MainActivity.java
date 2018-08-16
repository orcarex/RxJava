package com.example.user.myfirstrx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class MainActivity extends AppCompatActivity {


//    create RTC time list for demo
    int[] timelist = new int[]{
            1500000000,1510000000,1520000000,
            1530000000,1540000000,1550000000,
            1600000000,1700000000,1570000000,
            500000000,400000000,3000,3000,
            14000000,13000000,1400000000,
    };

    Consumer<int[]> consumer;

    ToggleButton toggle;
    TextView textView;

    Subject<int[]> mObservable = BehaviorSubject.createDefault(timelist);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggle = (ToggleButton) findViewById(R.id.button);
        textView = findViewById(R.id.textView);



        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    replace fftlist2 to a new random array
                    createRandomArray();
                    mObservable.doOnNext(consumer);
                    mObservable.subscribe(consumer);
                } else {
//                    sort timelist
                    sortArray(timelist);
                    mObservable.doOnNext(consumer);
                    mObservable.subscribe(consumer);
                }
            }
        });


        String[] names = {"hello","world"};


        consumer = new Consumer<int[]>() {
            @Override
            public void accept(int[] ints) throws Exception {
                String text = "";
                for(int i : ints){
                    text += String.valueOf(i) + "\n";
                }
                textView.setText(text);
            }
        };


        mObservable
                .subscribeOn(Schedulers.newThread())
                .subscribe(consumer);


    }

    private void sortArray(int[] list) {

        for(int i = 0 ; i < list.length ; i ++){
            for(int j = i ; j < list.length ; j ++){
                if(list[i] > list[j]){
                    swap(i,j,list);
                }
            }
        }

        logArray(list);

    }

    private void swap(int index_1,int index_2,int[] array) {
        int temp = array[index_1];
        array[index_1] = array[index_2];
        array[index_2] = temp;
    }

    private void createRandomArray() {

        for(int i = 0 ; i < timelist.length ; i ++){
            timelist[i] = (int) (System.currentTimeMillis()/1000 - 500000000 * Math.random());
        }

        logArray(timelist);

    }

    private void logArray(int[] fftlist2) {
        String logs = "";
        for(int i : fftlist2){
            logs += String.valueOf(i) + " ";
        }
        Log.v("log array  = ", logs);
    }


}
