package com.sevenorcas.field.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;

import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.State;

import java.util.Date;
import java.util.List;

public class GraphRepo implements DbI {

    private AppDatabase appDatabase;

    public GraphRepo(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                //.addMigrations(MIGRATION_1_2)
                .build();
    }


    public Long nextId(){
        Long id = appDatabase.graphDao().maxId();
        id = id != null? id : 0;
        return id+1;
    }

    public void insert(Config config,
                       State state,
                       String descr,
                       int participants){
        Graph graph = new Graph();
//        graph.setId(nextId());
        graph.setCreated(new Date());
        graph.setConfig(config.encode());
        graph.setResult(state.encodeResult());
        graph.setData(state.encodeData());
        graph.setDescr(descr);
        graph.setParticipants(participants);
        graph.setMinY(state.getMinY());
        graph.setMaxY(state.getMaxY());
        insert(graph);
    }

    public void insert(final Graph graph){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.graphDao().insert(graph);
                return null;
            }
        }.execute();
    }

    public List<Graph> getAll(){
        return appDatabase.graphDao().getAll();
    }

    public List<Graph> getById(long graphId){
        return appDatabase.graphDao().getById(graphId);
    }

    public List<Graph> getByDescr(String descr){
        return appDatabase.graphDao().getByDescr(descr);
    }


    public void delete(final long graphId) {
        final List<Graph> list = getById(graphId);
        if(list != null && list.size()>0) {
            for (Graph g : list){
                delete(g);
            }
        }
    }

    public void delete(final Graph graph) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.graphDao().delete(graph);
                return null;
            }
        }.execute();
    }


}

