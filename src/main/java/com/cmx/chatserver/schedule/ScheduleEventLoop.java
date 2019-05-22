package com.cmx.chatserver.schedule;


import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author cmx
 * @date 2019/5/15
 */
public class ScheduleEventLoop extends RecursiveTask<Long> {

    private Integer threadHolder = 10;

    private Integer start;

    private Integer end;

    ScheduleEventLoop(Integer start, Integer end){
        this.start = start;
        this.end = end;
    }


    private Long mutAdd(Integer start, Integer end){
        Long sum = 0L;
        for(int i = start; i <= end; i++){
            sum += i;
        }
        return sum;
    }

    @Override
    protected Long compute() {
        if(end - start < threadHolder){
            return mutAdd(start, end);
        } else {
            Integer middle = start + (end - start)/2;
            ScheduleEventLoop left = new ScheduleEventLoop(start, middle);
            ScheduleEventLoop right = new ScheduleEventLoop(middle + 1, end);

            left.fork();
            right.fork();

            Long joinLeft = left.join();
            Long joinRight = right.join();

            return joinLeft + joinRight;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ScheduleEventLoop s = new ScheduleEventLoop(1, 100);
        ForkJoinTask<Long> submit = forkJoinPool.submit(s);

        int a = 34;
        System.out.println(Integer.toBinaryString(a));
        int b = 87;
        System.out.println(Integer.toBinaryString(b));

        a = a ^ b;
        System.out.println(Integer.toBinaryString(a));
        b = b ^ a;
        System.out.println(Integer.toBinaryString(b));
        a = a ^ b;
        System.out.println(Integer.toBinaryString(a));

    }

}
