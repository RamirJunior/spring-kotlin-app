package br.com.ramir.springkotlinapp.datasource.mock

import br.com.ramir.springkotlinapp.datasource.BankDataSource
import br.com.ramir.springkotlinapp.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    val banks = listOf(
        Bank("1234", 3.14, 0),
        Bank("1515", 2.8, 1),
        Bank("2521", 4.5, 1)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull() { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank for account $accountNumber")
}
