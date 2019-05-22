package com.cmx.chatserver.chat.test.visiter;

/**
 * @author cmx
 * @date 2019/5/17
 */
public class Computer {

    private ComputerComponent[] computerComponentList;

    public Computer(){
        computerComponentList = new ComputerComponent[]{new Mouse(), new KeyBoard()};
    }

    public static void main(String[] args){
        Computer computer = new Computer();
        Visitor visitor = new SimpleVisitor();
        for(ComputerComponent computerComponent : computer.computerComponentList) {
            computerComponent.accept(visitor);
        }
    }


}
