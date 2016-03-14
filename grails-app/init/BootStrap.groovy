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
    }
    def destroy = {
    }
}
