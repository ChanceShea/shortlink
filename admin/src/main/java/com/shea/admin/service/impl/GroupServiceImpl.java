package com.shea.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shea.admin.common.biz.user.UserContext;
import com.shea.admin.common.convention.result.Result;
import com.shea.admin.dao.entity.GroupDO;
import com.shea.admin.dao.mapper.GroupMapper;
import com.shea.admin.dto.req.GroupSortReqDTO;
import com.shea.admin.dto.req.GroupUpdateReqDTO;
import com.shea.admin.dto.resp.GroupRespDTO;
import com.shea.admin.remote.ShortLinkRemoteService;
import com.shea.admin.remote.dto.resp.ShortLinkGroupCountRespDTO;
import com.shea.admin.service.GroupService;
import com.shea.admin.toolkit.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Shea
 * @since 2025-05-11
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    //TODO 后续重构为Spring Cloud Feign调用
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    @Override
    public void saveGroup(String groupName) {
        saveGroup(UserContext.getUsername(), groupName);
    }

    @Override
    public void saveGroup(String username, String groupName) {
        String gid;

        do {
            gid = RandomStringUtils.generateRandomString();
        } while (!hasGid(username, gid));

        GroupDO groupDO = GroupDO.builder()
                .gid(gid) // 生成随机的GID
                .name(groupName)
                .username(username)
                .sortOrder(0)
                .build();
        save(groupDO);
    }


    @Override
    public List<GroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        Result<List<ShortLinkGroupCountRespDTO>> listResult = shortLinkRemoteService
                .listGroupCount(groupDOList.stream().map(GroupDO::getGid).toList());
        List<GroupRespDTO> shortLinkGroupRespDTOList = BeanUtil.copyToList(groupDOList, GroupRespDTO.class);
        shortLinkGroupRespDTOList.forEach(each -> {
            Optional<ShortLinkGroupCountRespDTO> first = listResult.getData().stream()
                    .filter(item -> Objects.equals(item.getGid(), each.getGid()))
                    .findFirst();
            //TODO 未完成，明早记得
            first.ifPresent(item -> each.setShortLinkCount(first.get().getCount()));
        });
        return shortLinkGroupRespDTOList;
    }

//    @Override
//    public List<GroupRespDTO> listGroup() {
//        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
//                .eq(GroupDO::getDelFlag, 0)
//                .eq(GroupDO::getUsername, UserContext.getUsername())
//                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
//        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
//        Result<List<ShortLinkGroupCountRespDTO>> listResult = shortLinkRemoteService
//                .listGroupCount(groupDOList.stream().map(GroupDO::getGid).toList());
//        List<GroupRespDTO> shortLinkGroupRespDTOList = BeanUtil.copyToList(groupDOList, GroupRespDTO.class);
//        shortLinkGroupRespDTOList.forEach(each -> {
//            Optional<ShortLinkGroupCountRespDTO> first = listResult.getData().stream()
//                    .filter(item -> Objects.equals(item.getGid(), each.getGid()))
//                    .findFirst();
//            //TODO 未完成，明早记得
//            first.ifPresent(item -> each.setShortLinkCount(first.get().getShortLinkCount()));
//        });
//        return shortLinkGroupRespDTOList;
//    }

    @Override
    public void updateGroup(GroupUpdateReqDTO groupUpdateReqDTO) {
        LambdaUpdateWrapper<GroupDO> wrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getGid, groupUpdateReqDTO.getGid())
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setName(groupUpdateReqDTO.getName());
        update(groupDO, wrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDO> wrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        update(groupDO, wrapper);
    }

    @Override
    public void sortGroup(List<GroupSortReqDTO> groupSortReqDTOS) {
        groupSortReqDTOS.forEach(one -> {
            LambdaUpdateWrapper<GroupDO> wrapper = Wrappers.lambdaUpdate(GroupDO.class)
                    .eq(GroupDO::getGid, one.getGid())
                    .eq(GroupDO::getUsername, UserContext.getUsername())
                    .eq(GroupDO::getDelFlag, 0);
            GroupDO groupDO = new GroupDO();
            groupDO.setSortOrder(one.getSortOrder());
            baseMapper.update(groupDO, wrapper);
        });
    }

    private boolean hasGid(String username, String gid) {
        LambdaQueryWrapper<GroupDO> wrapper = Wrappers
                .lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, Optional.ofNullable(username).orElse(UserContext.getUsername()));
        GroupDO one = baseMapper.selectOne(wrapper);
        return one == null;
    }
}
