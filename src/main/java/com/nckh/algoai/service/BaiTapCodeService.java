package com.nckh.algoai.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nckh.algoai.dto.BaiTapCodeDTO;
import com.nckh.algoai.entity.BaiTapCodeEntity;
import com.nckh.algoai.exception.ValidationException;
import com.nckh.algoai.repository.BaiTapCodeRepository;

@Service
public class BaiTapCodeService {

    @Autowired
    private BaiTapCodeRepository baiTapCodeRepository;

    public List<BaiTapCodeDTO> getBaiTapCodeTheoMucDo(String mucDo, Integer idBaiHoc) {
        if(idBaiHoc == null){
            throw new ValidationException("idBaiHoc không được để trống");
        }
        if(baiTapCodeRepository.findByIdBaiHoc(idBaiHoc).isEmpty()){
            throw new ValidationException("Bài tập code không tồn tại");
        }
        if(!mucDo.equals("co_ban") && !mucDo.equals("trung_binh") && !mucDo.equals("nang_cao")){
            throw new ValidationException("mucDo không hợp lệ");
        }
        List<BaiTapCodeEntity> entities = baiTapCodeRepository.findByIdBaiHocAndMucDo(idBaiHoc, mucDo);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BaiTapCodeDTO convertToDTO(BaiTapCodeEntity entity) {
        BaiTapCodeDTO dto = new BaiTapCodeDTO();
        dto.setId(entity.getId());
        dto.setIdBaiHoc(entity.getIdBaiHoc());
        dto.setDeBai(entity.getDeBai());
        dto.setDauVaoMau(entity.getDauVaoMau());
        dto.setDauRaMau(entity.getDauRaMau());
        dto.setMucDo(entity.getMucDo());
        return dto;
    }
} 