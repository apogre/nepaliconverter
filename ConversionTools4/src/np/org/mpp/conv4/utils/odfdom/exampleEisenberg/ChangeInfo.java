package np.org.mpp.conv4.utils.odfdom.exampleEisenberg;

public class ChangeInfo
{
  private String type;
  private String author;
  private String timeStamp;
  private String changeContent;

  ChangeInfo( String what, String who, String when, String theChange )
  {
    type = what;
    author = who;
    timeStamp = when;
    changeContent = theChange;
  }

  void setType( String type )
  {
    this.type = type;
  }

  String getType()
  {
    return type;
  }

  void setAuthor( String author )
  {
    this.author = author;
  }

  String getAuthor()
  {
    return author;
  }

  void setTimeStamp( String timeStamp )
  {
    this.timeStamp = timeStamp;
  }

  String getTimeStamp()
  {
    return timeStamp;
  }

  void setChangeContent( String changeContent )
  {
    this.changeContent = changeContent;
  }

  String getChangeContent()
  {
    return changeContent;
  }
}
