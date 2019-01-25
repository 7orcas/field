package com.sevenorcas.field.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface GraphDao {

    @Query("SELECT * FROM graph")
    List<Graph> getAll();

    @Query("SELECT * FROM graph WHERE id = :graphId")
    List<Graph> getById(long graphId);

    @Query("SELECT * FROM graph WHERE descr LIKE :descr")
    List<Graph> getByDescr(String descr);

    @Query("SELECT id FROM graph ORDER BY id DESC LIMIT 1")
    Long maxId();

    @Insert
    void insert(Graph graph);

    @Delete
    void delete(Graph graph);
}
