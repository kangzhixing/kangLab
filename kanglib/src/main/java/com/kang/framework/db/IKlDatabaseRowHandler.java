package com.kang.framework.db;

import java.sql.*;

public interface IKlDatabaseRowHandler {

	void map(ResultSet resultSet) throws Exception;
}
