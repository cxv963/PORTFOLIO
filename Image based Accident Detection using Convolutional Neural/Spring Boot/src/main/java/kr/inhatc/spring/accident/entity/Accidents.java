package kr.inhatc.spring.accident.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 												//db로 연동가능
@Table(name = "accident")					// 테이블 이름과 다르게 주고싶으면 설정.
@NoArgsConstructor 						//디폴트 생성자
@AllArgsConstructor							//전체 컬럼 생성자
@Data
@Builder												//이거 없으면 직접 만들어야함.
public class Accidents {
	
	@Id													//엔티티랑 연동해서 없으면 안돌아감.
	@Column(name = "ACCIDENTS_ID")		//컬럼의 이름을 다른걸로 주고 싶을 때
	private String id;
	
	@Column(length = 20)
	private String title;
	
	//날짜 처리
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")			 //columnDefinition = 기본값
	private Date joinDate;
	private String filename;
	
    public void PostsEntity(String id,String title, Date joinDate, String filename) {
        this.id = id;
        this.title = title;
        this.joinDate = joinDate;
        this.filename=filename;
    }
}
