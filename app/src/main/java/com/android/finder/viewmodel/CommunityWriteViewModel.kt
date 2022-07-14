package com.android.finder.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.finder.App
import com.android.finder.R
import com.android.finder.convertToMultipart
import com.android.finder.dataobj.CommunityImgDto
import com.android.finder.network.MainNetWorkUtil
import com.android.finder.network.response.EmailLoginResponse
import com.android.finder.network.response.MessageResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Multipart
import java.io.File

class CommunityWriteViewModel: ViewModel() {
    var isCurious : Boolean = false
    val selectedMbti : MutableLiveData<String> = MutableLiveData("")
    val questionImages : ObservableArrayList<String> = ObservableArrayList()
    var modifyImages : Array<CommunityImgDto> = arrayOf()
    var deleteImageIds : ArrayList<Long> = arrayListOf()
    var addImageUrls : ArrayList<String> = arrayListOf()
    var writeResultMessage = ""
    var modifyResultMessage = ""

    fun writeContent(title : String, content : String) : Boolean {
        if((selectedMbti.value?:"").isNotEmpty()) {
            val params = LinkedHashMap<String, Any>()
            params["title"] = title
            params["content"] = content
            params["mbti"] = selectedMbti.value!!
            params["isQuestion"] = isCurious
            if(!questionImages.isEmpty()) params["images"] = questionImages.map { it }
            val parts = convertToMultipart(params, "communityImages[]")
            MainNetWorkUtil.api.writeCommunityContents(parts.first, parts.second).runCatching {
                val result = this.execute()
                if(!result.isSuccessful) {
                    result.errorBody()?.string()?.let {
                        val response = Gson().fromJson(it, MessageResponse::class.java)
                        writeResultMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                            response.errorResponse.errorMessages[0]
                        } else "입력값을 확인해주세요."
                    }
                } else {
                    writeResultMessage = App.instance.getString(R.string.msg_success_write)
                }
                return result.isSuccessful
            }.onFailure {
                writeResultMessage =App.instance.resources.getString(R.string.error_unspecified_message)
                it.printStackTrace()
            }
        } else {
            writeResultMessage = App.instance.resources.getString(R.string.error_mbti_empty)
        }
        return false
    }

    fun modifyContent(communityId : Long, title : String, content : String) : Boolean {
        if((selectedMbti.value?:"").isNotEmpty()) {
            val params = LinkedHashMap<String, Any>()
            params["title"] = title
            params["content"] = content
            params["mbti"] = selectedMbti.value!!
            params["isQuestion"] = isCurious
//            if(deleteImageIds.isNotEmpty()) params["deleteImageIds[]"] = deleteImageIds
            if(addImageUrls.isNotEmpty()) params["images"] = addImageUrls
            val parts = convertToMultipart(params, "addImages[]")
            var deleteImageList : ArrayList<MultipartBody.Part> = ArrayList()
            if(deleteImageIds.isNotEmpty()) deleteImageIds.forEach {
                deleteImageList.add(MultipartBody.Part.createFormData("deleteImageIds", it.toString()))
            }
            MainNetWorkUtil.api.modifyCommunityContents(communityId, parts.first, deleteImageList, parts.second).runCatching {
                val result = this.execute()
                if(!result.isSuccessful) {
                    result.errorBody()?.string()?.let {
                        val response = Gson().fromJson(it, MessageResponse::class.java)
                        writeResultMessage = if(response.errorResponse.errorMessages.isNotEmpty()) {
                            response.errorResponse.errorMessages[0]
                        } else "입력값을 확인해주세요."
                    }
                } else {
                    writeResultMessage = App.instance.getString(R.string.msg_success_write)
                }
                return result.isSuccessful
            }.onFailure {
                writeResultMessage =App.instance.resources.getString(R.string.error_unspecified_message)
                it.printStackTrace()
            }
        } else {
            writeResultMessage = App.instance.resources.getString(R.string.error_mbti_empty)
        }
        return false
    }
}