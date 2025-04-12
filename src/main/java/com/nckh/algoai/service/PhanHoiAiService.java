package com.nckh.algoai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nckh.algoai.entity.PhanHoiAiEntity;
import com.nckh.algoai.entity.NopBaiEntity;
import com.nckh.algoai.repository.PhanHoiAiRepository;
import com.nckh.algoai.repository.NopBaiRepository;
import com.nckh.algoai.dto.PhanHoiAiDTO;
import com.nckh.algoai.exception.ValidationException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PhanHoiAiService {

    @Autowired
    private PhanHoiAiRepository phanHoiAiRepository;

    @Autowired
    private NopBaiRepository nopBaiRepository;

    public PhanHoiAiEntity luuPhanHoiAi(PhanHoiAiDTO phanHoiAiDTO) {
        // Kiểm tra nộp bài có tồn tại không
        NopBaiEntity nopBai = nopBaiRepository.findById(phanHoiAiDTO.getIdNopBai())
                .orElseThrow(() -> new ValidationException("Không tìm thấy bài nộp"));

        // Cập nhật điểm cho bài nộp
        nopBai.setDiem(phanHoiAiDTO.getDiem());
        nopBaiRepository.save(nopBai);

        // Lưu phản hồi AI
        PhanHoiAiEntity phanHoiAi = new PhanHoiAiEntity();
        phanHoiAi.setIdNopBai(phanHoiAiDTO.getIdNopBai());
        phanHoiAi.setNoiDung(phanHoiAiDTO.getNoiDung());
        phanHoiAi.setGoiYCaiThien(phanHoiAiDTO.getGoiYCaiThien());

        return phanHoiAiRepository.save(phanHoiAi);
    }

    public PhanHoiAiEntity getPhanHoiAi(Integer idNopBai) {
        // Kiểm tra nộp bài có tồn tại không
        NopBaiEntity nopBai = nopBaiRepository.findById(idNopBai)
                .orElseThrow(() -> new ValidationException("Không tìm thấy bài nộp"));

        // Lấy phản hồi AI
        return phanHoiAiRepository.findByIdNopBai(idNopBai)
                .orElseThrow(() -> new ValidationException("Không tìm thấy phản hồi AI"));
    }

    public PhanHoiAiEntity suaPhanHoiAi(Integer idNopBai, PhanHoiAiDTO phanHoiAiDTO) {
        // Kiểm tra nộp bài có tồn tại không
        NopBaiEntity nopBai = nopBaiRepository.findById(idNopBai)
                .orElseThrow(() -> new ValidationException("Không tìm thấy bài nộp"));

        // Kiểm tra phản hồi AI có tồn tại không
        PhanHoiAiEntity phanHoiAi = phanHoiAiRepository.findByIdNopBai(idNopBai)
                .orElseThrow(() -> new ValidationException("Không tìm thấy phản hồi AI"));

        // Cập nhật điểm cho bài nộp
        nopBai.setDiem(phanHoiAiDTO.getDiem());
        nopBaiRepository.save(nopBai);

        // Cập nhật phản hồi AI
        phanHoiAi.setNoiDung(phanHoiAiDTO.getNoiDung());
        phanHoiAi.setGoiYCaiThien(phanHoiAiDTO.getGoiYCaiThien());

        return phanHoiAiRepository.save(phanHoiAi);
    }
} 