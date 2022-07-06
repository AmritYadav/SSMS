package com.sts.ssms.paging.documents
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.documents.repository.DocumentsRepository
import com.sts.ssms.ui.helpdesk.documents.model.Documents

class DocumentsDataSourceFactory(private val documentsRepository: DocumentsRepository) :
    DataSource.Factory<Int, Documents>() {

    val documentDataSource = MutableLiveData<DocumentsDataSource>()

    override fun create(): DataSource<Int, Documents> {
        val dataSource = DocumentsDataSource(documentsRepository)
        documentDataSource.postValue(dataSource)
        return dataSource
    }

    fun getRepository() = documentsRepository

}