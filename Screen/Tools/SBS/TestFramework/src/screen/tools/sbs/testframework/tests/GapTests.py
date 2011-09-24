'''
Created on 23 sept. 2011

@author: thoratou
'''
import unittest
from xml.dom.minidom import parseString
from screen.tools.sbs.testframework.xml.comparison.Entry import Entry
from screen.tools.sbs.testframework.xml.comparison.Gap import Gap


class GapTests(unittest.TestCase):

    def testRootEqual(self):
        source = """\
            <root>
            </root>
            """
        ref = """\
            <root>
            </root>
            """
        source_dom = parseString(source)
        ref_dom = parseString(ref)
        entry = Entry(source_dom, ref_dom)
        
        self.assertTrue(entry.compare())
        self.assertListEqual(entry.gaps, [])
        
    def testRootGap(self):
        source = """\
            <root>
            </root>
            """
        ref = """\
            <other>
            </other>
            """
        source_dom = parseString(source)
        ref_dom = parseString(ref)
        entry = Entry(source_dom, ref_dom)

        self.assertFalse(entry.compare())
        self.assertEqual(len(entry.gaps), 1)
        
        gap = entry.gaps[0]
        self.assertEqual(gap.code, Gap.DIFF_TAG_NAME)
        self.assertEqual(gap.source.tagName, "root")
        self.assertEqual(gap.ref.tagName, "other")
        
    def testElementGap(self):
        source = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <build>executable</build>
                </properties>
            </pack>
            """
            
        ref = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
            </pack>
            """

        source_dom = parseString(source)
        ref_dom = parseString(ref)
        entry = Entry(source_dom, ref_dom)

        self.assertFalse(entry.compare())
        self.assertEqual(len(entry.gaps), 1)
        
        gap = entry.gaps[0]
        self.assertEqual(gap.code, Gap.DIFF_TAG_NAME)
        self.assertEqual(gap.source.tagName, "build")
        self.assertEqual(gap.ref.tagName, "buildtype")
        
    def testTextGap(self):
        source = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
            </pack>
            """
            
        ref = """\
            <pack>
                <properties>
                    <name>dlroWolleH</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
            </pack>
            """

        source_dom = parseString(source)
        ref_dom = parseString(ref)
        entry = Entry(source_dom, ref_dom)

        self.assertFalse(entry.compare())
        self.assertEqual(len(entry.gaps), 1)
        
        gap = entry.gaps[0]
        self.assertEqual(gap.code, Gap.DIFF_TEXT_CONTENT)
        self.assertEqual(gap.source.tagName, "name")
        self.assertEqual(gap.ref.tagName, "name")
        self.assertEqual(gap.source.firstChild.nodeValue, "HelloWorld")
        self.assertEqual(gap.ref.firstChild.nodeValue, "dlroWolleH")

    def testNodeTypeGap(self):
        source = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
            </pack>
            """
            
        ref = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version><toto/></version>
                    <buildtype>executable</buildtype>
                </properties>
            </pack>
            """

        source_dom = parseString(source)
        ref_dom = parseString(ref)
        entry = Entry(source_dom, ref_dom)

        self.assertFalse(entry.compare())        
        self.assertEqual(len(entry.gaps), 1)
        
        gap = entry.gaps[0]
        self.assertEqual(gap.code, Gap.DIFF_NODE_TYPE)
        self.assertEqual(gap.source.tagName, "version")
        self.assertEqual(gap.ref.tagName, "version")
    
    def testAttrValueGap(self):
        source = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
                <main>
                    <flags>
                        <flag flag="TEXT_HELLO" value="Hello" type="string" />
                    </flags>
                </main>
            </pack>
            """
            
        ref = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
                <main>
                    <flags>
                        <flag flag="HELLO_TEXT" value="Hello" type="string" />
                    </flags>
                </main>
            </pack>
            """

        source_dom = parseString(source)
        ref_dom = parseString(ref)
        entry = Entry(source_dom, ref_dom)

        self.assertFalse(entry.compare())
        self.assertEqual(len(entry.gaps), 1)
        
        gap = entry.gaps[0]
        self.assertEqual(gap.code, Gap.DIFF_ATTRS)
        self.assertEqual(gap.source.tagName, "flag")
        self.assertEqual(gap.ref.tagName, "flag")

    def testAttrGap(self):
        source = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
                <main>
                    <flags>
                        <flag flag="HELLO_TEXT" value="Hello" kind="string" />
                    </flags>
                </main>
            </pack>
            """
            
        ref = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
                <main>
                    <flags>
                        <flag flag="HELLO_TEXT" value="Hello" type="string" />
                    </flags>
                </main>
            </pack>
            """

        source_dom = parseString(source)
        ref_dom = parseString(ref)
        entry = Entry(source_dom, ref_dom)

        self.assertFalse(entry.compare())
        self.assertEqual(len(entry.gaps), 1)
        
        gap = entry.gaps[0]
        self.assertEqual(gap.code, Gap.DIFF_ATTRS)
        self.assertEqual(gap.source.tagName, "flag")
        self.assertEqual(gap.ref.tagName, "flag")
        
    def testComboGap(self):
        source = """\
            <pack>
                <properties>
                    <name>Hello</name>
                    <version>toto</version>
                    <buildtype>executable</buildtype>
                </properties>
                <main>
                    <flags>
                        <flag flag="HELLO_TEXT" value="Hello" type="number" />
                    </flags>
                </main>
            </pack>
            """
            
        ref = """\
            <pack>
                <properties>
                    <name>HelloWorld</name>
                    <version>1.0.0</version>
                    <buildtype>executable</buildtype>
                </properties>
                <main>
                    <flags>
                        <flag flag="HELLO_TEXT" value="Hello" type="string" />
                    </flags>
                </main>
            </pack>
            """

        source_dom = parseString(source)
        ref_dom = parseString(ref)
        entry = Entry(source_dom, ref_dom)

        self.assertFalse(entry.compare())        
        self.assertEqual(len(entry.gaps), 3)
        
        gap = entry.gaps[0]
        self.assertEqual(gap.code, Gap.DIFF_TEXT_CONTENT)
        self.assertEqual(gap.source.tagName, "name")
        self.assertEqual(gap.ref.tagName, "name")
        self.assertEqual(gap.source.firstChild.nodeValue, "Hello")
        self.assertEqual(gap.ref.firstChild.nodeValue, "HelloWorld")

        gap = entry.gaps[1]
        self.assertEqual(gap.code, Gap.DIFF_TEXT_CONTENT)
        self.assertEqual(gap.source.tagName, "version")
        self.assertEqual(gap.ref.tagName, "version")
        self.assertEqual(gap.source.firstChild.nodeValue, "toto")
        self.assertEqual(gap.ref.firstChild.nodeValue, "1.0.0")

        gap = entry.gaps[2]
        self.assertEqual(gap.code, Gap.DIFF_ATTRS)
        self.assertEqual(gap.source.tagName, "flag")
        self.assertEqual(gap.ref.tagName, "flag")


if __name__ == "__main__":
    #import sys;sys.argv = ['', 'Test.testName']
    unittest.main()