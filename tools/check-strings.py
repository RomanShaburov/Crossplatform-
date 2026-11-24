import xml.etree.ElementTree as ET
import os
import sys

def get_keys(xml_path):
    if not os.path.exists(xml_path):
        return set()
    tree = ET.parse(xml_path)
    return {s.get('name') for s in tree.getroot().findall('string')}

ru_path = 'shared/src/commonMain/composeResources/values/strings.xml'
en_path = 'shared/src/commonMain/composeResources/values-en/strings.xml'

ru_keys = get_keys(ru_path)
en_keys = get_keys(en_path)

m_in_en = ru_keys - en_keys
m_in_ru = en_keys - ru_keys

if not m_in_en and not m_in_ru:
    print(f"SUCCESS: {len(ru_keys)} keys match")
else:
    if m_in_en: print(f"MISSING IN EN: {m_in_en}")
    if m_in_ru: print(f"MISSING IN RU: {m_in_ru}")
    sys.exit(1)
