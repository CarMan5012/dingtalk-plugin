# DingTalk 机器人通知


![机器人头像](jenkins-logo.png)

> **注意：** 本项目 Fork 自官方仓库 [jenkinsci/dingtalk-plugin](https://github.com/jenkinsci/dingtalk-plugin.git)。

#    [💯　能看到吗？这是文档！！！](https://jenkinsci.github.io/dingtalk-plugin/)

## Contributing

 [🍻　Contributing](./CONTRIBUTING.md)

## ⚠️ 核心使用须知（项目命名规范）

为了让钉钉消息卡片能够自动解析并展示**「模块名称」**和**「运行环境」**等高级字段，你的 Jenkins 任务（JOB_NAME）名称**必须严格遵循以下中划线分隔的命名规范**：

> **`【项目名】-【环境】-【前/后端】-【模块名称】-构建发布`**

*举例说明：`电商系统-prod-后端-订单服务-构建发布`*
- **项目名**：电商系统（会作为卡片大标题）
- **环境**：prod（会自动转换为中文“生产环境”并标红）
- **前/后端 + 模块名称**：后端-订单服务
- **固定后缀**：构建发布

*(如果不按此规范命名，消息卡片仍可正常发送，但会使用原始的项目全名作为标题，且不会拆分展示模块名称和运行环境等字段。)*

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
