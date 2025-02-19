package com.example.jse.m11.s14;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class DiceCaster {
    private static final int NR = 3;
    private static final int MAX_VALUE = 6;

    public static void main(String[] args) {
        @SuppressWarnings("unchecked")
        FutureTask<Integer>[] dice = new FutureTask[NR];
        for (int i = 0; i < dice.length; i++) {
            dice[i] = new FutureTask<>(() -> {
                int result = (int) Math.ceil(Math.random() * MAX_VALUE);
                if (result == MAX_VALUE) {
                    // !!! just to show the exception behavior !!!
                    // !!! exception should be used only for exceptional cases !!!
                    throw new IllegalStateException("Value is too high (" + result + ")");
                }

                return result;
            });
        }

        for (var die : dice) {
            new Thread(die).start();
        }

        for (var die : dice) {
            try {
                System.out.println(die.get());
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } catch (ExecutionException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("done");
    }
}