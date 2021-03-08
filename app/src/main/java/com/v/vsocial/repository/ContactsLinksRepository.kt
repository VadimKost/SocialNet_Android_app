package com.v.vsocial.repository

import android.content.Context
import com.v.vsocial.Api
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsLinksRepository @Inject constructor(
    var api: Api,
    @ApplicationContext var context: Context
) {
}