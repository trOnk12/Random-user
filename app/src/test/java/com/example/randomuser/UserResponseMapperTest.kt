package com.example.randomuser

import com.example.randomuser.data.model.*
import com.example.randomuser.domain.mapper.UserResponseMapper
import com.example.randomuser.domain.model.User
import org.junit.Assert
import org.junit.Test

class UserResponseMapperTest {

    private val userResponseMapper = UserResponseMapper()

    @Test
    fun `'given a test response when mapping to user result matches the expected value'`() {
        //given
        val expected = listOf(User(testId.value))
        //when
        val result = userResponseMapper.toUsers(testResponse)
        //then
        Assert.assertEquals(expected, result)
    }

}

val testId = Id(name = "testIdName", value = "testIdValue")

val testResponse = UserResponse(
    results = listOf(
        element = Results(
            gender = "mal",
            name = Name(title = "", first = "test", last = "user"),
            location = Location(
                street = Street(number = 0, name = ""),
                city = "",
                state = "",
                country = "",
                postcode = 0,
                coordinates = Coordinates(
                    latitude = 0.0,
                    longitude = 0.0
                ),
                timezone = Timezone(offset = "", description = "")
            ),
            email = "",
            login = Login(
                uuid = "",
                username = "",
                password = "",
                salt = "",
                md5 = "",
                sha1 = "",
                sha256 = ""
            ),
            dob = Dob(date = "", age = 0),
            registered = Registered(date = "", age = 0),
            phone = "",
            cell = "",
            id = testId,
            picture = Picture(
                large = "",
                medium = "",
                thumbnail = ""
            ),
            nat = ""
        )
    ),
    info = Info(seed = "", results = 0, page = 0, version = 0.0)
)


