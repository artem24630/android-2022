package com.old.leopards.restaurant.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.old.leopards.restaurant.database.dao.*
import com.old.leopards.restaurant.database.entities.FoodDescription
import com.old.leopards.restaurant.database.entities.FoodItem
import com.old.leopards.restaurant.database.entities.Language
import com.old.leopards.restaurant.database.entities.User
import com.old.leopards.restaurant.database.viewModels.LanguageViewModel
import kotlinx.coroutines.runBlocking

@Database(entities = [User::class, FoodDescription::class,
                     FoodItem::class, Language::class], version = 1, exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun foodItemDao(): FoodItemDao
    abstract fun foodDescriptionDao(): FoodDescriptionDao
    abstract fun languageDao(): LanguageDao
    abstract fun localizedFoodDao(): LocalizedFoodDao

    companion object{
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "restaurant_database"
                ).build()
                fillDatabase(instance)
                INSTANCE = instance
                return instance
            }
        }
    }
}

fun fillDatabase(db : RestaurantDatabase) {
    runBlocking {
        db.languageDao().addLanguage(Language(0, "russian"))
        db.languageDao().addLanguage(Language(0, "english"))

        db.foodItemDao().addFoodItem(FoodItem(0, 220, 300,
            "https://ashaindia.ru/upload/resize_cache/iblock/f7a/450_450_040cd750bba9870f18aada2478b24840a/f7afeb07539a5ce95b3dd08bf4f0432a.jpg"))

        db.foodDescriptionDao().addFoodDescription(FoodDescription(0, 0, 0,
            "Ляоганмашка", "Супер вкусная, супер сладкая вещица"))
        db.foodDescriptionDao().addFoodDescription(FoodDescription(0, 1, 0,
            "Laoganmashka", "Ultra tasty and sweet thingy"))
    }
}
