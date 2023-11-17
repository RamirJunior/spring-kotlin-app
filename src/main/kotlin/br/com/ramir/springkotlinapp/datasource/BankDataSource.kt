package br.com.ramir.springkotlinapp.datasource

import br.com.ramir.springkotlinapp.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
    fun retrieveBank(accountNumber: String): Bank
}
