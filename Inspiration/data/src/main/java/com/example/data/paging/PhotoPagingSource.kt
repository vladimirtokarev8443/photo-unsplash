package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.models.Photo
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val PAGE_SIZE = 15

typealias LoaderPhoto = suspend (pageIndex: Int, pageSize: Int) -> List<Photo>

class PhotoPagingSource (
    private val loader: LoaderPhoto
): PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {
            val pageIndex = params.key ?: STARTING_PAGE_INDEX

            val photos = loader.invoke(pageIndex, params.loadSize)

            return LoadResult.Page(
                data = photos,
                prevKey = if(pageIndex == STARTING_PAGE_INDEX) null else pageIndex - 1,
                //prevKey = if(pageIndex == STARTING_PAGE_INDEX) null else pageIndex,//попробовать -1
                //nextKey = if (photos.size == params.loadSize) pageIndex + (params.loadSize / PAGE_SIZE) else null
                nextKey = if(photos.isEmpty()) null else pageIndex + (params.loadSize / PAGE_SIZE)
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