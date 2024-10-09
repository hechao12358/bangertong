package com.wode.bangertong.service;

import com.wode.bangertong.common.entity.AudioDefault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AudioDefaultService {

    List<AudioDefault> getAudioDefaultAll();

    String getDefaultAudioIdsByType(String type);
}
