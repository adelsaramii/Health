package core.main.home.data

import arrow.core.Either
import core.auth.data.remote.AuthService
import core.main.home.data.remote.HomeService
import core.main.home.data.remote.dto.HealthInfoBulkDto
import core.main.home.domain.HomeRepository
import data.remote.Failure

class HomeRepositoryImpl(
    private val homeService: HomeService
) : HomeRepository {

    override suspend fun healthInfoBulk(): Either<Failure, HealthInfoBulkDto> {
        return homeService.healthInfoBulk()
    }

}