package di

import core.auth.data.AuthRepositoryImpl
import core.auth.domain.AuthRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    single {
        AuthRepositoryImpl(get() , get())
    } bind AuthRepository::class

}