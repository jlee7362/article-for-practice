package org.example;

import java.util.List;

public class MemberService {
    private List<Member> memberList;

    public MemberService(List<Member> memberList) {
        this.memberList = memberList;
    }

    public boolean isJoinable(String loginId) {
        for (Member member : memberList) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    public void join(int lastMemberId, String loginId, String loginPw, String name){
        memberList.add(new Member(lastMemberId,loginId,loginPw,name));
    }
    public Member getMemberByLoginIdAndPw(String loginId, String loginPw){
        for(Member member : memberList){
            if(member.getLoginId().equals(loginId)&&member.getLoginPw().equals(loginPw)){
                return member;
            }
        }
        return null;
    }
}
