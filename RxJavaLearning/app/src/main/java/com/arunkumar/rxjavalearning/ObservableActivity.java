package com.arunkumar.rxjavalearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ObservableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNumbers();
        loopThroughArray();
        multipleObservablesUsecase();
        multiplyOddEven();
        printNumbers();
    }

    /**
     * Add numbers passed on from observable
     */
    private void addNumbers() {
        Observable
            .just(1, 2, 3)
            .reduce((a, b) -> a + b)
            .subscribe(
                result -> Log.v("", "addNumbers -> " + result),
                error -> error.printStackTrace()
            );
    }

    /**
     * Same as above but takes arraylist as input and adds all element and gives total
     */
    private void loopThroughArray() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        Observable
            .fromIterable(numbers)
            .reduce((a, b) -> a + b)
            .subscribe(
                result -> Log.v("", "loopThroughArray -> " + result),
                error -> error.printStackTrace()
            );
    }

    /**
     * Observable A[0, 1, 2]
     * Observable B[2, 4, 6]
     * <p>
     * Divide all elements of B with all elements of A and avoid Divide by zero exception
     * Ex result:
     * 2/1, 2/2, 4/1, 4/2, 6/1, 6/2 => 2, 1, 4, 2, 6, 3
     */

    private void multipleObservablesUsecase() {
        Observable<Integer> num1 = Observable.just(0, 1, 2);
        Observable<Integer> num2 = Observable.just(2, 4, 6);

        num2
            .flatMap(
                i2 -> num1
                    .filter(i -> i != 0)
                    .map(i1 -> i2 / i1)
            )
            .subscribe(
                s -> Log.v("", "cartisianProduct -> " + s),
                error -> error.printStackTrace()
            );
    }

    /**
     * [1, 2, 3, 4, 5] - 3 odds, 2 evens
     * Requirement: Find number of odds and evens and multiply odd count with odd numbers
     * & even count with even numbers and print them.
     * Ex result:
     * 3   (1 * 3)
     * 4   (2 * 2)
     * 9   (3 * 3)
     * 8   (4 * 2)
     * 15  (5 * 3)
     */
    private void multiplyOddEven() {
        Observable<Integer> obs = Observable.just(1, 2, 3, 4, 5);

        Observable<Long> evenCount = obs.filter(integer -> integer % 2 == 0).count().toObservable();
        Observable<Long> oddCount = obs.filter(integer -> integer % 2 != 0).count().toObservable();


        obs.flatMap(
            integer -> integer % 2 == 0 ?
                       evenCount.map(count -> count * integer) :
                       oddCount.map(count -> count * integer)
        ).subscribe(
            s -> Log.v("", "multiplyOddEven -> " + s),
            error -> error.printStackTrace()
        );
    }

    /**
     * Iterate through integer array and print each element
     */
    private void printNumbers() {
        List<Integer> arrayIntegers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arrayIntegers.add(i);
        }
        arrayIntegers.add(null);

        Observable
            .fromIterable(arrayIntegers)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("", "printNumbers -> Subscribed");
                    }

                    @Override
                    public void onNext(Integer i) {
                        Log.d("", "printNumbers -> " + i);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("", "printNumbers -> error: " + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("", "printNumbers -> Completed");
                    }
                }
            );
    }

}
