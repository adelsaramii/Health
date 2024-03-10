package core.main.home.domain

import arrow.core.Either
import core.main.home.data.remote.dto.HealthInfoBulkDto
import data.remote.Failure

interface HomeRepository {
    suspend fun healthInfoBulk(): Either<Failure, HealthInfoBulkDto>
}