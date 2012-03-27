package com.rms.collector.data;

import java.util.List;

public interface iDAO<T> {
	public List<T> findAll();
	public boolean delete(T entity);
	public Object insert(T entity);
	public boolean update(T entity);
}
