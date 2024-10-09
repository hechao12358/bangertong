package com.wode.bangertong.common.dto;

import com.wode.bangertong.common.entity.Audio;
import lombok.Data;

import java.util.List;

@Data
public class AudioDTO {

    private String userId;

    private List<Audio> audios;

}
