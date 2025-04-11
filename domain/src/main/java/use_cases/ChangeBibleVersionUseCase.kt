package use_cases

import com.v4vic.model.BibleRepository
import javax.inject.Inject

class ChangeBibleVersionUseCase @Inject constructor(
    private val repository: BibleRepository
) {
    operator fun invoke() = repository.changeVersion()
}