package com.broker.chain;

public abstract class BaseHandler {
    protected BaseHandler next;
    public void setNext(BaseHandler next) { this.next = next; }
    public abstract void handle(ChainState state);
    protected void next(ChainState state) {
        if (next != null) next.handle(state);
    }
}
