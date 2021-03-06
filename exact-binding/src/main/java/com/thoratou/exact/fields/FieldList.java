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

import java.util.ArrayList;
import java.util.Iterator;

public class FieldList <T extends Entry<T>> implements Entry<FieldList<T> >{
    private ArrayList<T> array;
    private final T prototype;

    public FieldList(final T prototype) {
        array = new ArrayList<T>();
        this.prototype = prototype;
    }

    public T allocate(){
        T instance = prototype.copy();
        array.add(instance);
        return instance;
    }

    public T allocate(int i){
        T instance = prototype.copy();
        array.add(i, instance);
        return instance;
    }

    public void clear(){
        array.clear();
    }

    public boolean contains(T instance){
        return array.contains(instance);
    }

    public T get(int i){
        T instance = array.get(i);
        return instance == null ? prototype.copy() : instance;
    }

    public int indexOf(T instance){
        return array.indexOf(instance);
    }

    public boolean isEmpty(){
        return array.isEmpty();
    }

    public Iterator<T> iterator() {
        return array.iterator();
    }

    public int lastIndexOf(T instance){
        return array.lastIndexOf(instance);
    }

    public T remove(int i){
        return array.remove(i);
    }

    public boolean remove(T instance){
        return array.remove(instance);
    }

    public T replace(int i){
        T instance = prototype.copy();
        array.set(i, instance);
        return instance;
    }

    public int size(){
        return array.size();
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(){
        return (T[]) array.toArray();
    }

    public T[] toArray(T[] table){
        return array.toArray(table);
    }

    @Override
    public void merge(FieldList<T> listEntry) {
        Iterator<T> iterator = listEntry.array.iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            T copy = item.copy();
            this.array.add(copy);
        }
    }

    @Override
    public FieldList<T> copy() {
        FieldList<T> clone = new FieldList<T>(this.prototype);
        clone.merge(this);
        return clone;
    }
}
