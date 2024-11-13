package mouhamed.dore.myapplication


import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("search/movie")
    suspend fun getFilmsParMotCle(@Query("api_key") apikey : String, @Query("query")motcle: String, @Query("language") language: String) : Movies

    @GET("search/tv")
    suspend fun getSeriesParMotCle(@Query("api_key") apikey : String, @Query("query")motcle: String, @Query("language") language: String) : Series

    @GET("search/person")
    suspend fun getPersonsParMotCle(@Query("api_key") apikey : String, @Query("query")motcle: String,@Query("language") language: String) : Persons

    @GET("trending/movie/week")
    suspend fun derniersFilms(@Query("api_key") apikey: String, @Query("language") language: String): Movies

    @GET("trending/tv/week")
    suspend fun dernieresSeries(@Query("api_key") apikey: String, @Query("language") language: String): Series

    @GET("trending/person/week")
    suspend fun dernieresPersons(@Query("api_key") apikey: String, @Query("language") language: String): Persons

    @GET("movie/{movie_id}?append_to_response=credits")
    suspend fun getFilmDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): FilmDetail

    @GET("tv/{serie_id}?append_to_response=credits")
    suspend fun getSerieDetails(@Path("serie_id") serieId: Int, @Query("api_key") apiKey: String, @Query("language") language: String ): SerieDetails

    @GET("person/{person_id}?append_to_response=credits")
    suspend fun getPersonDetails(@Path("person_id") personId: Int, @Query("api_key") api_key: String, @Query("api_key") language: String): PersonDetail

}