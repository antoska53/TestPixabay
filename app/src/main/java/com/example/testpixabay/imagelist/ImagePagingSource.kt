package com.example.testpixabay.imagelist

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.testpixabay.BuildConfig
import com.example.testpixabay.network.Image
import com.example.testpixabay.network.RetrofitModule
import retrofit2.HttpException
import java.io.IOException

class ImagePagingSource(val category: String) : PagingSource<Int, Image>() {
    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = RetrofitModule.imageApi.getImagesPaging(
                BuildConfig.API_KEY,
                category,
                nextPageNumber,
                params.loadSize
            )
            return LoadResult.Page(
                data = response.images,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1, // Only paging forward.
                nextKey = if (response.images.size < params.loadSize) null else nextPageNumber.plus(1)
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }
}