/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gman.modeler.gui.api;

import javax.swing.*;
import java.lang.reflect.ParameterizedType;

/**
 * @author gman
 */
public abstract class DataCreator<T> extends JPanel {

    public String getCreatorName() {
        return createClass().getSimpleName();
    }

    public Class createClass() {
        return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void reset() {
        //default empty impl for non options components
    }

    public void parse(T obj) {
        //default empty impl for non options components
    }

    public abstract T create();
}
