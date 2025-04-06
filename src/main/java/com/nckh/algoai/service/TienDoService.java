package com.nckh.algoai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nckh.algoai.entity.TienDoEntity;
import com.nckh.algoai.exception.ValidationException;
import com.nckh.algoai.entity.BaiHocEntity;
import com.nckh.algoai.repository.TienDoRepository;
import com.nckh.algoai.repository.BaiHocRepository;
import com.nckh.algoai.repository.UserRepository;
import com.nckh.algoai.dto.BaiHocTienDoDTO;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class TienDoService {

    @Autowired
    private TienDoRepository tienDoRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BaiHocRepository baiHocRepository;

    public TienDoEntity capNhatTienDo(Integer idNguoiDung, Integer idBaiHoc) {
        Optional<TienDoEntity> tienDoOpt = tienDoRepository.findByIdNguoiDungAndIdBaiHoc(idNguoiDung, idBaiHoc);
        
        if (tienDoOpt.isEmpty()) {
            // Nếu chưa có tiến độ, tạo mới với trạng thái "chua_hoc"
            TienDoEntity tienDo = new TienDoEntity();
            tienDo.setIdNguoiDung(idNguoiDung);
            tienDo.setIdBaiHoc(idBaiHoc);
            tienDo.setTrangThai("dang_hoc");
            tienDo.setLanCuoiTruyCap(LocalDateTime.now());
            return tienDoRepository.save(tienDo);
        } else {
            // Nếu đã có tiến độ, cập nhật trạng thái
            TienDoEntity tienDo = tienDoOpt.get();
            String trangThaiHienTai = tienDo.getTrangThai();
            
            // Cập nhật trạng thái theo quy tắc
            if ("chua_hoc".equals(trangThaiHienTai)) {
                tienDo.setTrangThai("dang_hoc");
            } else if ("dang_hoc".equals(trangThaiHienTai)) {
                tienDo.setTrangThai("da_hoc");
            }
            
            // Cập nhật thời gian truy cập
            tienDo.setLanCuoiTruyCap(LocalDateTime.now());
            return tienDoRepository.save(tienDo);
        }
    }
    
    public List<BaiHocTienDoDTO> getDanhSachBaiHocTheoTrangThai(Integer idNguoiDung, Integer idKhoaHoc, String trangThai) {
        if(idNguoiDung == null){
            throw new ValidationException("idNguoiDung không được để trống");
        }
        if(idKhoaHoc == null){
            throw new ValidationException("idKhoaHoc không được để trống");
        }
        if(idKhoaHoc<12521068 || idKhoaHoc>12521070){
            throw new ValidationException("idKhoaHoc không hợp lệ");
        }
        if(!trangThai.equals("chua_hoc") && !trangThai.equals("dang_hoc") && !trangThai.equals("da_hoc")){
            throw new ValidationException("trangThai không hợp lệ");
        }
        if(baiHocRepository.findByIdKhoaHoc(idKhoaHoc).isEmpty()){
            throw new ValidationException("khóa học không tồn tại");
        }
        if(tienDoRepository.findByIdNguoiDungAndIdKhoaHoc(idNguoiDung, idKhoaHoc).isEmpty()){
            
        }
        if(userRepository.findById(idNguoiDung).isEmpty()){
            throw new ValidationException("người dùng không tồn tại");
        }
        // Lấy tất cả bài học của khóa học
        List<BaiHocEntity> danhSachBaiHoc = baiHocRepository.findByIdKhoaHoc(idKhoaHoc);
        
        // Lấy danh sách tiến độ của người dùng cho khóa học này
        List<TienDoEntity> danhSachTienDo = tienDoRepository.findByIdNguoiDungAndIdKhoaHoc(idNguoiDung, idKhoaHoc);
        
        // Tạo map để dễ dàng tìm kiếm tiến độ theo id bài học
        java.util.Map<Integer, TienDoEntity> tienDoMap = danhSachTienDo.stream()
                .collect(Collectors.toMap(TienDoEntity::getIdBaiHoc, tienDo -> tienDo));
        
        List<BaiHocTienDoDTO> ketQua = new ArrayList<>();
        
        for (BaiHocEntity baiHoc : danhSachBaiHoc) {
            BaiHocTienDoDTO baiHocTienDoDTO = new BaiHocTienDoDTO();
            
            // Copy thông tin bài học
            baiHocTienDoDTO.setId(baiHoc.getId());
            baiHocTienDoDTO.setIdKhoaHoc(baiHoc.getIdKhoaHoc());
            baiHocTienDoDTO.setTieuDe(baiHoc.getTieuDe());
            baiHocTienDoDTO.setNoiDung(baiHoc.getNoiDung());
            baiHocTienDoDTO.setMucDo(baiHoc.getMucDo());
            baiHocTienDoDTO.setLinkYoutube(baiHoc.getLinkYoutube());
            baiHocTienDoDTO.setLinkMoTa(baiHoc.getLinkMoTa());
            
            // Kiểm tra tiến độ
            TienDoEntity tienDo = tienDoMap.get(baiHoc.getId());
            if (tienDo != null) {
                baiHocTienDoDTO.setTrangThai(tienDo.getTrangThai());
                baiHocTienDoDTO.setLanCuoiTruyCap(tienDo.getLanCuoiTruyCap());
            } else {
                // Nếu chưa có tiến độ, mặc định là "chua_hoc"
                baiHocTienDoDTO.setTrangThai("chua_hoc");
                baiHocTienDoDTO.setLanCuoiTruyCap(null);
            }
            
            // Chỉ thêm vào kết quả nếu trạng thái phù hợp
            if (trangThai == null || trangThai.equals(baiHocTienDoDTO.getTrangThai())) {
                ketQua.add(baiHocTienDoDTO);
            }
        }
        
        return ketQua;
    }
} 