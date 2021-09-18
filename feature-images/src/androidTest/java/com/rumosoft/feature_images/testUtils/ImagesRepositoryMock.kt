package com.rumosoft.feature_images.testUtils

import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.infrastructure.Resource
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class ImagesRepositoryMock : ImagesRepository {

    override suspend fun getImages(): Resource<List<Image>?> {
        TODO("Not yet implemented")
    }

    override suspend fun addImage(image: Image): Resource<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun editImage(image: Image): Resource<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun updateImageTitle(image: Image): Resource<Image> {
        TODO("Not yet implemented")
    }

    override suspend fun removeImage(image: Image): Resource<Unit> {
        TODO("Not yet implemented")
    }

    object FileUtil {

        @Throws(IOException::class)
        fun readFileWithoutNewLineFromResources(fileName: String): String {
            var inputStream: InputStream? = null
            try {
                inputStream = getInputStreamFromResource(fileName)
                val builder = StringBuilder()
                inputStream?.let {
                    val reader = BufferedReader(InputStreamReader(inputStream))

                    var str: String? = reader.readLine()
                    while (str != null) {
                        builder.append(str)
                        str = reader.readLine()
                    }
                }
                return builder.toString()
            } finally {
                inputStream?.close()
            }
        }
        private fun getInputStreamFromResource(fileName: String) =
            javaClass.classLoader?.getResourceAsStream(fileName)
    }
}
