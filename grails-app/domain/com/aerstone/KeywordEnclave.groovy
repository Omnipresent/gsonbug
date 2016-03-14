package com.aerstone

import groovy.transform.ToString
import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class KeywordEnclave implements Serializable {

    Enclave enclave
    Keyword keyword

    KeywordEnclave(Keyword keyword, Enclave enclave) {
        this.keyword = keyword
        this.enclave = enclave
    }

    static List<Enclave> getEnclaves(Keyword keyword) {
       List<KeywordEnclave> ke = KeywordEnclave.createCriteria().list {
            eq("keyword", keyword)
       }
       List<Enclave> enclaves = []
       ke.each { KeywordEnclave k ->
           enclaves.add(k.enclave)
       }
       return enclaves
    }

    static void removeAll(Keyword k, boolean flush = false) {
        if (k == null) return

        KeywordEnclave.where { keyword == k }.deleteAll()

        if (flush) { KeywordEnclave.withSession { it.flush() } }
    }

    @Override
    boolean equals(other) {
        if (!(other instanceof KeywordEnclave)) {
            return false
        }
        other.keyword?.id == keyword?.id && other.enclave?.id == enclave?.id
    }

    @Override
    int hashCode() {
        def builder = new HashCodeBuilder()
        if (keyword) builder.append(keyword.id)
        if (enclave) builder.append(enclave.id)
        builder.toHashCode()
    }

    static mapping = {
        version false
        id composite: ['keyword', 'enclave']
    }
}
