package LicenseProject.License.service;


import LicenseProject.License.dto.MemberDTO;
import LicenseProject.License.entity.MemberEntity;
import LicenseProject.License.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepositoy;
    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환
        // 2. repositroy의 save 메소드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepositoy.save(memberEntity);


    }

    public MemberDTO login(MemberDTO memberDTO) {
    // 1. 회원이 입력한 이메일로 DB에서 조회릏하
        // 2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        Optional<MemberEntity> byMemberEmail =
                memberRepositoy.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()){
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // 비밀번호 일치 하는 경우
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }else {
                // 비밀번호가 불일치 하는 경우
                return null;
            }
        }else {
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }
}
