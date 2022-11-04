package com.shashavs.trackviewer

import com.google.android.gms.maps.model.LatLng
import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.domain.usecases.GetShareLink
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testGetShareLink() {
        val firstPoint = LatLng(52.315610,5.103540)
        val secondPoint = LatLng(52.315870,5.102640)
        val lastPoint = LatLng(52.315950,5.102350)
        val track = Track(
            points = listOf(firstPoint, secondPoint, lastPoint)
        )
        val expected = "https://www.google.com/maps/dir/?api=1&origin=${firstPoint.latitude},${firstPoint.longitude}&destination=${lastPoint.latitude},${lastPoint.longitude}"
        val actual = GetShareLink().invoke(track)
        assertEquals(expected, actual)
    }
}