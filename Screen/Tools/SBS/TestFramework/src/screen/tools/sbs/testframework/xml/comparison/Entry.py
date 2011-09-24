'''
Created on 23 sept. 2011

@author: thoratou
'''

from screen.tools.sbs.testframework.xml.comparison.Gap import Gap

class Entry:
    '''
    classdocs
    '''


    def __init__(self, source_dom, reference_dom):
        '''
        Constructor
        '''
        self.source_dom = source_dom
        self.reference_dom = reference_dom
        self.gaps = []
        
    def compare(self):
        self.gaps = []
        
        source_root = self.source_dom.documentElement
        ref_root = self.reference_dom.documentElement
        
        return self.isEqualElement(source_root, ref_root)

    def isEqualElement(self, source, ref):
        ret = True
        
        if source.tagName != ref.tagName:
            self.gaps.append(Gap(Gap.DIFF_TAG_NAME, source, ref))
            return False
                
        if sorted(source.attributes.items()) != sorted(ref.attributes.items()):
            self.gaps.append(Gap(Gap.DIFF_ATTRS, source, ref))
            ret = False
        
        if len(source.childNodes) != len(ref.childNodes):
            self.gaps.append(Gap(Gap.DIFF_CHLD_NB, source, ref))
            return False
            
        for source_child, ref_child in zip(source.childNodes, ref.childNodes):
            if source_child.nodeType != ref_child.nodeType:
                self.gaps.append(Gap(Gap.DIFF_NODE_TYPE, source, ref))
                ret = False
            elif source_child.nodeType == source_child.TEXT_NODE and source_child.data != ref_child.data:
                self.gaps.append(Gap(Gap.DIFF_TEXT_CONTENT, source, ref))
                ret = False
            elif source_child.nodeType == source_child.ELEMENT_NODE and not self.isEqualElement(source_child, ref_child):
                ret = False
        
        return ret