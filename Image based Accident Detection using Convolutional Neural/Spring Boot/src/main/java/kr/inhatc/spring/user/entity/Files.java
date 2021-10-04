package kr.inhatc.spring.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@SequenceGenerator(
        name="FILE_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="FILES_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )
@Table(name = "files")
public class Files {
	@Id
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator="FILE_SEQ_GEN"
			)
	int fno;
	String id;
	String filename;
	String fileOriName;
	String fileurl;
}