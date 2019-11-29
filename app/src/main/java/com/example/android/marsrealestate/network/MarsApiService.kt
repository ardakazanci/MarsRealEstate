/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import retrofit2.http.GET

// API adresi.
private const val BASE_URL = " https://android-kotlin-fun-mars-server.appspot.com/"

// Moshi
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

// Retrofit nesnesi oluşturmak için.
private val retrofit = Retrofit.Builder()
        // Web Servisten aldığımız yanıtı String olarak döndürmesi için ScalarsConverterFactory kullandık.
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

// Retrofit'in HTTP istek yöntemleri ile API üzerinde yapılacak isteklerin gösterimi.
interface MarsApiService {
    // GET isteği yapılabilir.
    // URl ' e endpoint olarak realestate eklenir.
    // Bu istek get... metodu ile yapılır
    // Dönüş olarak String alınır.
    @GET("realestate")
    fun getProperties(): Deferred<List<MarsProperty>>

}

// Retrofit ' i başlatmak için kullanılacak object kısmıç
// Tek bir retrofit nesnesi ile retrofit service i bütünleştiriyoruz.
/**
 * Retrofit create () yöntemi, Retrofit hizmetinin kendisini MarsApiService arabirimiyle oluşturur.
 * Bu çağrı pahalı olduğundan ve uygulama yalnızca bir Retrofit servisi örneğine ihtiyaç duyduğundan,
 * hizmeti MarsApi adlı bir genel nesne kullanarak uygulamanın geri kalanına sunar ve Retrofit hizmetini orada tembel bir şekilde başlatırsınız. Tüm kurulum yapıldıktan sonra, uygulamanız MarsApi.retrofitService'i her çağırdığında,
 * MarsApiService'i uygulayan tek bir Retrofit nesnesi elde edilir.
 */
object MarsApi {

    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }

}

