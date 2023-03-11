package com.example.newsappproba

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.simpleframework.xml.*


@Root(name = "rss", strict = false)
data class RSSFeed @JvmOverloads constructor(
    @field:ElementList(name = "item", inline = true, required = false)
    @param:ElementList(name = "item", inline = true, required = false)
    @field:Path("channel")
    @param:Path("channel")
    var items: List<NewsArticle>? = null
)


@Entity(tableName = "news_table")
@Root(name = "item", strict = false)
data class NewsArticle @JvmOverloads constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    @ColumnInfo(name = "title")

    var title: String? = null,

    @ColumnInfo(name = "description")
    @field:Element(name = "description")
    @param:Element(name = "description")
    var description: String? = null,

    @field:Attribute(name = "src")
    @param:Attribute(name = "src")
    @field:Path("image/img")
    @param:Path("image/img")
    var image: String? = null,

    @field:Attribute(name = "src")
    @param:Attribute(name = "src")
    @field:Path("thumb/img")
    @param:Path("thumb/img")
    val thumb: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var newsArticleId: Long = 0L
}

//@Root(name = "thumb", strict = false)
//data class Image(
//    @field:Element(name = "img")
//    @param:Element(name = "img")
//    val img: Img? = null
//)
//
//@Root(name = "img", strict = false)
//data class Img(
//    @field:Attribute(name = "src")
//    @param:Attribute(name = "src")
//    var src: String = ""
//)

@Entity(tableName = "favorites_table")
class FavoritesTable(
    @ColumnInfo(name = "title") val favoritesTableTitle: String?,
    @ColumnInfo(name = "description") var favoritesTableDescription: String?,
    @ColumnInfo(name = "image_url") var favoritesTableImageUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


//@Entity(tableName = "news_table")
//class NewsTable(
//    @ColumnInfo(name = "title") val title: String?,
//    @ColumnInfo(name = "description") var description: String?,
//    @ColumnInfo(name = "image_url") var imageUrl: String
//) {
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//}



