package sift3

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        group "/api", {
            "/hello/test"(controller: "hello", action: "test")
            "/enclaves"(resources:"enclave")
            "/keywords"(resources:"keyword")
            "/enclaves/sendfile"(controller: "enclave", action: "upload")
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
