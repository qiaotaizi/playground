package com.jaiz.study.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 模拟redis命令
 */
@Getter
@Setter
public class RedisCommand {
    private String command;
    private String[] args;

    public static RedisCommand build(String cmd,String... args){
        RedisCommand rc=new RedisCommand();
        rc.command=cmd;
        rc.args=args;
        return rc;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder(command);
        if (args!=null && args.length>0){
            for (String arg : args) {
                sb.append(' ').append(arg);
            }
        }
        return sb.toString();
    }
}
