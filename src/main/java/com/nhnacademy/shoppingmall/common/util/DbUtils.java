package com.nhnacademy.shoppingmall.common.util;


import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.Duration;

public class DbUtils {
    public DbUtils(){
        throw new IllegalStateException("Utility class");
    }

    private static final DataSource DATASOURCE;

    static {
        BasicDataSource basicDataSource = new BasicDataSource();

        try {
            basicDataSource.setDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //#1-1 {ip},{database},{username},{password} 설정
        basicDataSource.setUrl("jdbc:mysql://220.67.216.14:13306/nhn_academy_226");
        basicDataSource.setUsername("nhn_academy_226");
        basicDataSource.setPassword("xJ0vunyd!");

        //#1-2 initialSize, maxTotal, maxIdle, minIdle 은 모두 5로 설정합니다.
        /*
        	1.	풀은 항상 정확히 5개의 커넥션을 유지합니다.
            2.	모든 커넥션이 사용 중일 때 추가 요청은 대기해야 합니다.
            3.	사용되지 않는 커넥션은 닫히지 않고 풀에 유지됩니다.
         */
        basicDataSource.setInitialSize(5); // 커넥션 풀 시작시 생성되는 커넥션 수
        basicDataSource.setMaxTotal(5); // 동시 사용가능한 커넥션 수
        basicDataSource.setMaxIdle(5); // 커넥션 풀에 유지가능한 최대 유휴 커넥션 수
        basicDataSource.setMinIdle(5); // 항상 유지해야하는 최소 유휴 커넥션 수


        //#1-3 Validation Query를 설정하세요
        basicDataSource.setMaxWait(Duration.ofSeconds(2));
        basicDataSource.setValidationQuery("select 1");
        basicDataSource.setTestOnBorrow(true);

        //#1-4 적절히 변경하세요
        DATASOURCE = basicDataSource;

    }

    public static DataSource getDataSource(){
        return DATASOURCE;
    }

}
