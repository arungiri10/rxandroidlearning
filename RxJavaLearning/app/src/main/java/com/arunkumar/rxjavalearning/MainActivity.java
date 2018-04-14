package com.arunkumar.rxjavalearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    /**
     * Problem statement:
     * Monk just purchased an array A having N integers. Monk is very superstitious. He calls the
     * array A Lucky if the frequency of the minimum element is odd, otherwise he considers it
     * Unlucky. Help Monk in finding out if the array is Lucky or not.
     */

    int[] arrayA = {8, 8, 5, 5, 5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FrameLayout row = findViewById(R.id.test);

        View.OnTouchListener l = new View.OnTouchListener() {
            int count = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                count++;
                Log.v("", "count: " + count);
                if(count == 1) {
                    Friend.main();
                }
                return true;
            }
        };

        row.setOnTouchListener(l);



        createAObservable();
        createAObservableWithArray();
    }

    private void createAObservable() {
        int data = 40;
        Observable<Integer> observable = Observable.just(data);
        observable.subscribe(newData -> Log.v("", "new data: " + newData));
    }

    private void createAObservableWithArray() {
        Observable
                .fromIterable(datas())
                .observeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(d -> Log.v("", "d is : " + d));
    }

    private Observable<Integer> test() {
        return Observable.fromIterable(datas2());
    }

    private List<Integer> datas() {
        List<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(2);
        data.add(3);
        data.add(4);
        data.add(5);
        return data;
    }

    private List<Integer> datas2() {
        List<Integer> data = new ArrayList<>();
        data.add(6);
        data.add(7);
        data.add(8);
        data.add(9);
        data.add(0);
        return data;
    }

    private void checkIfLucky() {
        Single.just(arrayA)
                .map(arrayElement -> {
                            int min = arrayElement[0];
                            int count = 1;
                            for (int i = 1; i < arrayElement.length; i++) {
                                if (min > arrayElement[i]) {
                                    min = arrayElement[i];
                                    count = 1;
                                } else if (min == arrayElement[i]) {
                                    count++;
                                }
                            }
                            if (count % 2 != 0) {
                                return "Lucky";
                            } else {
                                return "Unlucky";
                            }
                        }
                ).subscribe(
                isLucky -> Log.d("", "He is " + isLucky),
                error -> error.printStackTrace());
    }

    public boolean isPalindrome(String word) {
        word = word.toLowerCase();
        if (word.length() % 2 != 0) {
            return false;
        }
        int j = word.length();
        boolean isPalindrom = true;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != word.charAt(j)) {
                isPalindrom = false;
                break;
            } else {
                j--;
            }
        }
        return isPalindrom;
    }

}
