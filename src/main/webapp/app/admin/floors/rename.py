import os

for root, dirs, files in os.walk('.'):
    for f in files:
        f_new = f.replace("floor", "floor")
        print(f_new)
        os.rename(os.path.join(root, f), os.path.join(root, f_new))