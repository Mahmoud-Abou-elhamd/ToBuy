package com.mahmoud.android.tobuy

import com.airbnb.epoxy.EpoxyController
import com.mahmoud.android.tobuy.ui.epoxy.models.HeaderEpoxyModel
import com.mahmoud.android.tobuy.ui.home.HomeEpoxyController

fun EpoxyController.addHeaderModel(headerText: String) {
    HeaderEpoxyModel(headerText).id(headerText).addTo(this)
}