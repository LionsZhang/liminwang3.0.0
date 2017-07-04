package com.example.administrator.lmw;

import com.example.administrator.lmw.utils.NumberParserUtil;
import com.example.administrator.lmw.utils.UpdateUtil;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testParserInt() {
//        System.out.println(NumberParserUtil.parserInt("3988"));
//        System.out.println(NumberParserUtil.parserInt("-3988"));
//        System.out.println(NumberParserUtil.parserInt("+3988"));
//        System.out.println(NumberParserUtil.parserInt("3988.00"));
//        System.out.println(NumberParserUtil.parserInt("-3988.00"));
//        System.out.println(NumberParserUtil.parserInt("3988.00",4));
//        System.out.println(NumberParserUtil.parserInt("",4));
//        System.out.println(NumberParserUtil.parserInt(null,4));
//        System.out.println(NumberParserUtil.parserInt(null));
//        System.out.println(NumberParserUtil.parserInt(""));
//        System.out.println(NumberParserUtil.parserInt("3988"));

        String s = "ddd\n傻逼";
        System.out.print(s.replaceAll("\n", ""));
        System.out.print(s.split("\\n")[0]);

//        Assert.assertEquals(NumberParserUtil.parserInt("",4),1);
    }

    @Test
    public void testParserDouble() {
//        System.out.println(NumberParserUtil.parserDouble("3988"));
//        System.out.println(NumberParserUtil.parserDouble("-3988"));
//        System.out.println(NumberParserUtil.parserDouble("+3988"));
//        System.out.println(NumberParserUtil.parserDouble("3988.00"));
//        System.out.println(NumberParserUtil.parserDouble("-3988.00"));
//        System.out.println(NumberParserUtil.parserDouble("3988.00",4));
//        System.out.println(NumberParserUtil.parserDouble("",4));
        System.out.println(NumberParserUtil.parserDouble(null, 4));
        System.out.println(NumberParserUtil.parserDouble(null));
        System.out.println(NumberParserUtil.parserDouble(""));
        System.out.println(UpdateUtil.md5(new File("F:/包/other_v9.9.9_999_prapare.apk")));

        Assert.assertEquals(1, 1);
    }

    @Test
    public void testParserFloat() {
        System.out.println(NumberParserUtil.parserFloat("3988"));
        System.out.println(NumberParserUtil.parserFloat("-3988"));
        System.out.println(NumberParserUtil.parserFloat("+3988"));
        System.out.println(NumberParserUtil.parserFloat("3988.00"));
        System.out.println(NumberParserUtil.parserFloat("-3988.00"));
        System.out.println(NumberParserUtil.parserFloat("3988.00", 4));
        System.out.println(NumberParserUtil.parserFloat("", 4));
        System.out.println(NumberParserUtil.parserFloat(null, 4));
        System.out.println(NumberParserUtil.parserFloat(null));
        System.out.println(NumberParserUtil.parserFloat(""));
        System.out.println(NumberParserUtil.parserFloat("3988"));

        Assert.assertEquals(1, 1);
    }

    @Test
    public void testFormat() {
//        System.out.println("(100)".replaceAll("\\(?[0-9]\\d*\\.?\\d*\\)?", ""));
//        System.out.println(" (   100 ) ".trim().replaceAll("[\\s|-]*\\(?[\\s|-]*[0-9]\\d*\\.?\\d*[\\s|-]*\\)[\\s|-]*?", "===="));
//        System.out.println(BigDecemalCalculateUtil.formatValue("3988.1",0,2));
//        System.out.println(BigDecemalCalculateUtil.formatValue("3988.88",0,2));
//        System.out.println(BigDecemalCalculateUtil.formatValue("3988.001",0,2));
//        System.out.println(BigDecemalCalculateUtil.formatValue("3988.001",0,4));
//        System.out.println(BigDecemalCalculateUtil.formatValue("50.00",0,0));
//        System.out.println(BigDecemalCalculateUtil.formatValue("3.15",0,0));
//        System.out.println(BigDecemalCalculateUtil.formatValue("3.15",0,2));
//        System.out.println(String.format("%s%s",BigDecemalCalculateUtil.formatValue("10.00",0,2),"%"));
//        System.out.println(BigDecemalCalculateUtil.subtractToString("1000","50",2));
//        System.out.println(BigDecemalCalculateUtil.subtractToString("1000.12","50",0));
//        System.out.println(BigDecemalCalculateUtil.subtractToString("1000.99","50",2));

//        String[] names = new  String[] {"djdjdj","从家电家具","djjdjdjdj"};
//        Observable.from(names)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String name) {
//                        System.out.println( name);
//                    }
//                });

        Observable
                .create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        System.out.println("rx_call" + Thread.currentThread().getName());

                        subscriber.onNext("dd");
                        subscriber.onCompleted();
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        System.out.println("rx_map  " + Thread.currentThread().getName());
                        System.out.println("rx_map  " + s);
                        return s + "88";
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("rx_subscribe " + Thread.currentThread().getName());
                        System.out.println("rx_subscribe " + s);
                    }
                });


        Assert.assertEquals(1, 1);
    }

}