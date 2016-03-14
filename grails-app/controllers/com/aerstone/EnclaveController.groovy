package com.aerstone

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EnclaveController {

    static responseFormats = ['json']
//    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def enclaves = Enclave.list(params)
        respond enclaves
    }

    @Secured(['ROLE_ADMIN'])
    def show(Enclave enclave) {
        respond enclave
    }

//    @Secured(["ROLE_ADMIN"])
//    @Transactional
//    def upload() {
//        println "in upload"
//        println params.file
//        println params
//        println request.JSON
//        respond (text: "ok") as JSON
//    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def save(Enclave enclave) {
        if (enclave == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (enclave.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond enclave.errors, view:'create'
            return
        }

        enclave.save flush:true

        respond enclave, [status: CREATED, view:"show"]
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def update(Enclave enclave) {
        if (enclave == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (enclave.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond enclave.errors, view:'edit'
            return
        }

        enclave.save flush:true

        respond enclave, [status: OK, view:"show"]
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def delete(Enclave enclave) {

        if (enclave == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        enclave.delete flush:true

        render status: NO_CONTENT
    }
}

//class FooCommand {
//    String name
//    String foo
//    File file
//    String test
//    List<Enclave> enclaveList
//}
