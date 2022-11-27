package com.example.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.api.UnsplashApi
import com.example.data.model.FilterPhoto
import com.example.domain.models.Photo
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 30


class PhotoPagingSource (
    private val api: UnsplashApi,
    private val filter: FilterPhoto
): PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {
            val position = params.key?: STARTING_PAGE_INDEX
            val photoList = if (filter.querySearch.isBlank()) {
                api.getPhotos(
                nextPageNumber = position,
                pageSize = params.loadSize,
                popular = filter.popular
            )
            } else {
                api.getSearchPhotos(
                    nextPageNumber = position,
                    pageSize = params.loadSize,
                    query = filter.querySearch
                    //popular = filter.popularSearch,
                    //orientation = filter.orientation
                ).result
            }



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