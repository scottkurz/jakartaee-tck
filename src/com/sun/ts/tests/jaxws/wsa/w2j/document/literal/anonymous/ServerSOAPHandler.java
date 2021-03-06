/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id: ServerSOAPHandler.java 52501 2007-01-24 02:29:49Z af70133 $
 */

package com.sun.ts.tests.jaxws.wsa.w2j.document.literal.anonymous;

import com.sun.ts.lib.util.*;
import com.sun.ts.lib.porting.*;
import com.sun.ts.lib.harness.*;

import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Set;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;

import com.sun.ts.tests.jaxws.common.Handler_Util;
import com.sun.ts.tests.jaxws.wsa.common.WsaBaseSOAPHandler;
import com.sun.ts.tests.jaxws.wsa.common.W3CAddressingConstants;
import com.sun.ts.tests.jaxws.wsa.common.ActionNotSupportedException;
import com.sun.ts.tests.jaxws.wsa.common.AddressingPropertyException;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.Text;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class ServerSOAPHandler extends WsaBaseSOAPHandler {
  protected void checkInboundAction(SOAPMessageContext context, String oper,
      String action) {
    TestUtil.logMsg("ServerSOAPHandler.checkInboundAction: [operation=" + oper
        + ", input action=" + action + "]");
    System.out.println("ServerSOAPHandler.checkInboundAction: [operation="
        + oper + ", input action=" + action + "]");
    if (Handler_Util.checkForMsg(context, "testAnonymousResponsesAssertion")) {
      VerifyAddressingHeadersForAnonymousResponsesAssertion(context, action);
    } else if (Handler_Util.checkForMsg(context,
        "testNonAnonymousResponsesAssertion")) {
      VerifyAddressingHeadersForNonAnonymousResponsesAssertion(context, action);
    }
  }

  private void VerifyAddressingHeadersForAnonymousResponsesAssertion(
      SOAPMessageContext context, String action) {
    TestUtil.logMsg(
        "ServerSOAPHandler.VerifyAddressingHeadersForAnonymousResponsesAssertion");
    System.out.println(
        "ServerSOAPHandler.VerifyAddressingHeadersForAnonymousResponsesAssertion");
    if (!TestConstants.TEST_ANONYMOUS_RESPONSES_ASSERTION_IN_ACTION
        .equals(action)) {
      throw new ActionNotSupportedException("Expected:"
          + TestConstants.TEST_ANONYMOUS_RESPONSES_ASSERTION_IN_ACTION
          + ", Actual:" + action);
    }
    try {
      String to = getTo(context);
      TestUtil.logMsg("[To=" + to + "]");
      System.out.println("[To=" + to + "]");
    } catch (Exception e) {
      TestUtil.logMsg("Exception occurred: " + e);
    }
    String replyTo = null;
    try {
      replyTo = getReplyTo(context);
      TestUtil.logMsg("[ReplyTo=" + replyTo + "]");
      System.out.println("[ReplyTo=" + replyTo + "]");
    } catch (Exception e) {
      TestUtil.logMsg("Exception occurred: " + e);
    }
    if (replyTo != null) {
      if (!replyTo.equals(W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS_URI)
          && !replyTo.equals(W3CAddressingConstants.WSA_NONE_ADDRESS)) {
        throw new AddressingPropertyException("Expected: wsa:ReplyTo="
            + W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS_URI + " or "
            + W3CAddressingConstants.WSA_NONE_ADDRESS + ", Actual: wsa:ReplyTo="
            + replyTo);
      }
    }
    try {
      String messageID = getMessageId(context);
      TestUtil.logMsg("[MessageID=" + messageID + "]");
      System.out.println("[MessageID=" + messageID + "]");
    } catch (Exception e) {
      throw new AddressingPropertyException(
          "wsa:MessageID was not set (unexpected)");
    }
  }

  private void VerifyAddressingHeadersForNonAnonymousResponsesAssertion(
      SOAPMessageContext context, String action) {
    TestUtil.logMsg(
        "ServerSOAPHandler.VerifyAddressingHeadersForNonAnonymousResponsesAssertion");
    System.out.println(
        "ServerSOAPHandler.VerifyAddressingHeadersForNonAnonymousResponsesAssertion");
    if (!TestConstants.TEST_NONANONYMOUS_RESPONSES_ASSERTION_IN_ACTION
        .equals(action)) {
      throw new ActionNotSupportedException("Expected:"
          + TestConstants.TEST_NONANONYMOUS_RESPONSES_ASSERTION_IN_ACTION
          + ", Actual:" + action);
    }
    try {
      String to = getTo(context);
      TestUtil.logMsg("[To=" + to + "]");
      System.out.println("[To=" + to + "]");
    } catch (Exception e) {
      TestUtil.logMsg("Exception occurred: " + e);
    }
    String replyTo;
    try {
      replyTo = getReplyTo(context);
      TestUtil.logMsg("[ReplyTo=" + replyTo + "]");
      System.out.println("[ReplyTo=" + replyTo + "]");
    } catch (Exception e) {
      throw new AddressingPropertyException(
          "wsa:ReplyTo was not set (unexpected)");
    }
    if (replyTo.equals(W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS_URI))
      throw new AddressingPropertyException("Expected: wsa:ReplyTo=!"
          + W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS_URI
          + ", Actual: wsa:ReplyTo=" + replyTo);
    try {
      String messageID = getMessageId(context);
      TestUtil.logMsg("[MessageID=" + messageID + "]");
      System.out.println("[MessageID=" + messageID + "]");
    } catch (Exception e) {
      throw new AddressingPropertyException(
          "wsa:MessageID was not set (unexpected)");
    }
  }

  protected String whichHandler() {
    return "ServerSOAPHandler";
  }
}
