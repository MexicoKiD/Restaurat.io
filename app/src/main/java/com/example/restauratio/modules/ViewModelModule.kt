package com.example.restauratio.modules

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.restauratio.cart.CartViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

    @Provides
    @ActivityScoped
    fun provideSharedViewModel(activity: AppCompatActivity): CartViewModel {
        return ViewModelProvider(activity)[CartViewModel::class.java]
    }
}