package use_cases

import com.v4vic.model.BibleRepository
import com.v4vic.model.Book
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BibleRepository
) {
    operator fun invoke(): StateFlow<List<Book>> = repository.booksFlow
}