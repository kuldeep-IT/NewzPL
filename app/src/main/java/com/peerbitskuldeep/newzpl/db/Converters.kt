package com.peerbitskuldeep.newzpl.db

import androidx.room.TypeConverter
import com.peerbitskuldeep.newzpl.jsondata.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source): String
    {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source
    {
        return Source(name, name)
    }

}