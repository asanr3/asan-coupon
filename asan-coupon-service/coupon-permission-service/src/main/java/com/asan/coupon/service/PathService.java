package com.asan.coupon.service;

import com.asan.coupon.dao.PathRepository;
import com.asan.coupon.entity.Path;
import com.asan.coupon.vo.CreatePathRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>路径相关的服务功能实现</h1>
 * @author Asan
 */
@Slf4j
@Service
public class PathService {

    /** Path Repository */
    private final PathRepository pathRepository;

    @Autowired
    public PathService(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    /**
     * <h2>添加新的 path 到数据表中</h2>
     * @param request {@link CreatePathRequest}
     * @return Path 数据记录的主键
     * */
    public List<Integer> createPath(CreatePathRequest request) {

        List<CreatePathRequest.PathInfo> pathInfos = request.getPathInfos();
        // 存储入参中有效的路径信息
        List<CreatePathRequest.PathInfo> validRequests =
                new ArrayList<>(request.getPathInfos().size());

        // 根据微服务的名称获取数据表中的path信息
        List<Path> currentPaths = pathRepository.findAllByServiceName(
                pathInfos.get(0).getServiceName()
        );

        // 检验入参中的path是否已经存在在数据表中
        if (!CollectionUtils.isEmpty(currentPaths)) {

            for (CreatePathRequest.PathInfo pathInfo : pathInfos) {
                boolean isValid = true;

                for (Path currentPath : currentPaths) {
                    // 根据路径和请求类型两个条件判断该信息是否已经存在数据表中
                    if (currentPath.getPathPattern()
                            .equals(pathInfo.getPathPattern()) &&
                    currentPath.getHttpMethod().equals(pathInfo.getHttpMethod())) {
                        isValid = false;
                        break;
                    }
                }
                // 记录有效的（数据表中不存在的path信息）
                if (isValid) {
                    validRequests.add(pathInfo);
                }
            }
        } else {
            validRequests = pathInfos;
        }

        List<Path> paths = new ArrayList<>(validRequests.size());
        validRequests.forEach(p -> paths.add(new Path(
                p.getPathPattern(),
                p.getHttpMethod(),
                p.getPathName(),
                p.getServiceName(),
                p.getOpMode()
        )));
        // 保存路径信息到数据表，并返回id
        return pathRepository.saveAll(paths)
                .stream().map(Path::getId).collect(Collectors.toList());
    }
}
