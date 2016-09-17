package com.prima.pricer.model;

import com.prima.configuration.dom.Root;

public class ObjectToProcessing {
    private String pathToExcel;
    private String pathToConfigXML;
    private Root root;

    public String getPathToExcel() {
        return pathToExcel;
    }

    public void setPathToExcel(String pathToExcel) {
        this.pathToExcel = pathToExcel;
    }

    public String getPathToConfigXML() {
        return pathToConfigXML;
    }

    public void setPathToConfigXML(String pathToConfigXML) {
        this.pathToConfigXML = pathToConfigXML;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}