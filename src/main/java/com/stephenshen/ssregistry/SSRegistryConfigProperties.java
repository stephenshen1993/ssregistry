package com.stephenshen.ssregistry;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author stephenshen
 * @date 2024/5/17 07:35:37
 */
@Data
@ConfigurationProperties(prefix = "ssregistry")
public class SSRegistryConfigProperties {

    private List<String> serverList;
}
