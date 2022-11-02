package org.validation.shpp;

import java.util.LinkedList;
import java.util.List;

public class StupidTest {


    public static void main(String[] args) {
        StupidTest stupidTest = new StupidTest();
        stupidTest.method(new LinkedList<A>());
    }


    public void method (List<? extends A> param){
        param.add(null);
        for(A a:param){
            String somethingStringA = a.somethingStringA;
        }
    }
    public void methodSuper (List<? super A> param){
        param.add(new StupidTest.A());
        param.add(new StupidTest.B());
        param.add(new StupidTest.C());
        for(Object a:param){

        }
    }






    static class A{
        String somethingStringA;
    }
    static class B extends A{
        String somethingStringB;
    }
    static class C extends B{
        String somethingStringC;
    }
}
