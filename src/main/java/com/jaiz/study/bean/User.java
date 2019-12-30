package com.jaiz.study.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class User {

    private Integer id;
    private String name;

    public static User build(Integer id,String name){
        User u=new User();
        u.id=id;
        u.name=name;
        return u;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
