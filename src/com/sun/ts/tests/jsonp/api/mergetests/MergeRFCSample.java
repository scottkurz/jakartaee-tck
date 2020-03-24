/*
 * Copyright (c) 2016, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.jsonp.api.mergetests;

import com.sun.ts.lib.util.TestUtil;
import com.sun.ts.tests.jsonp.api.common.TestResult;
import jakarta.json.JsonObject;

import static com.sun.ts.tests.jsonp.api.common.MergeRFCObject.*;

// $Id$
/**
 * RFC 7396: JavaScript Object Notation (JSON) Merge Patch compatibility
 * tests.<br>
 * Test based on
 * {@see <a href="https://tools.ietf.org/html/rfc7396#section-3">RFC 7396: 3.
 * Example</a>} objects.
 */
public class MergeRFCSample extends MergeCommon {

  /**
   * Creates an instance of RFC 7396 value replacing test.
   */
  MergeRFCSample() {
    super();
  }

  /**
   * Test RFC 7396: Adding non existing values. Suite entry point.
   * 
   * @return Result of all tests in this suite.
   */
  TestResult test() {
    final TestResult result = new TestResult("RFC 7396: Example JSON object");
    TestUtil.logMsg("Testing RFC 7396: Example JSON object");
    testMerge(result);
    testDiff(result);
    return result;
  }

  /**
   * Test RFC 7396 patch for example objects.
   * 
   * @param result
   *          Tests result record.
   */
  private void testMerge(final TestResult result) {
    TestUtil.logMsg(" - merge");
    final JsonObject in = createRFCSourceObject();
    final JsonObject patch = createRFCPatchObject();
    final JsonObject check = createRFCTargetObject();
    simpleMerge(result, in, patch, check);
    simpleDiff(result, in, check, patch);
  }

  /**
   * Test RFC 7396 diff for example objects.
   * 
   * @param result
   *          Tests result record.
   */
  private void testDiff(final TestResult result) {
    TestUtil.logMsg(" - diff");
    final JsonObject in = createRFCSourceObject();
    final JsonObject diff = createRFCPatchObject();
    final JsonObject out = createRFCTargetObject();
    simpleDiff(result, in, out, diff);
  }

}
