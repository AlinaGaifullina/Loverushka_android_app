package ru.itis.loverushka_app.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.loverushka_app.data.repositotyimpl.DishRepositoryImpl
import ru.itis.loverushka_app.data.repositotyimpl.UserRepositoryImpl
import ru.itis.loverushka_app.data.usecaseimpl.AddDishToFavouritesUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.DeleteDishFromFavouritesUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.GetAllDishesUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.LoginUserUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.RegisterUserUseCaseImpl
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.repository.UserRepository
import ru.itis.loverushka_app.domain.usecase.AddDishToFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.GetAllDishesUseCase
import ru.itis.loverushka_app.domain.usecase.LoginUserUseCase
import ru.itis.loverushka_app.domain.usecase.RegisterUserUseCase


@Module
@InstallIn(SingletonComponent::class)
class DomainProvidesModule {

//    @Provides
//    fun getFilmByIdUseCase(filmDetailsRepository: FilmDetailsRepository): GetFilmByIdUseCase =
//        GetFilmByIdUseCaseImpl(filmDetailsRepository)

    @Provides
    fun addDishToFavouritesUseCase(dishRepository: DishRepository): AddDishToFavouritesUseCase =
        AddDishToFavouritesUseCaseImpl(dishRepository)

    @Provides
    fun deleteDishFromFavouritesUseCase(dishRepository: DishRepository): DeleteDishFromFavouritesUseCase =
        DeleteDishFromFavouritesUseCaseImpl(dishRepository)

    @Provides
    fun getAllDishesUseCase(dishRepository: DishRepository): GetAllDishesUseCase =
        GetAllDishesUseCaseImpl(dishRepository)

    @Provides
    fun loginUserUseCase(userRepository: UserRepository): LoginUserUseCase =
        LoginUserUseCaseImpl(userRepository)

    @Provides
    fun registerUserUseCase(userRepository: UserRepository): RegisterUserUseCase =
        RegisterUserUseCaseImpl(userRepository)

//    @Provides
//    fun getFavouritesByUsernameUseCase(filmDetailsRepository: FilmDetailsRepository): GetFavouritesByUsernameUseCase =
//        GetFavouritesByUsernameUseCaseImpl(filmDetailsRepository)

}



@Module
@InstallIn(SingletonComponent::class)

internal abstract class DomainBindsModule {

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindDishRepository(impl: DishRepositoryImpl): DishRepository

}