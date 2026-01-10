from PIL import Image
import os

def images_to_pdf(image_list, output_pdf_path):
    # 打开所有图像并将其转换为 RGB 模式
    images = [Image.open(image).convert('RGB') for image in image_list]

    # 将第一个图像保存为 PDF，并附加其他图像
    if images:
        images[0].save(output_pdf_path, save_all=True, append_images=images[1:])
        print(f"PDF 已保存到 {output_pdf_path}")
    else:
        print("没有找到图片文件来生成 PDF")

if __name__ == "__main__":
    # 图像文件列表（使用绝对路径或相对路径）
    image_folder = "/Users/peihanggu/Documents/漫画/漫画/禁断岛/"  # 修改为你的图片文件夹路径
    image_list = [os.path.join(image_folder, f) for f in sorted(os.listdir(image_folder)) if f.endswith(('png', 'jpg', 'jpeg'))]
    '''
    []才能确保循环的是对象而不是字符串
    for f in sorted(os.listdir(image_folder)):
        if f.endswith(('png', 'jpg', 'jpeg')):
            image_list = os.path.join(image_folder, f)
    '''
    # 指定输出 PDF 的路径
    output_pdf_path = "/Users/peihanggu/Documents/漫画/漫画/禁断岛/output.pdf"

    # 将图像列表转换为 PDF
    images_to_pdf(image_list, output_pdf_path)