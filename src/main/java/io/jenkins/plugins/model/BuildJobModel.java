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

  public String toMarkdown() {
    java.util.List<String> lines = new java.util.ArrayList<>();
    String titleName = projectName;
    String moduleName = null;
    String envName = null;

    if (projectName != null) {
      String[] parts = projectName.split("-");
      if (parts.length == 5 && "构建发布".equals(parts[4])) {
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
        moduleName = parts[2] + "-" + parts[3];
      }
    }

    lines.add(String.format("### 🚀 [%s](%s)", titleName, projectUrl));
    lines.add("---");
    lines.add(String.format("📌 **当前任务**：[%s](%s)", jobName, jobUrl));

    if (moduleName != null) {
      lines.add(String.format("📦 **模块名称**：%s", moduleName));
    }
    if (envName != null) {
      lines.add(String.format("🌐 **运行环境**：%s", envName));
    }

    lines.add(
        String.format("🚦 **构建状态**：%s",
            Utils.dye(
                statusType.getLabel(),
                statusType.getColor()
            )
        )
    );
    lines.add(String.format("⏱️ **持续时间**：%s", duration));
    lines.add(String.format("👤 **执行人员**：%s", executorName));
    if (content != null && !"".equals(content)) {
      lines.add(content);
    }

    return Utils.join(lines);
  }
}
