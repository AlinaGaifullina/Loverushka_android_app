package ru.itis.loverushka_app.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.itis.loverushka_app.data.repositotyimpl.CartRepositoryImpl
import ru.itis.loverushka_app.data.repositotyimpl.DishRepositoryImpl
import ru.itis.loverushka_app.data.repositotyimpl.OrderRepositoryImpl
import ru.itis.loverushka_app.data.repositotyimpl.UserRepositoryImpl
import ru.itis.loverushka_app.data.usecaseimpl.AddCartUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.AddDishToCartUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.AddDishToFavouritesUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.AddOrderUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.ChangeCheckedDishesInCartUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.ChangeNumberOfDishInCartUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.DeleteDishFromCartUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.DeleteDishFromFavouritesUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.GetAllDishesUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.GetCartByPhoneNumberUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.GetDishByIdUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.GetFavouritesByUserNumberUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.GetOrderByIdUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.GetOrdersByPhoneNumberUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.LoginUserUseCaseImpl
import ru.itis.loverushka_app.data.usecaseimpl.RegisterUserUseCaseImpl
import ru.itis.loverushka_app.domain.repository.CartRepository
import ru.itis.loverushka_app.domain.repository.DishRepository
import ru.itis.loverushka_app.domain.repository.OrderRepository
import ru.itis.loverushka_app.domain.repository.UserRepository
import ru.itis.loverushka_app.domain.usecase.AddCartUseCase
import ru.itis.loverushka_app.domain.usecase.AddDishToCartUseCase
import ru.itis.loverushka_app.domain.usecase.AddDishToFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.AddOrderUseCase
import ru.itis.loverushka_app.domain.usecase.ChangeCheckedDishesInCartUseCase
import ru.itis.loverushka_app.domain.usecase.ChangeNumberOfDishInCartUseCase
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromCartUseCase
import ru.itis.loverushka_app.domain.usecase.DeleteDishFromFavouritesUseCase
import ru.itis.loverushka_app.domain.usecase.GetAllDishesUseCase
import ru.itis.loverushka_app.domain.usecase.GetCartByPhoneNumberUseCase
import ru.itis.loverushka_app.domain.usecase.GetDishByIdUseCase
import ru.itis.loverushka_app.domain.usecase.GetFavouritesByUserNumberUseCase
import ru.itis.loverushka_app.domain.usecase.GetOrderByIdUseCase
import ru.itis.loverushka_app.domain.usecase.GetOrdersByPhoneNumberUseCase
import ru.itis.loverushka_app.domain.usecase.LoginUserUseCase
import ru.itis.loverushka_app.domain.usecase.RegisterUserUseCase


@Module
@InstallIn(SingletonComponent::class)
class DomainProvidesModule {

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

    @Provides
    fun getFavouritesByUserNumberUseCase(dishRepository: DishRepository): GetFavouritesByUserNumberUseCase =
        GetFavouritesByUserNumberUseCaseImpl(dishRepository)

    @Provides
    fun getDishByIdUseCase(dishRepository: DishRepository): GetDishByIdUseCase =
        GetDishByIdUseCaseImpl(dishRepository)

    @Provides
    fun addCartUseCase(cartRepository: CartRepository): AddCartUseCase =
        AddCartUseCaseImpl(cartRepository)

    @Provides
    fun addDishToCartUseCase(cartRepository: CartRepository): AddDishToCartUseCase =
        AddDishToCartUseCaseImpl(cartRepository)

    @Provides
    fun changeNumberOfDishInCartUseCase(cartRepository: CartRepository): ChangeNumberOfDishInCartUseCase =
        ChangeNumberOfDishInCartUseCaseImpl(cartRepository)

    @Provides
    fun changeCheckedDishesInCartUseCase(cartRepository: CartRepository): ChangeCheckedDishesInCartUseCase =
        ChangeCheckedDishesInCartUseCaseImpl(cartRepository)
    @Provides
    fun deleteDishFromCartUseCase(cartRepository: CartRepository): DeleteDishFromCartUseCase =
        DeleteDishFromCartUseCaseImpl(cartRepository)

    @Provides
    fun getCartByPhoneNumberUseCase(cartRepository: CartRepository): GetCartByPhoneNumberUseCase =
        GetCartByPhoneNumberUseCaseImpl(cartRepository)

    @Provides
    fun getOrdersByPhoneNumberUseCase(orderRepository: OrderRepository): GetOrdersByPhoneNumberUseCase =
        GetOrdersByPhoneNumberUseCaseImpl(orderRepository)

    @Provides
    fun addOrderUseCase(orderRepository: OrderRepository): AddOrderUseCase =
        AddOrderUseCaseImpl(orderRepository)

    @Provides
    fun getOrderByIdUseCase(orderRepository: OrderRepository): GetOrderByIdUseCase =
        GetOrderByIdUseCaseImpl(orderRepository)

}



@Module
@InstallIn(SingletonComponent::class)

internal abstract class DomainBindsModule {

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindDishRepository(impl: DishRepositoryImpl): DishRepository

    @Binds
    abstract fun bindCartRepository(impl: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun bindOrderRepository(impl: OrderRepositoryImpl): OrderRepository

}