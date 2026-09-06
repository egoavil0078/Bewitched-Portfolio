package com.kevinegoavil.bewitched.data.remote.api

import com.kevinegoavil.bewitched.data.remote.dto.producto.CategoriaResponse
import com.kevinegoavil.bewitched.data.remote.dto.producto.ProductoCreateRequest
import com.kevinegoavil.bewitched.data.remote.dto.producto.ProductoImagenResponse
import com.kevinegoavil.bewitched.data.remote.dto.producto.ProductoResponse
import com.kevinegoavil.bewitched.data.remote.dto.producto.ProductoUpdateRequest
import com.kevinegoavil.bewitched.data.remote.dto.producto.ReordenarImagenesRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoApiServiceExample {

    @GET("api/productos")
    fun listarProductos(
        @Query("categoria") categoria: String? = null,
        @Query("mineral") mineral: String? = null
    ): Call<List<ProductoResponse>>

    @GET("api/productos/{id}")
    fun obtenerProducto(
        @Path("id") productoId: Long
    ): Call<ProductoResponse>

    @GET("api/favoritos")
    fun listarFavoritos(): Call<List<ProductoResponse>>

    @POST("api/favoritos/{productoId}")
    fun agregarFavorito(
        @Path("productoId") productoId: Long
    ): Call<Void>

    @DELETE("api/favoritos/{productoId}")
    fun eliminarFavorito(
        @Path("productoId") productoId: Long
    ): Call<Void>

    @GET("api/admin/productos")
    fun listarProductosAdmin(): Call<List<ProductoResponse>>

    @GET("api/admin/productos/sin-stock")
    fun listarProductosSinStockAdmin(): Call<List<ProductoResponse>>

    @POST("api/admin/productos")
    fun crearProductoAdmin(
        @Body request: ProductoCreateRequest
    ): Call<ProductoResponse>

    @PUT("api/admin/productos/{productoId}")
    fun actualizarProductoAdmin(
        @Path("productoId") productoId: Long,
        @Body request: ProductoUpdateRequest
    ): Call<ProductoResponse>

    @DELETE("api/admin/productos/{productoId}")
    fun desactivarProductoAdmin(
        @Path("productoId") productoId: Long
    ): Call<Void>

    @Multipart
    @POST("api/admin/productos/{productoId}/imagenes/upload")
    fun subirImagenAdmin(
        @Path("productoId") productoId: Long,
        @Part foto: MultipartBody.Part,
        @Part("altText") altText: RequestBody? = null
    ): Call<ProductoImagenResponse>

    @DELETE("api/admin/productos/{productoId}/imagenes/{imagenId}")
    fun eliminarImagenAdmin(
        @Path("productoId") productoId: Long,
        @Path("imagenId") imagenId: Long
    ): Call<Void>

    @GET("api/categorias")
    fun listarCategorias(): Call<List<CategoriaResponse>>

    @Multipart
    @PUT("api/admin/productos/{productoId}/imagenes/reemplazar")
    fun reemplazarImagenesAdmin(
        @Path("productoId") productoId: Long,
        @Part fotos: List<MultipartBody.Part>,
        @Part("altText") altText: RequestBody? = null
    ): Call<List<ProductoImagenResponse>>

    @PUT("api/admin/productos/{productoId}/imagenes/reordenar")
    fun reordenarImagenesAdmin(
        @Path("productoId") productoId: Long,
        @Body request: ReordenarImagenesRequest
    ): Call<Unit>
}
