package ru.itis.loverushka_app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.itis.loverushka_app.data.CartDatabase
import ru.itis.loverushka_app.data.DishDatabase
import ru.itis.loverushka_app.data.FavouritesDatabase
import ru.itis.loverushka_app.data.OrderDatabase
import ru.itis.loverushka_app.data.UserDatabase
import ru.itis.loverushka_app.data.dao.CartDao
import ru.itis.loverushka_app.data.dao.DishDao
import ru.itis.loverushka_app.data.dao.FavouritesDao
import ru.itis.loverushka_app.data.dao.OrderDao

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

    @Provides
    fun provideAppDatabase(context: Context): UserDatabase {
        return UserDatabase.get(context)
    }

    @Provides
    fun provideFavouritesDatabase(context: Context): FavouritesDatabase {
        return FavouritesDatabase.get(context)
    }

    @Provides
    fun provideFavouritesDao(favouritesDatabase: FavouritesDatabase): FavouritesDao {
        return favouritesDatabase.favouritesDao()
    }

    @Provides
    fun provideDishDatabase(context: Context): DishDatabase {
        return DishDatabase.get(context)
    }

    @Provides
    fun provideDishDao(dishDatabase: DishDatabase): DishDao {
        return dishDatabase.dishDao()
    }

    @Provides
    fun provideCartDatabase(context: Context): CartDatabase {
        return CartDatabase.get(context)
    }

    @Provides
    fun provideCartDao(cartDatabase: CartDatabase): CartDao {
        return cartDatabase.cartDao()
    }

    @Provides
    fun provideOrderDatabase(context: Context): OrderDatabase {
        return OrderDatabase.get(context)
    }

    @Provides
    fun provideOrderDao(orderDatabase: OrderDatabase): OrderDao {
        return orderDatabase.orderDao()
    }
}