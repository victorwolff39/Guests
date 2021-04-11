package net.alerok.guests.service.repository

import android.content.ContentValues
import android.content.Context
import net.alerok.guests.service.constants.DatabaseConstants
import net.alerok.guests.service.models.GuestModel
import java.lang.Exception

class GuestRepository private constructor(context: Context) {

    private var mGuestDatabaseHelper: GuestDatabaseHelper = GuestDatabaseHelper(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized) {
                repository = GuestRepository(context)
            }
            return repository
        }
    }

    fun getAll(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            val columns = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.ID,
                DatabaseConstants.GUEST.COLUMNS.NAME,
                DatabaseConstants.GUEST.COLUMNS.PRESENCE,
            )

            val cursor = db.query(
                DatabaseConstants.GUEST.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count != 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                    val isPresent =
                        (cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, isPresent)
                    list.add(guest)
                }
            }
            cursor.close()

            list
        } catch (e: Exception) {
            list
        }
    }

    fun getPresent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            val cursor =
                db.rawQuery(
                    "SELECT ${DatabaseConstants.GUEST.COLUMNS.ID}, " +
                            "${DatabaseConstants.GUEST.COLUMNS.NAME}, " +
                            "${DatabaseConstants.GUEST.COLUMNS.PRESENCE} " +
                            "from ${DatabaseConstants.GUEST.TABLE_NAME} " +
                            "WHERE ${DatabaseConstants.GUEST.COLUMNS.PRESENCE} = 1",
                    null
                )

            if (cursor != null && cursor.count != 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                    val isPresent =
                        (cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, isPresent)
                    list.add(guest)
                }
            }
            cursor.close()

            list
        } catch (e: Exception) {
            list
        }
    }

    fun getAbsent(): List<GuestModel> {
        val list: MutableList<GuestModel> = ArrayList()

        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            val cursor =
                db.rawQuery(
                    "SELECT ${DatabaseConstants.GUEST.COLUMNS.ID}, " +
                            "${DatabaseConstants.GUEST.COLUMNS.NAME}, " +
                            "${DatabaseConstants.GUEST.COLUMNS.PRESENCE} " +
                            "from ${DatabaseConstants.GUEST.TABLE_NAME} " +
                            "WHERE ${DatabaseConstants.GUEST.COLUMNS.PRESENCE} = 0",
                    null
                )

            if (cursor != null && cursor.count != 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                    val isPresent =
                        (cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, isPresent)
                    list.add(guest)
                }
            }
            cursor.close()

            list
        } catch (e: Exception) {
            list
        }
    }

    // CRUD - Create, Read, Update and Delete
    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null

        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())
            val columns = arrayOf(
                DatabaseConstants.GUEST.COLUMNS.NAME,
                DatabaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor = db.query(
                DatabaseConstants.GUEST.TABLE_NAME,
                columns,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()

                val name =
                    cursor.getString(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.NAME))
                val isPresent =
                    (cursor.getInt(cursor.getColumnIndex(DatabaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                guest = GuestModel(id, name, isPresent)
            }
            cursor.close()

            guest
        } catch (e: Exception) {
            guest
        }
    }

    fun save(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val values = ContentValues()
            values.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guest.isPresent)

            db.insert(DatabaseConstants.GUEST.TABLE_NAME, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val values = ContentValues()
            values.put(DatabaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DatabaseConstants.GUEST.COLUMNS.PRESENCE, guest.isPresent)

            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DatabaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val selection = DatabaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DatabaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

}