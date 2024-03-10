package core.main.home.data.remote

import arrow.core.Either
import core.main.home.data.remote.dto.HealthInfoBulkDto
import data.remote.Failure

interface HomeService {
    suspend fun healthInfoBulk(): Either<Failure, HealthInfoBulkDto>
}