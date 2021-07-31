package com.rumosoft.feature_images.domain.usecases

import android.net.Uri
import com.rumosoft.feature_images.domain.usecases.interfaces.StoreImageFromContentUseCase
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.BitmapRepository
import javax.inject.Inject

class StoreImageFromContentUseCaseImpl @Inject constructor(
    private val imageRepository: BitmapRepository
) : StoreImageFromContentUseCase {
    override operator fun invoke(uri: Uri, width: Int?, height: Int?) =
        imageRepository.storeImageFromContentUri(uri, width, height)
}
