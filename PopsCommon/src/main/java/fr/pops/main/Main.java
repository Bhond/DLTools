package fr.pops.main;

import fr.pops.beanloop.BeanLoop;
import fr.pops.beans.test.TestBean;

public class Main {

    public static void main(String[] args) {

       TestBean bean = new TestBean();
       BeanLoop.getInstance().start();
    }

}
