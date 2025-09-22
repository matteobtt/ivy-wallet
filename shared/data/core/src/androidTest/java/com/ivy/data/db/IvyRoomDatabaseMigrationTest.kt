package com.ivy.data.db

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IvyRoomDatabaseMigrationTest {

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        IvyRoomDatabase::class.java,
        emptyList(),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrateAll() {
        // given:
        // Create earliest version of the database
        helper.createDatabase(TEST_DB, 1).apply {
            close()
        }

        // then:
        // Open latest version of the database.
        // Room validates and executes all migrations.
        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            IvyRoomDatabase::class.java,
            TEST_DB
        ).addMigrations(*IvyRoomDatabase.migrations()).build().apply {
            openHelper.writableDatabase.close()
        }
    }

    companion object {
        private const val TEST_DB = "migration-test"
    }
}