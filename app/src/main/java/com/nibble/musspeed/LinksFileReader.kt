package com.nibble.musspeed
import java.io.File

class LinksFileReader {
    fun ReadLinks(fileAndExtension:String): MutableList<MutableList<String>> {
        val links = mutableListOf<MutableList<String>>()
        var currentSpeed = -1;
        File(fileAndExtension)
            .useLines { lines -> lines
                .forEach {
                    if(it[0].isDigit())
                    {
                        if(it[0].toInt() >= links.size)
                            for (i in links.size..it[0].toInt())
                                links.add(mutableListOf())
                        currentSpeed = it[0].toInt()
                    }
                    else
                        if(currentSpeed!=-1)
                            links[currentSpeed].add(it)
                }
            }
        return links
    }
}