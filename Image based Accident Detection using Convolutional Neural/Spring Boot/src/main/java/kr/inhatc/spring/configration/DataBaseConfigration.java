package kr.inhatc.spring.configration;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration //설정파일 객체?
@PropertySource("classpath:/application.properties") //application.properties에 있는 정보 전달
public class DataBaseConfigration {
	
	@Autowired //자동으로 메모리에 올라감
	private ApplicationContext appContext;
	
	
	//HikariConfig 설정
	@Bean//객체 올리기
	@ConfigurationProperties(prefix = "spring.datasource.hikari")//접두어 사용
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	@Bean
	public DataSource dataSource() throws Exception{ //DB와 관련된 정보를 땡겨서 데이터 소스를 만들고 DB와 연결
		DataSource dataSource = new HikariDataSource(hikariConfig());
		System.out.println("===========>" + dataSource.toString());
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		
//		//매퍼 연결
//		sqlSessionFactoryBean.setMapperLocations(
//				appContext.getResources("classpath:/mapper/**/sql-*.xml")//**은 알아서 찾음.
//			);
		
		//마이바티스에 대한 설정 추가
		sqlSessionFactoryBean.setConfiguration(myBatisConfig());
		
		return sqlSessionFactoryBean.getObject();//SqlSessionFactory형태가 아니기 때문에 getObject()를 붙여줌.
	}
	
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")//접두어 사용
	public org.apache.ibatis.session.Configuration myBatisConfig(){
		return new org.apache.ibatis.session.Configuration();
	}
	
	//SQL을 마이바티스에서 쉽게 쓰기 위해서
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {//테스트용 쿼리를 날림.
		return new SqlSessionTemplate(sqlSessionFactory);
		
	}
	
	// JPA 설정
	// JPA 설정 빈 등록
	@Bean
	@ConfigurationProperties(prefix = "spring.jpa")
	public Properties hibernateConfig() {
		return new Properties();
	}
}
