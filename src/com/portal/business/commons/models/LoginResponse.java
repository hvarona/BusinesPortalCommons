package com.portal.business.commons.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="response")
public class LoginResponse
{

  @ElementList(name="errors", required=false)
  private List<String> errors;

  @Element(name="login", required=false)
  private String login;
  
  @Element(name="fullName", required=false)
  private String fullName;

  @ElementList(name="messages", required=false)
  private List<String> messages;

  @Element(name="userId", required=false)
  private String userId;

  public LoginResponse()
  {
    ArrayList localArrayList1 = new ArrayList();
    this.errors = localArrayList1;
    ArrayList localArrayList2 = new ArrayList();
    this.messages = localArrayList2;
  }

  public void addError(String paramString)
  {
    boolean bool = this.errors.add(paramString);
  }

  public void addMessage(String paramString)
  {
    boolean bool = this.messages.add(paramString);
  }

  public List<String> getErrors()
  {
    return this.errors;
  }

  public String getErrorsAsString()
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    Iterator localIterator = this.errors.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuffer1.toString();
      String str = (String)localIterator.next();
      StringBuffer localStringBuffer2 = localStringBuffer1.append(str);
    }
  }

  public String getLogin()
  {
    return this.login;
  }

  public List<String> getMessages()
  {
    return this.messages;
  }

  public String getMessagesAsString()
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    Iterator localIterator = this.messages.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return localStringBuffer1.toString();
      String str = (String)localIterator.next();
      List localList = this.messages;
      StringBuffer localStringBuffer2 = localStringBuffer1.append(localList);
    }
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setErrors(List<String> paramList)
  {
    this.errors = paramList;
  }

  public void setLogin(String paramString)
  {
    this.login = paramString;
  }

  public void setMessages(List<String> paramList)
  {
    this.messages = paramList;
  }

  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }

public String getFullName() {
	return fullName;
}

public void setFullName(String fullName) {
	this.fullName = fullName;
}
  
  
}