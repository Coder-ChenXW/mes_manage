package org.chenxw.authentication.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp; // 修改这里

/**
 * @Author: ChenXW
 * @Date:2024/3/1 21:46
 * @Description: 登录日志
 **/
@Entity
@Table(name = "log")
@Data
@EqualsAndHashCode(callSuper = false)
public class Log implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "log_date")
    private Timestamp logDate;

    @Column(name = "log_info")
    private String logInfo;

}

