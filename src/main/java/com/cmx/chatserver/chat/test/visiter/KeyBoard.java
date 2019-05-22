package com.cmx.chatserver.chat.test.visiter;

/**
 * @author cmx
 * @date 2019/5/17
 */
public class KeyBoard implements ComputerComponent {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void print(int[] arr) {
        System.out.println("I'm KeyBoard!");
    }
}
