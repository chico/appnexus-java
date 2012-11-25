package appnexus.api;


public class MemberResponse extends Response {

  private static final long serialVersionUID = 1L;

  private Member member;

  public Member getMember() {
    return member;
  }

  public void setMember(Member member) {
    this.member = member;
  }
  
}
