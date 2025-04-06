package com.nckh.algoai.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nckh.algoai.dto.BaiTapQuizDTO;
import com.nckh.algoai.entity.BaiTapQuizzEntity;
import com.nckh.algoai.exception.ValidationException;
import com.nckh.algoai.repository.BaiTapQuizRepository;

@Service
public class BaiTapQuizService {

    @Autowired
    private BaiTapQuizRepository baiTapQuizRepository;

    public List<BaiTapQuizDTO> getBaiTapQuizTheoMucDo(String mucDo, Integer idBaiHoc) {
        if(!mucDo.equals("co_ban") && !mucDo.equals("trung_binh") && !mucDo.equals("nang_cao")){
            throw new ValidationException("mucDo không hợp lệ");
        }
        if(idBaiHoc == null){
            throw new ValidationException("idBaiHoc không được để trống");
        }
        if(baiTapQuizRepository.findByIdBaiHoc(idBaiHoc).isEmpty()){
            throw new ValidationException("Bài tập quiz không tồn tại");
        }
        
        List<BaiTapQuizzEntity> entities = baiTapQuizRepository.findByMucDoAndIdBaiHoc(mucDo, idBaiHoc);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BaiTapQuizDTO convertToDTO(BaiTapQuizzEntity entity) {
        BaiTapQuizDTO dto = new BaiTapQuizDTO();
        dto.setId(entity.getId());
        dto.setIdBaiHoc(entity.getIdBaiHoc());
        dto.setCauHoi(entity.getCauHoi());
        dto.setLuaChonA(entity.getLuaChonA());
        dto.setLuaChonB(entity.getLuaChonB());
        dto.setLuaChonC(entity.getLuaChonC());
        dto.setLuaChonD(entity.getLuaChonD());
        dto.setDapAnDung(entity.getDapAnDung());
        dto.setMucDo(entity.getMucDo());
        return dto;
    }
} 