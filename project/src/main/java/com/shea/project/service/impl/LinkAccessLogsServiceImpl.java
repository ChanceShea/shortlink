package com.shea.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shea.project.dao.entity.LinkAccessLogsDO;
import com.shea.project.dao.mapper.LinkAccessLogsMapper;
import com.shea.project.service.ILinkAccessLogsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Shea
 * @since 2025-05-15
 */
@Service
public class LinkAccessLogsServiceImpl extends ServiceImpl<LinkAccessLogsMapper, LinkAccessLogsDO> implements ILinkAccessLogsService {

}
