package com.example.eydemo.presentation.whether.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eydemo.domain.usecase.GetWhetherDataUseCase

class WhetherViewModalFactory(
      private val getWhetherDataUseCase: GetWhetherDataUseCase
) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WhetherViewModal(getWhetherDataUseCase) as T
    }
}