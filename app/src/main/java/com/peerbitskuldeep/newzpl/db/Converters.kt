package com.peerbitskuldeep.newzpl.db

import androidx.room.TypeConverter
import com.peerbitskuldeep.newzpl.jsondata.Source

class Converters {

    //Source type to String
    @TypeConverter
    fun fromSource(source: Source): String
    {
        return source.name
    }
    // convert String to Source type
    @TypeConverter
    fun toSource(name: String): Source
    {
        return Source(name, name)
    }

}