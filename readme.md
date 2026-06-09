# DingTalk 机器人通知


![机器人头像](jenkins-logo.png)

> **注意：** 本项目 Fork 自官方仓库 [jenkinsci/dingtalk-plugin](https://github.com/jenkinsci/dingtalk-plugin.git)。

#    [💯　能看到吗？这是文档！！！](https://jenkinsci.github.io/dingtalk-plugin/)

## Contributing

 [🍻　Contributing](./CONTRIBUTING.md)

## 🛠️ 二次开发与打包指南

本项目提供了一键打包的环境支持，极大降低了开发门槛。

> [!TIP]
> **免环境编译方案**
> 如果你本地没有安装 Java 与 Maven 等开发环境，强烈推荐使用本项目提供的 `Dockerfile`。你可以直接在 Docker 容器中完成源码的编译和打包，做到真正的“开箱即用”。



### Step 1: 构建编译镜像

在项目根目录下运行以下命令。该操作会自动拉取 Maven 编译环境、下载依赖，并完成插件的构建打包（此过程可能需要几分钟，请耐心等待）：

```bash
docker build -t dingtalk-plugin-builder .
```

### Step 2: 提取插件包 (HPI)

编译成功后，我们需要将打包好的插件从镜像中提取到本地。执行以下命令即可在当前目录下生成 `.hpi` 插件安装文件：

```bash
docker create --name extract-container dingtalk-plugin-builder
docker cp extract-container:/dingding-notifications.hpi ./dingding-notifications.hpi
docker rm extract-container
```

### Step 3: 手动安装到 Jenkins

获取到 `dingding-notifications.hpi` 文件后：
1. 登录你的 Jenkins 控制台。
2. 导航至 **系统管理 (Manage Jenkins)** -> **插件管理 (Plugins)**。
3. 点击 **高级设置 (Advanced settings)** 选项卡。
4. 找到 **部署插件 (Deploy Plugin)** 区域，上传生成的 `.hpi` 文件并重启 Jenkins 即可完成本地二次开发包的安装。
