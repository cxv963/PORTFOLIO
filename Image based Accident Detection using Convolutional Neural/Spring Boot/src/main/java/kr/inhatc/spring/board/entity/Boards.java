package kr.inhatc.spring.board.entity;

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

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * 1. 개요 : DB에 테이블 생성
 * 2. 처리 내용 : DB 테이블 생성
 * </pre>
 * @Entity 객체 생성
 * @Table TableName : users
 * @return
 *
 */
@Entity // 오라클 시퀀스 생성
@SequenceGenerator(
        name="USER_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="BOARD_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )
@Table(name = "board") // "board" 테이블 생성
@NoArgsConstructor
@Data
public class Boards {
	
	@Id
	@Column(name = "Board_Idx")
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="USER_SEQ_GEN"
			)
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String createId;
	
	// DB타입에 맞도록 매핑.
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date createDate;
	
}
