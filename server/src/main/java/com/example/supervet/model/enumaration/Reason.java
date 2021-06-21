package com.example.supervet.model.enumaration;

import lombok.Getter;

@Getter
public enum Reason {
    PCE("Първични и контролни прегледи","Първични и контролни прегледи"),
    SE("Специализирани прегледи","Специализирани прегледи"),
    VM("Ваксини и микрочипове","Ваксини и микрочипове"),
    ILD("Образна и Лабораторна диагностика","Образна и Лабораторна диагностика"),
    T("Терапия","Терапия"),
    S("Хирургия","Хирургия"),
    O("Други","Други");

    String name;
    String value;

    Reason(String name,String value) {
        this.name=name;
        this.value=value;
    }


}
