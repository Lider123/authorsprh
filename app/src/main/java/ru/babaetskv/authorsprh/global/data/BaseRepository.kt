package ru.babaetskv.authorsprh.global.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.babaetskv.authorsprh.data.network.Result
import java.lang.Exception

abstract class BaseRepository {

    protected suspend fun <T : Any> makeSafeCall(call: suspend () -> T): Result<T> =
            withContext(Dispatchers.IO) {
                return@withContext try {
                    val data = call.invoke()
                    Result.Success(data)
                } catch (e: Exception) {
                    Result.Error(e)
                }
            }
}
