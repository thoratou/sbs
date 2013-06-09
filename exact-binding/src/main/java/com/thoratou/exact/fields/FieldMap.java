/*****************************************************************************
 * This source file is part of SBS (Screen Build System),                    *
 * which is a component of Screen Framework                                  *
 *                                                                           *
 * Copyright (c) 2008-2013 Ratouit Thomas                                    *
 *                                                                           *
 * This program is free software; you can redistribute it and/or modify it   *
 * under the terms of the GNU Lesser General Public License as published by  *
 * the Free Software Foundation; either version 3 of the License, or (at     *
 * your option) any later version.                                           *
 *                                                                           *
 * This program is distributed in the hope that it will be useful, but       *
 * WITHOUT ANY WARRANTY; without even the implied warranty of                *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser   *
 * General Public License for more details.                                  *
 *                                                                           *
 * You should have received a copy of the GNU Lesser General Public License  *
 * along with this program; if not, write to the Free Software Foundation,   *
 * Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA, or go to   *
 * http://www.gnu.org/copyleft/lesser.txt.                                   *
 *****************************************************************************/

package com.thoratou.exact.fields;

import com.thoratou.exact.Entry;

import java.util.*;

public class FieldMap<Key extends Entry<Key>, Value extends Entry<Value>> implements Entry< FieldMap<Key, Value> > {
    private Hashtable<Key, Value> table;
    private Value prototype;

    public FieldMap(Value prototype) {
        table = new Hashtable<Key, Value>();
        this.prototype = prototype;
    }

    public Value allocate(Key key){
        Value ret = null;
        if(key != null)
        {
            Value value = prototype.copy();
            table.put(key.copy(), value);
            ret = value;
        }
        return ret;
    }

    public void clear(){
        table.clear();
    }

    public boolean containsKey(Key key){
        return table.contains(key);
    }

    public boolean containsValue(Value value){
        return table.containsValue(value);
    }

    public Enumeration<Value> elements(){
        return table.elements();
    }

    public Set<java.util.Map.Entry<Key, Value>> entrySet(){
        return table.entrySet();
    }

    public boolean isEmpty(){
        return table.isEmpty();
    }

    public Enumeration<Key> keys(){
        return table.keys();
    }

    public Set<Key> keySet(){
        return table.keySet();
    }

    public Value get(Key key){
        Value value = table.get(key);
        return value == null ? allocate(key) : value;
    }

    public Value remove(Key key){
        return table.remove(key);
    }

    public Collection<Value> values(){
        return table.values();
    }

    @Override
    public void merge(FieldMap<Key, Value> tableEntry) {
        Set<java.util.Map.Entry<Key, Value>> entrySet = tableEntry.table.entrySet();
        Iterator<java.util.Map.Entry<Key, Value>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            java.util.Map.Entry<Key, Value> next = iterator.next();

            Key key = next.getKey();
            Value value = next.getValue();

            Key keyCopy = key.copy();
            Value valueCopy = value.copy();

            this.table.put(keyCopy, valueCopy);
        }
    }

    @Override
    public FieldMap<Key, Value> copy() {
        FieldMap<Key, Value> fieldMap = new FieldMap<Key, Value>(prototype.copy());
        fieldMap.merge(this);
        return fieldMap;
    }
}
