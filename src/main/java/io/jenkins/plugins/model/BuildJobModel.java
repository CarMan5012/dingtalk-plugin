package io.jenkins.plugins.model;

import io.jenkins.plugins.enums.BuildStatusEnum;
import io.jenkins.plugins.tools.Utils;
import java.util.Arrays;
import lombok.Builder;
import lombok.Data;

/**
 * @author liuwei
 */
@Data
@Builder
public class BuildJobModel {

  private String projectName;

  private String projectUrl;

  private String jobName;

  private String jobUrl;

  private BuildStatusEnum statusType;

  private String duration;

  private String executorName;

  private String executorMobile;

  private String content;

  private String gitBranch;

  private String customTitle;

  private boolean enableEmojis;

  private String emoji(String icon) {
    return enableEmojis ? icon + " " : "";
  }

  public String toMarkdown() {
    java.util.List<String> lines = new java.util.ArrayList<>();
    String titleName = projectName;
    String moduleName = null;
    String envName = null;

    if (projectName != null) {
      String[] parts = projectName.split("-");
      if (parts.length >= 5 && "构建发布".equals(parts[parts.length - 1])) {
        titleName = parts[0];
        String env = parts[1];
        if ("test".equalsIgnoreCase(env)) {
          envName = "测试环境";
        } else if ("dev".equalsIgnoreCase(env)) {
          envName = "开发环境";
        } else if ("prod".equalsIgnoreCase(env)) {
          envName = Utils.dye("生产环境", io.jenkins.plugins.tools.Constants.COLOR_RED);
        } else if ("uat".equalsIgnoreCase(env)) {
          envName = "UAT环境";
        } else {
          envName = env;
        }
        StringBuilder moduleBuilder = new StringBuilder();
        for (int i = 2; i < parts.length - 1; i++) {
          if (i > 2) {
            moduleBuilder.append("-");
          }
          moduleBuilder.append(parts[i]);
        }
        moduleName = moduleBuilder.toString();
      }
    }

    String displayTitle = titleName + "-发版报告";
    if (customTitle != null && !customTitle.isEmpty()) {
      displayTitle = customTitle;
    }
    
    // 添加前缀引语，防止钉钉预览列表强制拼接导致的标题重复
    lines.add("> 🤖 **自动化持续集成与部署通知**");
    lines.add("");
    lines.add(String.format("### %s<font color=\"#1890ff\">%s</font>", emoji("🚀").trim(), displayTitle));
    lines.add("---");

    if (moduleName != null) {
      lines.add(String.format("%s**模块**：%s", emoji("📦"), moduleName));
    }

    if (gitBranch != null && !"".equals(gitBranch)) {
      String displayBranch = gitBranch.startsWith("origin/") ? gitBranch.substring(7) : gitBranch;
      lines.add(String.format("%s**分支**：%s", emoji("🌿"), displayBranch));
    }

    lines.add(String.format("%s**编号**：[%s](%s)", emoji("📌"), jobName, jobUrl));

    lines.add(
        String.format("%s**状态**：%s",
            emoji("🚦"),
            Utils.dye(
                statusType.getLabel(),
                statusType.getColor()
            )
        )
    );

    if (envName != null) {
      lines.add(String.format("%s**环境**：%s", emoji("🌐"), envName));
    }

    lines.add(String.format("%s**耗时**：%s", emoji("⏱️"), duration));
    lines.add(String.format("%s**触发**：%s", emoji("👤"), executorName));
    if (content != null && !"".equals(content)) {
      lines.add(content);
    }

    return Utils.join(lines);
  }
}
