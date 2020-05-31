package com.hnefashuffle.gui;

public class TimeThread extends Thread {
    private Table gameView;
    boolean cancel = false;
    private int count = 1;

    TimeThread(Table table){
        this.gameView = table;
    }

    public void cancelThread() {
        cancel = true;
    }

    private void startTimer(){
        while(!cancel) {
            String time = String.format("%02d:%02d", count / 60, count % 60);
            gameView.setTimeText(time);
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        startTimer();
    }
}