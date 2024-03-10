package di

import core.auth.data.AuthRepositoryImpl
import core.auth.domain.AuthRepository
import core.main.home.data.HomeRepositoryImpl
import core.main.home.domain.HomeRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    single {
        AuthRepositoryImpl(get(), get())
    } bind AuthRepository::class

    single {
        HomeRepositoryImpl(get())
    } bind HomeRepository::class

}