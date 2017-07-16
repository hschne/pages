package at.hschroedl.pages.web.rest

import at.hschroedl.pages.domain.User
import at.hschroedl.pages.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sample")
class SampleResource(val userRepository: UserRepository) {

    @GetMapping("/users")
    fun getUsers() : ResponseEntity<List<User>> {
        val users = userRepository.findAll()
        return ResponseEntity(users, HttpStatus.OK)
    }

}
