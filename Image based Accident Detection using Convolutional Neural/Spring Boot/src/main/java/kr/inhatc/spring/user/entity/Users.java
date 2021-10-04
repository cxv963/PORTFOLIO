package kr.inhatc.spring.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 												//db로 연동가능
@Table(name = "users")					// 테이블 이름과 다르게 주고싶으면 설정.
@NoArgsConstructor 						//디폴트 생성자
@AllArgsConstructor							//전체 컬럼 생성자
@Data
@Builder												//이거 없으면 직접 만들어야함.
public class Users {

	@Id													//엔티티랑 연동해서 없으면 안돌아감.
	@Column(name = "USER_ID")		//컬럼의 이름을 다른걸로 주고 싶을 때
	private String id;
	private String pw;
	
	@Column(length = 20)
	private String name;
	private String email;
	
	//날짜 처리
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")			 //columnDefinition = 기본값
	private Date joinDate;
	private String enabled;
	private String role;
	private String filename;
	
    public void PostsEntity(String id,String pw,String name,String email,Date joinDate,String enabled,String role,String filename) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.joinDate = joinDate;
        this.enabled = enabled;
        this.role = role;
        this.filename=filename;
    }
}
