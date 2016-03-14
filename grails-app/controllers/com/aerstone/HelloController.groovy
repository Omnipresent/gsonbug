package com.aerstone


import grails.rest.*
import grails.converters.*
import grails.plugin.springsecurity.annotation.Secured

class HelloController {

    def index() {}

    @Secured(['ROLE_USER'])
    def test() {}
}
