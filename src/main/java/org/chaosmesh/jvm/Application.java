package org.chaosmesh.jvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/hello")
    public String hello(String name) {
        if (name == null) {
            name = "friend";
        }
        try {
            return sayHello(name);
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    @GetMapping("/async")
    public String asyncHello(final String name, long timeout) {
        if (timeout == 0) {
            timeout = 3000;
        }
        try {
            FutureTask futureTask = new FutureTask(new Callable() {
                @Override
                public Object call() throws Exception {
                    return sayHello(name);
                }
            });
            new Thread(futureTask).start();
            return (String)futureTask.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            return "timeout, " + e.getMessage() + "\n";
        } catch (Exception e) {
            return e.getMessage() + "\n";
        }
    }

    private String sayHello(String name){
        return "Hello " + name;
    }

    private int zeroError() throws ZeroError{
        try{
            return 1/0;
        }catch (Exception e){
            throw new ZeroError();
        }
    }

    @GetMapping("/zero")
    public String zero() throws Exception {
        try{
            zeroError();
        }
        catch (Exception e){
            return e.toString();
        }
        return null;
    }

    class ZeroError extends Exception{

    }


}
