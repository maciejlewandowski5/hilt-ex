package com.example.android.hilt.di

import com.example.android.hilt.data.Post
import com.example.android.hilt.data.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.http.GET
import javax.inject.Inject

interface PostsRetriever {
    @GET("demo/posts")
    suspend fun getPosts(): List<Post>
}

class PostsProvider @Inject constructor(
    private val postsRetriever: PostsRetriever,
    private val dispatcher: CoroutineDispatcher,
) {
    suspend fun getPosts(): Response<List<Post>> {
        return withContext(dispatcher) {
            try {
                Response.Success(postsRetriever.getPosts())
            } catch (throwable: Throwable) {
                val a = throwable
                a
                Response.Error(0, null)
            }
        }

    }
}

