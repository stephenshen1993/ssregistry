package com.stephenshen.ssregistry.cluster;

import com.stephenshen.ssregistry.model.InstanceMeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;

/**
 *
 * @author stephenshen
 * @date 2024/5/23 07:49:58
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Snapshot {
    LinkedMultiValueMap<String, InstanceMeta> REGISTRY;
    Map<String, Long> VERSIONS;
    Map<String, Long> TIMESTAMPS;
    long version;
}
