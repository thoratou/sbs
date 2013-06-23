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
import com.thoratou.exact.bom.Extension;

import java.util.ArrayList;
import java.util.Iterator;

public class FieldExtensionList<T extends Entry<T>> implements Entry<FieldExtensionList<T>>{
    private ArrayList<Extension<T>> array;

    public FieldExtensionList() {
        array = new ArrayList<Extension<T>>();
    }

    public Extension<T> allocate(String clazzString) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(clazzString);
        Extension<T> instance = (Extension<T>) clazz.newInstance();
        array.add(instance);
        return instance;
    }

    public Extension<T> allocate(String clazzString, int i) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(clazzString);
        Extension<T> instance = (Extension<T>) clazz.newInstance();
        array.add(i, instance);
        return instance;
    }

    public void clear(){
        array.clear();
    }

    public boolean contains(T instance){
        return array.contains(instance);
    }

    public Extension<T> get(int i){
        Extension<T> instance = array.get(i);
        return instance;
    }

    public int indexOf(T instance){
        return array.indexOf(instance);
    }

    public boolean isEmpty(){
        return array.isEmpty();
    }

    public Iterator<Extension<T>> iterator() {
        return array.iterator();
    }

    public int lastIndexOf(T instance){
        return array.lastIndexOf(instance);
    }

    public Extension<T> remove(int i){
        return array.remove(i);
    }

    public boolean remove(T instance){
        return array.remove(instance);
    }

    public Extension<T> replace(String clazzString, int i) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(clazzString);
        Extension<T> instance = (Extension<T>) clazz.newInstance();
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
    public void merge(FieldExtensionList<T> listEntry) {
        Iterator<Extension<T>> iterator = listEntry.array.iterator();
        while (iterator.hasNext()) {
            Extension<T> item = iterator.next();
            //TODO
            //Extension<T> copy = item.copy();
            //this.array.add(copy);
            this.array.add(item);
        }
    }

    @Override
    public FieldExtensionList<T> copy() {
        FieldExtensionList<T> clone = new FieldExtensionList<T>();
        clone.merge(this);
        return clone;
    }
}
