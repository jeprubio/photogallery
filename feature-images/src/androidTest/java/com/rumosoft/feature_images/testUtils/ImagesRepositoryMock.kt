package com.rumosoft.feature_images.testUtils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rumosoft.feature_images.domain.model.Image
import com.rumosoft.feature_images.domain.usecases.interfaces.repository.ImagesRepository
import com.rumosoft.feature_images.infrastructure.Resource
import com.rumosoft.feature_images.testUtils.ImagesRepositoryMock.FileUtil.readFileWithoutNewLineFromResources
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type

class ImagesRepositoryMock : ImagesRepository {

    override suspend fun getImages(): Resource<List<Image>?> {
        val imagesList = readFileWithoutNewLineFromResources("photos_list.json")
        val listType: Type = object : TypeToken<ArrayList<Image?>?>() {}.type
        val result: List<Image> = Gson().fromJson(imagesList, listType)
        return Resource.Success(result)
    }

    override suspend fun addImage(image: Image): Resource<Long> {
        return Resource.Success(1L)
    }

    override suspend fun editImage(image: Image): Resource<Long> {
        return Resource.Success(1L)
    }

    override suspend fun updateImageTitle(image: Image): Resource<Image> {
        return Resource.Success(Image(1L, "title", "image", "thumbnail"))
    }

    override suspend fun removeImage(image: Image): Resource<Unit> {
        return Resource.Success(Unit)
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
