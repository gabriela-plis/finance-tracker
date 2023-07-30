package com.financetracker.app.user

import com.financetracker.app.security.authentication.RegisterDetailsDTO
import com.financetracker.app.security.authorization.Role
import com.financetracker.app.utils.exception.custom.DocumentNotFoundException
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class UserServiceTest extends Specification {

    UserRepository userRepository = Mock()
    UserMapper userMapper = Mappers.getMapper(UserMapper)
    PasswordEncoder passwordEncoder = Mock()

    UserService userService = new UserService(userRepository, userMapper, passwordEncoder)

    def"should register user"() {
        given:
        RegisterDetailsDTO registerDetails = new RegisterDetailsDTO("anne", "anne@gmail.com", "anne123")
        User userWithRole = new User(null, "anne", "anne@gmail.com", "anne123", List.of(Role.USER))

        when:
        userService.registerUser(registerDetails)

        then:
        1 * passwordEncoder.encode(registerDetails.password()) >> "anne123"
        1 * userRepository.insert(userWithRole)
    }

    def"should return true if user exist"() {
        given:
        String email = "anne@gmail.com"

        when:
        boolean result = userService.userIsExist(email)

        then:
        1 * userRepository.findByEmail(email) >> Optional.of(getUser())

        and:
        result == true
    }

    def"should return false if user doesn't exist"() {
        given:
        String email = "anne@gmail.com"

        when:
        boolean result = userService.userIsExist(email)

        then:
        1 * userRepository.findByEmail(email) >> Optional.empty()

        and:
        result == false
    }

    def"should get all users with pagination"() {
        given:
        PageRequest pageable = PageRequest.of(0, 5)

        when:
        Page<User> result = userService.getAllUsers(pageable)

        then:
        1 * userRepository.findAll(pageable) >> getPagedUsers()

        and:
        result == getPagedUsers()
    }

    def"should get user by id"() {
        given:
        String id = "1"

        when:
        User result = userService.getUserById(id)

        then:
        1 * userRepository.findById(id) >> Optional.of(getUser())

        and:
        result == getUser()
    }

    def"should throw DocumentNotFoundException if user searching by id doesn't exist"() {
        given:
        String id = "1"

        when:
        userService.getUserById(id)

        then:
        1 * userRepository.findById(id) >> Optional.empty()

        and:
        thrown(DocumentNotFoundException)
    }

    def"should update user"() {
        given:
        String id = "1"
        UserDTO userToUpdate = GroovyMock()

        when:
        userService.updateUser(id, userToUpdate)

        then:
        1 * userRepository.findById(id) >> Optional.of(getUser())
        1 * userRepository.save(_ as User)
    }

    def"should throw DocumentNotFoundException if user to update searching by given id doesn't exist"() {
        given:
        String id = "1"
        UserDTO userToUpdate = GroovyMock()

        when:
        userService.updateUser(id, userToUpdate)

        then:
        1 * userRepository.findById(id) >> Optional.empty()

        and:
        thrown(DocumentNotFoundException)
    }

    def"should delete user"() {
        given:
        String id = "1"

        when:
        userService.deleteUser(id)

        then:
        1 * userRepository.findById(id) >> Optional.of(getUser())
        1 * userRepository.delete(getUser())
    }

    def"should throw DocumentNotFoundException if user to delete searching by given id doesn't exist"() {
        given:
        String id = "1"

        when:
        userService.deleteUser(id)

        then:
        1 * userRepository.findById(id) >> Optional.empty()

        and:
        thrown(DocumentNotFoundException)
    }

    private User getUser() {
        return new User("1", "Anne", "anne@gmail.com", "anne123", List.of(Role.USER))
    }

    private Page<User> getPagedUsers() {
        List<User> users = List.of(
            getUser()
        )

        return new PageImpl<User>(users, PageRequest.of(0, 5), 5)
    }
}
