package di

import core.auth.data.AuthRepositoryImpl
import core.auth.domain.AuthRepository
import core.main.home.data.HomeRepositoryImpl
import core.main.home.domain.HomeRepository
import core.main.profile.data.ProfileRepositoryImpl
import core.main.profile.domain.ProfileRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {

    single {
        AuthRepositoryImpl(get(), get())
    } bind AuthRepository::class

    single {
        HomeRepositoryImpl(get())
    } bind HomeRepository::class

    single {
        ProfileRepositoryImpl(get())
    } bind ProfileRepository::class

}