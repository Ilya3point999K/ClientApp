package com.ilya3point999k.clientapp

interface IClientModel {
    fun insert(data: HashMap<String, String>)
    fun get(request: List<String>): HashMap<String, String>
    fun update(data: HashMap<String, String>)
}