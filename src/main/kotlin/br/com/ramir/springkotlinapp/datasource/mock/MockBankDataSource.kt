package br.com.ramir.springkotlinapp.datasource.mock

import br.com.ramir.springkotlinapp.datasource.BankDataSource
import br.com.ramir.springkotlinapp.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank("1234", 3.14, 0),
        Bank("1515", 2.8, 1),
        Bank("2521", 4.5, 1)
    )

    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank for account $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Account number ${bank.accountNumber} is already registered.")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find a bank for account ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }
}
