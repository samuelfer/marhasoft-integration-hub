package br.com.marhasoft.integrationhub.core.model;

import java.util.List;

public interface BatchRequest<T> {

    List<T> getElementos();

}