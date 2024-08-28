# 之前用到的文件操作是open 
# 这里使用os模块.os.walk()方便遍历文件
import os;
def find_all_files(root_path):
    all_files = []
    for dirpath, dirnames, filenames in os.walk(root_path):
        for filenames in filenames:
            # 把文件的路径,文件名组合起来添加到列表
            all_files.append(os.path.join(dirpath, filenames))
    return all_files

root_path='/Users/peihanggu/GithubLocal/Practice/10.Python'
files = find_all_files(root_path)
print("begin")
for file in files:
    print(file)
print("end")