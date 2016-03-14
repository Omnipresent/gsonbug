package com.aerstone

class Keyword {

    String name
    String alias
    boolean isRegex


    static constraints = {
        name(nullable: false, unique: true)
        alias(nullable: true)
        isRegex(nullable: false)
    }
}
