'''
Created on 23 sept. 2011

@author: thoratou
'''



class Gap:
    '''
    classdocs
    '''

    DIFF_TAG_NAME = 1
    DIFF_ATTRS = 2
    DIFF_CHLD_NB = 3 
    DIFF_NODE_TYPE = 4
    DIFF_TEXT_CONTENT = 5
    
    gap_text = { DIFF_TAG_NAME : "DIFF_TAG_NAME",
                 DIFF_ATTRS : "DIFF_ATTRS",
                 DIFF_CHLD_NB : "DIFF_CHLD_NB",
                 DIFF_NODE_TYPE : "DIFF_NODE_TYPE",
                 DIFF_TEXT_CONTENT : "DIFF_TEXT_CONTENT"
                }
    

    def __init__(self, code,  source, ref):
        '''
        Constructor
        '''
        self.code = code
        self.source = source
        self.ref = ref
        
    def gapText(self):
        return self.gap_text[self.code]        
        