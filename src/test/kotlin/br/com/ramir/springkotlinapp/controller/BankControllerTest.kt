package br.com.ramir.springkotlinapp.controller

import br.com.ramir.springkotlinapp.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all banks`() {
            // when/then
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234") }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/banks/{accountNumber}")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return the bank with the given account number`() {
            //given
            val accountNumber = 1234

            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("3.14") }
                    jsonPath("$.transactionFee") { value("0") }
                }
        }
    }

    @Test
    fun `should return NOT FOUND if the account number does not exist`() {
        //given
        val accountNumber = "does_not_exist"

        // when/then
        mockMvc.get("$baseUrl/$accountNumber")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }

    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class PostNewBank {

        @Test
        fun `should add a new bank`() {
            //given
            val newBank = Bank(
                accountNumber = "acc123",
                trust = 45.66,
                transactionFee = 3
            )

            //when
            val performPostMethod = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            // then
            performPostMethod
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                }
        }
    }
}
