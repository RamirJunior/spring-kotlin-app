package br.com.ramir.springkotlinapp.service

import br.com.ramir.springkotlinapp.datasource.BankDataSource
import br.com.ramir.springkotlinapp.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNumber: String): Bank = dataSource.retrieveBank(accountNumber)
}
