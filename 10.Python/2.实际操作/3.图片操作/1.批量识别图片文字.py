# pytesseract 谷歌提供的一个py版本接口库
# 先下载tesseract,然后使用pip安装pytesseract模组
# 既然要批量识别,os也是必要的py
import pytesseract
from PIL import Image
import os

def ocr_on_image_folder(image_folder_path):
    # 指定pytesseract包的位置
    pytesseract.pytesseract.tesseract_cmd = r'/opt/homebrew/Cellar/tesseract/5.4.1/bin/tesseract'
    # 确保给的path是一个路径
    if not os.path.isdir(image_folder_path):
        print("Not a folder, Process end")
        return
    
    print("正在遍历图片:")
    # 遍历图片,如果没有os.dir,就变成字符的遍历了
    for filename in os.listdir(image_folder_path):   
        if filename.lower().endswith((".jpg",".png")):
            image_path = os.path.join(image_folder_path, filename)
            print("正在识别图片"+image_path)
            # 打开并识别图片,设定一种语言 chi_ , jpn
            text = pytesseract.image_to_string(Image.open(image_path), lang="jpn_vert")
            print(text)
    print("END")

image_folder_path = "/Users/peihanggu/Documents/漫画/漫画/【FCLG】漫画整理/Test"
ocr_on_image_folder(image_folder_path)
