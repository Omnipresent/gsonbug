package com.aerstone

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class KeywordController {

    static responseFormats = ['json']
//    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        List<Enclave> enclaveList = []
        List <KeywordCommand> keywordCommands = []
        Keyword.all.each {Keyword key ->
            keywordCommands.add(new KeywordCommand(id: key.id, name: key.name, alias: key.alias, isRegex: key.isRegex, enclaves: KeywordEnclave.getEnclaves(key)))
        }
        render (view: "index", model: [keywordCommandList: keywordCommands])
    }

    def show(Keyword keyword) {
        render (view: "show", status: OK, model: [keyword: keyword, enclaveList: KeywordEnclave.getEnclaves(keyword)])
    }

    @Secured(['ROLE_ADMIN'])
    @Transactional
    def save(KeywordCommand command) {
        if (command == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (command.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond command.errors, view:'create'
            return
        }

        if (command.name.startsWith("/") && command.name.endsWith("/"))
            command.isRegex = true

        Keyword keyword = new Keyword(name: command.name, alias: command.alias)
        keyword.save flush: true
        if (keyword.hasErrors()){
            respond keyword.errors, view: 'create'
            return
        }

        command?.enclaves?.each { Enclave enclave ->
            new KeywordEnclave(keyword, Enclave.findByName(enclave.name)).save flush: true
        }

        render (view: "show", model: [enclaveList: KeywordEnclave.getEnclaves(keyword), keyword: keyword], status: CREATED)
    }

    @Transactional
    def update(KeywordCommand keywordCommand) {
        if (keywordCommand == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (keywordCommand.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond keywordCommand.errors, view:'edit'
            return
        }

        Keyword keyword = Keyword.get(params.id)
        if (keyword == null) {
            render(view: "/notFound")
            return
        }
        keyword?.name = keywordCommand.name
        keyword?.alias = keywordCommand.alias
        keyword?.isRegex = keywordCommand.isRegex
        keyword.save flush: true

        keywordCommand?.enclaves?.each { Enclave enclave ->
            new KeywordEnclave(keyword, Enclave.findByName(enclave.name)).save flush: true
        }

        render (view: "show", model: [enclaveList: KeywordEnclave.getEnclaves(keyword), keyword: keyword], status: CREATED)
    }

    @Transactional
    def delete(Keyword keyword) {

        if (keyword == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        keyword.delete flush:true
        KeywordEnclave.removeAll(keyword, true)

        render status: NO_CONTENT
    }
}

class KeywordCommand {
    Long id
    String name
    String alias
    boolean isRegex
    List<Enclave> enclaves

    static constraints = {
        id blank: true, nullable: true
        name blank: false, nullable: false, unique: true
        alias blank: true, nullable: true
        isRegex blank: true, nullable: true
        enclaves blank: true, nullable: true, validator: { enclave ->
            enclave.every { it.validate() } ?:  'com.aerstone.KeywordCommand.enclaves.custom'
        }
    }
}
