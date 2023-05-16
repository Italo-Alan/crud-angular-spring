package com.italo.enums;

public enum Category {
    BACK_END("Back-End"), FRONT_END("Front-End"), FULL_STACK("Full-Stack"), DATABASE("Database"), DEVOPS("DevOps");

    private String value;

    private Category(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public String toString(){
        return value;
    }
}
