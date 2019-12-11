package com.droidcon.commons.date

import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import javax.inject.Inject

class GetNowDate @Inject constructor() {

    operator fun invoke(): LocalDate = LocalDate.now(ZoneId.of("GMT+1"))
}