# =====================================================================
# 🛠️ 使用 Docker 编译并提取插件的步骤：
#
# 1. 构建编译镜像 (在项目根目录下运行)：
#    docker build -t dingtalk-plugin-builder .
#
# 2. 提取编译好的 .hpi 插件到当前本地目录：
#    docker run --rm --entrypoint cat dingtalk-plugin-builder /dingding-notifications.hpi > dingding-notifications.hpi
# =====================================================================

# 使用官方 Maven 镜像进行编译打包 (基于 Java 17)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# 先拷贝 pom.xml 下载依赖（利用 Docker 缓存机制，后续代码修改时构建会更快）
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# 拷贝源码并打包
COPY src ./src
# 加上 -DskipTests 跳过测试以加快打包速度
RUN mvn clean package -DskipTests

# 最小化镜像，用于存放打包好的插件包
FROM alpine:latest
COPY --from=builder /app/target/dingding-notifications.hpi /dingding-notifications.hpi

# 提示信息
CMD ["echo", "编译完成！您可以从容器中拷贝出 /dingding-notifications.hpi 插件。"]
