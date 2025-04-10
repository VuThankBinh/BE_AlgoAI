package com.nckh.algoai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nckh.algoai.entity.NopBaiEntity;
import com.nckh.algoai.repository.NopBaiRepository;
import com.nckh.algoai.repository.BaiHocRepository;
import com.nckh.algoai.repository.UserRepository;
import com.nckh.algoai.dto.NopBaiTapCodeDTO;
import com.nckh.algoai.dto.NopBaiTapQuizDTO;
import com.nckh.algoai.exception.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import com.nckh.algoai.entity.BaiTapCodeEntity;
import com.nckh.algoai.repository.BaiTapCodeRepository;
import com.nckh.algoai.dto.BaiTapCodeNopBaiDTO;
import com.nckh.algoai.entity.BaiTapQuizzEntity;
import com.nckh.algoai.repository.BaiTapQuizRepository;
import com.nckh.algoai.dto.BaiTapQuizNopBaiDTO;

@Service
public class NopBaiService {

    @Autowired
    private NopBaiRepository nopBaiRepository;
        

    @Autowired
    private BaiHocRepository baiHocRepository;
    @Autowired
    private UserRepository userRepository;

    
    public NopBaiEntity luuBaiTapQuiz(NopBaiTapQuizDTO nopBaiTapQuizDTO) {
        // Kiểm tra người dùng tồn tại
        if (userRepository.findById(nopBaiTapQuizDTO.getIdNguoiDung()).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        if(baiHocRepository.findById(nopBaiTapQuizDTO.getIdBaiHoc()).isEmpty()){
            throw new ValidationException("Bài học không tồn tại");
        }

        if(nopBaiTapQuizDTO.getDiem() == null){
            throw new ValidationException("Điểm không được để trống");
        }
        if(nopBaiTapQuizDTO.getDapAn() == null){
            throw new ValidationException("Đáp án không được để trống");
        }
        if(nopBaiTapQuizDTO.getMucDo() == null){
            throw new ValidationException("Mức độ không được để trống");
        }
        // Tạo đối tượng nộp bài
        NopBaiEntity nopBai = new NopBaiEntity();
        nopBai.setIdNguoiDung(nopBaiTapQuizDTO.getIdNguoiDung());
        nopBai.setIdBaiHoc(nopBaiTapQuizDTO.getIdBaiHoc());
        nopBai.setLoaiBaiTap("quiz");
        nopBai.setMucDo(nopBaiTapQuizDTO.getMucDo());
        nopBai.setDapAn(nopBaiTapQuizDTO.getDapAn());
        nopBai.setDiem(nopBaiTapQuizDTO.getDiem());
        nopBai.setNgayNop(LocalDateTime.now());
        
        // Lưu bài nộp
        NopBaiEntity savedNopBai = nopBaiRepository.save(nopBai);
        
        
        return savedNopBai;
    }
    public NopBaiEntity luuBaiTapCode(NopBaiTapCodeDTO nopBaiTapCodeDTO) {
        // Kiểm tra người dùng tồn tại
        if (userRepository.findById(nopBaiTapCodeDTO.getIdNguoiDung()).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        // Kiểm tra bài tập code tồn tại
        if(baiHocRepository.findById(nopBaiTapCodeDTO.getIdBaiHoc()).isEmpty()){
            throw new ValidationException("Bài học không tồn tại");
        }
        if(nopBaiTapCodeDTO.getDapAn() == null){
            throw new ValidationException("Đáp án không được để trống");
        }
        if(nopBaiTapCodeDTO.getMucDo() == null){
            throw new ValidationException("Mức độ không được để trống");
        }
        // Tạo đối tượng nộp bài
        NopBaiEntity nopBai = new NopBaiEntity();
        nopBai.setIdNguoiDung(nopBaiTapCodeDTO.getIdNguoiDung());
        nopBai.setIdBaiHoc(nopBaiTapCodeDTO.getIdBaiHoc());
        nopBai.setLoaiBaiTap("code");
        nopBai.setMucDo(nopBaiTapCodeDTO.getMucDo());
        nopBai.setDapAn(nopBaiTapCodeDTO.getDapAn());
        nopBai.setDiem(0); // Điểm sẽ được cập nhật sau khi chấm điểm
        nopBai.setNgayNop(LocalDateTime.now());
        
        // Lưu bài nộp
        NopBaiEntity savedNopBai = nopBaiRepository.save(nopBai);
        
        
        
        return savedNopBai;
    }
    
    /**
     * Lấy danh sách bài nộp của người dùng
     * @param idNguoiDung ID người dùng
     * @param loaiBaiTap Loại bài tập (quiz hoặc code)
     * @return Danh sách bài nộp
     */
    public List<NopBaiEntity> getDanhSachBaiNop(Integer idNguoiDung, String loaiBaiTap) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (loaiBaiTap != null && !loaiBaiTap.equals("quiz") && !loaiBaiTap.equals("code")) {
            throw new ValidationException("Loại bài tập không hợp lệ");
        }
        
        if (loaiBaiTap == null) {
            return nopBaiRepository.findByIdNguoiDungAndLoaiBaiTap(idNguoiDung, "quiz");
        } else {
            return nopBaiRepository.findByIdNguoiDungAndLoaiBaiTap(idNguoiDung, loaiBaiTap);
        }
    }
    
    /**
     * Lấy đáp án và đề bài đã lưu của người dùng
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @return Danh sách bài nộp
     */
    public List<NopBaiEntity> getDapAnVaDeBaiDaSave(Integer idNguoiDung, Integer idBaiHoc) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        return nopBaiRepository.findByIdNguoiDungAndIdBaiHoc(idNguoiDung, idBaiHoc);
    }
    
    /**
     * Lấy danh sách bài tập code đã nộp của người dùng
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @return Danh sách bài nộp code
     */
    public List<NopBaiEntity> getBaiTapCodeDaNop(Integer idNguoiDung, Integer idBaiHoc) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        List<NopBaiEntity> danhSachBaiNop = nopBaiRepository.findByIdNguoiDungAndIdBaiHoc(idNguoiDung, idBaiHoc);
        return danhSachBaiNop.stream()
                .filter(baiNop -> "code".equals(baiNop.getLoaiBaiTap()))
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Lấy danh sách bài tập quiz đã nộp của người dùng
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @return Danh sách bài nộp quiz
     */
    public List<NopBaiEntity> getBaiTapQuizDaNop(Integer idNguoiDung, Integer idBaiHoc) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        List<NopBaiEntity> danhSachBaiNop = nopBaiRepository.findByIdNguoiDungAndIdBaiHoc(idNguoiDung, idBaiHoc);
        return danhSachBaiNop.stream()
                .filter(baiNop -> "quizz".equals(baiNop.getLoaiBaiTap()))
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Lấy danh sách bài tập code đã nộp của người dùng theo mức độ
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @param mucDo Mức độ bài tập (co_ban, trung_binh, nang_cao)
     * @return Danh sách bài nộp code theo mức độ
     */
    public List<NopBaiEntity> getBaiTapCodeTheoMucDo(Integer idNguoiDung, Integer idBaiHoc, String mucDo) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (mucDo == null) {
            throw new ValidationException("Mức độ không được để trống");
        }
        
        if (!mucDo.equals("co_ban") && !mucDo.equals("trung_binh") && !mucDo.equals("nang_cao")) {
            throw new ValidationException("Mức độ không hợp lệ");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        List<NopBaiEntity> danhSachBaiNop = nopBaiRepository.findByIdNguoiDungAndIdBaiHoc(idNguoiDung, idBaiHoc);
        return danhSachBaiNop.stream()
                .filter(baiNop -> "code".equals(baiNop.getLoaiBaiTap()) && mucDo.equals(baiNop.getMucDo()))
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Lấy danh sách bài tập quiz đã nộp của người dùng theo mức độ
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @param mucDo Mức độ bài tập (co_ban, trung_binh, nang_cao)
     * @return Danh sách bài nộp quiz theo mức độ
     */
    public List<NopBaiEntity> getBaiTapQuizTheoMucDo(Integer idNguoiDung, Integer idBaiHoc, String mucDo) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (mucDo == null) {
            throw new ValidationException("Mức độ không được để trống");
        }
        
        if (!mucDo.equals("co_ban") && !mucDo.equals("trung_binh") && !mucDo.equals("nang_cao")) {
            throw new ValidationException("Mức độ không hợp lệ");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        List<NopBaiEntity> danhSachBaiNop = nopBaiRepository.findByIdNguoiDungAndIdBaiHoc(idNguoiDung, idBaiHoc);
        return danhSachBaiNop.stream()
                .filter(baiNop -> "quizz".equals(baiNop.getLoaiBaiTap()) && mucDo.equals(baiNop.getMucDo()))
                .collect(java.util.stream.Collectors.toList());
    }
    
    @Autowired
    private BaiTapCodeRepository baiTapCodeRepository;
    
    /**
     * Lấy danh sách bài tập code đã nộp của người dùng theo mức độ, bao gồm cả đề bài
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @param mucDo Mức độ bài tập (co_ban, trung_binh, nang_cao)
     * @return Danh sách bài nộp code theo mức độ, bao gồm cả đề bài
     */
    public List<BaiTapCodeNopBaiDTO> getBaiTapCodeTheoMucDoCoDeBai(Integer idNguoiDung, Integer idBaiHoc, String mucDo) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (mucDo == null) {
            throw new ValidationException("Mức độ không được để trống");
        }
        
        if (!mucDo.equals("co_ban") && !mucDo.equals("trung_binh") && !mucDo.equals("nang_cao")) {
            throw new ValidationException("Mức độ không hợp lệ");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        // Lấy danh sách bài nộp
        List<NopBaiEntity> danhSachBaiNop = nopBaiRepository.findByIdNguoiDungAndIdBaiHoc(idNguoiDung, idBaiHoc);
        List<NopBaiEntity> danhSachBaiNopCode = danhSachBaiNop.stream()
                .filter(baiNop -> "code".equals(baiNop.getLoaiBaiTap()) && mucDo.equals(baiNop.getMucDo()))
                .collect(java.util.stream.Collectors.toList());
        
        // Lấy danh sách bài tập code
        List<BaiTapCodeEntity> danhSachBaiTapCode = baiTapCodeRepository.findByIdBaiHocAndMucDo(idBaiHoc, mucDo);
        
        // Kết hợp thông tin
        List<BaiTapCodeNopBaiDTO> ketQua = new java.util.ArrayList<>();
        
        for (NopBaiEntity baiNop : danhSachBaiNopCode) {
            for (BaiTapCodeEntity baiTapCode : danhSachBaiTapCode) {
                BaiTapCodeNopBaiDTO dto = new BaiTapCodeNopBaiDTO();
                dto.setId(baiTapCode.getId());
                dto.setIdBaiHoc(baiTapCode.getIdBaiHoc());
                dto.setDeBai(baiTapCode.getDeBai());
                dto.setDauVaoMau(baiTapCode.getDauVaoMau());
                dto.setDauRaMau(baiTapCode.getDauRaMau());
                dto.setMucDo(baiTapCode.getMucDo());
                dto.setDapAnNguoiDung(baiNop.getDapAn());
                dto.setDiem(baiNop.getDiem());
                dto.setNgayNop(baiNop.getNgayNop());
                ketQua.add(dto);
            }
        }
        
        return ketQua;
    }
    
    @Autowired
    private BaiTapQuizRepository baiTapQuizRepository;
    
    /**
     * Lấy danh sách bài tập quiz đã nộp của người dùng theo mức độ, bao gồm cả đề bài
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @param mucDo Mức độ bài tập (co_ban, trung_binh, nang_cao)
     * @return Danh sách bài nộp quiz theo mức độ, bao gồm cả đề bài
     */
    public List<BaiTapQuizNopBaiDTO> getBaiTapQuizTheoMucDoCoDeBai(Integer idNguoiDung, Integer idBaiHoc, String mucDo) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (mucDo == null) {
            throw new ValidationException("Mức độ không được để trống");
        }
        
        if (!mucDo.equals("co_ban") && !mucDo.equals("trung_binh") && !mucDo.equals("nang_cao")) {
            throw new ValidationException("Mức độ không hợp lệ");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        // Lấy danh sách bài nộp
        List<NopBaiEntity> danhSachBaiNop = nopBaiRepository.findByIdNguoiDungAndIdBaiHoc(idNguoiDung, idBaiHoc);
        List<NopBaiEntity> danhSachBaiNopQuiz = danhSachBaiNop.stream()
                .filter(baiNop -> "quiz".equals(baiNop.getLoaiBaiTap()) && mucDo.equals(baiNop.getMucDo()))
                .collect(java.util.stream.Collectors.toList());
        
        // Lấy danh sách bài tập quiz
        List<BaiTapQuizzEntity> danhSachBaiTapQuiz = baiTapQuizRepository.findByMucDoAndIdBaiHoc(mucDo, idBaiHoc);
        
        // Kết hợp thông tin
        List<BaiTapQuizNopBaiDTO> ketQua = new java.util.ArrayList<>();
        
        for (NopBaiEntity baiNop : danhSachBaiNopQuiz) {
            for (BaiTapQuizzEntity baiTapQuiz : danhSachBaiTapQuiz) {
                BaiTapQuizNopBaiDTO dto = new BaiTapQuizNopBaiDTO();
                dto.setId(baiTapQuiz.getId());
                dto.setIdBaiHoc(baiTapQuiz.getIdBaiHoc());
                dto.setCauHoi(baiTapQuiz.getCauHoi());
                dto.setLuaChonA(baiTapQuiz.getLuaChonA());
                dto.setLuaChonB(baiTapQuiz.getLuaChonB());
                dto.setLuaChonC(baiTapQuiz.getLuaChonC());
                dto.setLuaChonD(baiTapQuiz.getLuaChonD());
                dto.setDapAnDung(baiTapQuiz.getDapAnDung());
                dto.setMucDo(baiTapQuiz.getMucDo());
                dto.setDapAnNguoiDung(baiNop.getDapAn());
                dto.setDiem(baiNop.getDiem());
                dto.setNgayNop(baiNop.getNgayNop());
                ketQua.add(dto);
            }
        }
        
        return ketQua;
    }

    /**
     * Sửa bài nộp đã có
     * @param id ID bài nộp cần sửa
     * @param nopBaiTapCodeDTO DTO chứa thông tin bài nộp mới
     * @return Bài nộp đã được sửa
     */
    public NopBaiEntity suaBaiNopCode(Integer idNguoiDung, Integer idBaiHoc, NopBaiTapCodeDTO nopBaiTapCodeDTO) {
        // Tìm bài nộp theo idNguoiDung và idBaiHoc
        List<NopBaiEntity> baiNops = nopBaiRepository.findByIdNguoiDungAndIdBaiHocAndLoaiBaiTap(
            idNguoiDung, idBaiHoc, "code");
        
        if (baiNops.isEmpty()) {
            throw new ValidationException("Không tìm thấy bài nộp");
        }

        // Lấy bài nộp đầu tiên
        NopBaiEntity baiNop = baiNops.get(0);

        // Chỉ cập nhật đáp án và thời gian nộp
        baiNop.setDapAn(nopBaiTapCodeDTO.getDapAn());
        baiNop.setNgayNop(LocalDateTime.now());

        return nopBaiRepository.save(baiNop);
    }

    /**
     * Sửa bài nộp quiz đã có
     * @param id ID bài nộp cần sửa
     * @param nopBaiTapQuizDTO DTO chứa thông tin bài nộp mới
     * @return Bài nộp đã được sửa
     */
    public NopBaiEntity suaBaiNopQuiz(Integer idNguoiDung, Integer idBaiHoc, NopBaiTapQuizDTO nopBaiTapQuizDTO) {
        // Tìm bài nộp theo idNguoiDung và idBaiHoc
        List<NopBaiEntity> baiNops = nopBaiRepository.findByIdNguoiDungAndIdBaiHocAndLoaiBaiTap(
            idNguoiDung, idBaiHoc, "quiz");
        
        if (baiNops.isEmpty()) {
            throw new ValidationException("Không tìm thấy bài nộp");
        }

        // Lấy bài nộp đầu tiên
        NopBaiEntity baiNop = baiNops.get(0);

        // Chỉ cập nhật đáp án và thời gian nộp
        baiNop.setDapAn(nopBaiTapQuizDTO.getDapAn());
        baiNop.setNgayNop(LocalDateTime.now());

        return nopBaiRepository.save(baiNop);
    }

    /**
     * Kiểm tra người dùng đã làm quiz chưa
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @return true nếu đã làm quiz, false nếu chưa
     */
    public boolean kiemTraDaLamQuiz(Integer idNguoiDung, Integer idBaiHoc) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        List<NopBaiEntity> baiNops = nopBaiRepository.findByIdNguoiDungAndIdBaiHocAndLoaiBaiTap(
            idNguoiDung, idBaiHoc, "quiz");
        
        return !baiNops.isEmpty();
    }

    /**
     * Kiểm tra người dùng đã làm code chưa
     * @param idNguoiDung ID người dùng
     * @param idBaiHoc ID bài học
     * @return true nếu đã làm code, false nếu chưa
     */
    public boolean kiemTraDaLamCode(Integer idNguoiDung, Integer idBaiHoc) {
        if (idNguoiDung == null) {
            throw new ValidationException("ID người dùng không được để trống");
        }
        
        if (idBaiHoc == null) {
            throw new ValidationException("ID bài học không được để trống");
        }
        
        if (userRepository.findById(idNguoiDung).isEmpty()) {
            throw new ValidationException("Người dùng không tồn tại");
        }
        
        if (baiHocRepository.findById(idBaiHoc).isEmpty()) {
            throw new ValidationException("Bài học không tồn tại");
        }
        
        List<NopBaiEntity> baiNops = nopBaiRepository.findByIdNguoiDungAndIdBaiHocAndLoaiBaiTap(
            idNguoiDung, idBaiHoc, "code");
        
        return !baiNops.isEmpty();
    }
} 