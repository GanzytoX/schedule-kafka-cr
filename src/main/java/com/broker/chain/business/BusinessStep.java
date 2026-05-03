package com.broker.chain.business;

public abstract class BusinessStep {
    protected BusinessStep next;

    public void setNext(BusinessStep next) {
        this.next = next;
    }

    public abstract void handle(BusinessContext context);
}
