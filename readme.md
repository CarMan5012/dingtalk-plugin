# DingTalk 机器人通知


![机器人头像](jenkins-logo.png)


#    [💯　能看到吗？这是文档！！！](https://jenkinsci.github.io/dingtalk-plugin/)

## Contributing

 [🍻　Contributing](./CONTRIBUTING.md)

## 🛠️ 二次开发与 Docker 打包指南

如果你本地没有安装 Java 与 Maven 环境，可以使用本项目提供的 [Dockerfile]快速在 Docker 容器中进行编译和打包：

### 1. 构建编译镜像
在项目根目录下运行以下命令，这会自动拉取编译环境并进行插件打包：
```bash
docker build -t dingtalk-plugin-builder .
```

### 2. 提取编译好的 `.hpi` 插件到本地
编译完成后，运行以下命令，直接将容器内打包好的插件包提取出来并保存到你本地当前目录下：
```bash
docker run --rm --entrypoint cat dingtalk-plugin-builder /dingding-notifications.hpi > dingding-notifications.hpi
```

*打包成功后，你可以直接将本地生成的 `dingding-notifications.hpi` 文件上传到 Jenkins 的 **系统管理** -> **插件管理** -> **高级设置** 进行手动安装安装。*
