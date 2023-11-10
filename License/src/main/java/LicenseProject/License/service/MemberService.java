package LicenseProject.License.service;


import LicenseProject.License.dto.MemberDTO;
import LicenseProject.License.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepositoy;
    public void save(MemberDTO memberDTO) {


    }
}
