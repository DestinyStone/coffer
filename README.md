# 系统环境要求
| 软件                   | 版本                   |
| ---------------------- | -------------------- |
| jdk                    | 1.8                  |
| node                   | v12.16.3             |
| mysql                  | 8.0                  |
| idea                   | 2024.2.3              |

# 项目运行、测试和部署

## 前端项目
1. git clone 将项目部署至本地
   ```shell
   git clone git@github.com:DestinyStone/goods.git
   ```
   ![](images/1.jpg)
2. 项目根目录下打开cmd, 使用 cd 进入前端目录
   ```shell
   cd 前端源码
   ```
   ![](images/2.jpg)
3. npm install 安装项目依赖
      ```shell
   npm install
   ```
   ![](images/3.jpg)
4. npm run server 运行项目
   ```shell
   npm run server
   ```
   ![](images/6.jpg)
   ![](images/5.jpg)
5. 访问 http://127.0.0.1:8080 测试项目是否正常
   ![](images/7.jpg)
## 后端项目
1. git clone 将项目部署至本地
   ```shell
   git clone git@github.com:DestinyStone/goods.git
   ```
   ![](images/1.jpg)
2. 使用idea打开项目，等待maven依赖安装完毕
   
   ![](images/8.jpg)
3. 启动DemoApplication类运行项目
   
   ![](images/9.jpg)
   
4. 访问 http://127.0.0.1:9999/user/list 测试项目是否正常
   
   ![](images/10.jpg)

