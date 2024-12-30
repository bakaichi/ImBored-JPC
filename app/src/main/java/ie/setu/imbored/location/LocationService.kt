package ie.setu.imbored.location

import android.location.Location
import com.google.protobuf.DescriptorProtos
import kotlinx.coroutines.flow.Flow

interface LocationService {
    fun getLocationFlow(): Flow<Location?>
}
