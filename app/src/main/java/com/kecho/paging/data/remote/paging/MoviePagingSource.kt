package com.kecho.hilttest.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kecho.hilttest.data.remote.MovieService
import com.kecho.hilttest.entity.Movie
import javax.inject.Inject

/**
 *
 */
class MoviePagingSource @Inject constructor(
    private val movieService: MovieService,
    private val query: String
) : PagingSource<Int, Movie>() {

    companion object {
        const val START_PAGE = 1
    }

    /**
     * 스와이프 Refresh나 데이터 업데이트 등으로 현재 목록을 대체할 새 데이터를 로드할 때 사용된다.
     * PagingData는 Component에서 설명한 것처럼 새로고침 될 때마다 상응하는 PagingData를 생성해야한다.
     * 즉, 수정이 불가능하고 새로운 인스턴스를 만들어야한다.
     * 가장 최근에 접근한 인덱스인 anchorPosition으로 주변 데이터를 다시 로드한다.
     *
     * refresh시 다시 시작할 키를 반환
     * anchorPosition : 사용자가 보고있는 아이템 위치 (새로고침 기준점)
     * closestPageToPosition : 주어진 'anchorPosition'에 가장 가까운 페이지
     */
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) // 현재 페이지가 이전 페이지 다음에 오는 케이스
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1) // 현재 페이지가 다음 페이지 이전에 오는 케이스
        }
    }

    /**
     * params.key에 현재 페이지 인덱스를 관리한다. 처음 데이터를 로드할 때에는 null이 반환된다.
     * params.loadSize는 가져올 데이터의 갯수를 관리
     *
     * 파라미터를 바탕으로 페이지의 데이터를 반환
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        Log.d("com.kecho.hilttest", "load")

        val position = params.key ?: START_PAGE
        val data = movieService.getMovie(targetDt = query)
        val movieList = when (data.isSuccessful) {
            true -> {
                data.body()?.boxOfficeResult?.dailyBoxOfficeList?.map {
                    Movie(it.movieNm, it.openDt, it.rank)
                } ?: emptyList()
            }

            else -> {
                emptyList()
            }
        }
        Log.d("com.kecho.hilttest", "movieList size : ${movieList.size}")

        return try {
            LoadResult.Page(
                data = movieList,
                prevKey = if (position == START_PAGE) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}