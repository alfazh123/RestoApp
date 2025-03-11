package com.example.core.di

import androidx.room.Room
import com.example.core.data.RestaurantRepository
import com.example.core.data.datastore.SettingPreference
import com.example.core.data.datastore.dataStore
import com.example.core.data.local.LocalDataSource
import com.example.core.data.local.RestaurantDatabase
import com.example.core.data.remote.RemoteDataSource
import com.example.core.domain.repository.IRestaurantRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<RestaurantDatabase>().restaurantDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            RestaurantDatabase::class.java, "Restaurant.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "restaurant-api.dicoding.dev"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/DTfK4/xJroJfN4ZVD29ITgA2f8OWSO6Hm7H9G/sMKdA=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restaurant-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(com.example.core.data.remote.network.ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single { SettingPreference(androidContext().dataStore) }
    single<IRestaurantRepository> { RestaurantRepository(get(), get(), get()) }
}