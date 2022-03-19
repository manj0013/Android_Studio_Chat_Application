package com.cst2335.test_application;

public class Message {
    protected String msg=null;
    protected int msg_Type=0;
    protected long id=0;

    public Message(String msg, int msg_Type, long id){
        this.msg=msg;
        this.msg_Type=msg_Type;
        this.id = id;
    }
    public long getId(){
        return id;
    }
    public void update(String msg, int msg_Type){
        this.msg=msg;
        this.msg_Type=msg_Type;
    }
    public String getMsg(){
        return msg;
    }

    public int get_Msg_Type(){
        return msg_Type;
    }
    public Message(String msg, int msg_Type){
        this.msg=msg;
        this.msg_Type=msg_Type;
    }
}
