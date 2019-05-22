package com.cmx.chatserver.chat.test.visiter;

/**
 * @author cmx
 * @date 2019/5/17
 */
public class SimpleVisitor implements Visitor {
    @Override
    public void visit(ComputerComponent computerComponent) {
        computerComponent.print(new int[]{});
    }
}
