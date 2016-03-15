import com.aerstone.Enclave
import com.aerstone.Keyword
import com.aerstone.KeywordEnclave
import com.aerstone.Role
import com.aerstone.User
import com.aerstone.UserRole

class BootStrap {

    def init = { servletContext ->
        def userRole = new Role('ROLE_ADMIN').save()

        def me = new User('tester@gmail.com', 'password123!').save()

        UserRole.create me, userRole

        UserRole.withSession {
            it.flush()
            it.clear()
        }

        Enclave one = new Enclave(name: "FirstEnclave")
        one.save flush: true
        Enclave two = new Enclave(name: "SecondEnclave")
        two.save flush: true
        Keyword keyone = new Keyword(name: "keywordone", alias: "something")
        keyone.save flush: true
        Keyword keytwo = new Keyword(name: "keywordtwo").save flush: true
        keytwo.save flush: true
        new KeywordEnclave(keyone, one).save flush: true
        new KeywordEnclave(keyone, two).save flush: true




    }
    def destroy = {
    }
}
