package com.example.data.storage

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.UnsplashApi
import com.example.domain.models.Photo
import com.example.domain.usecase.photo.GetPhotosUseCase
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 30

class PhotoPagingSource (
    private val getPhotosUseCase: GetPhotosUseCase,
    private val query: String = ""
): PagingSource<Int, Photo>() {
    var q = ""
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {

            val position = params.key?: STARTING_PAGE_INDEX

            val photoList = getPhotosUseCase.execute(
                nextPageNumber = position,
                pageSize = params.loadSize,
                query = query
            )

            val nextKey = if(photoList.isEmpty()){
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            return LoadResult.Page(
                data = photoList,
                prevKey = if(position == STARTING_PAGE_INDEX) null else position,
                nextKey = nextKey
            )
        }catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}