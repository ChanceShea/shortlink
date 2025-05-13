package com.shea.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shea.admin.dao.entity.GroupDO;
import com.shea.admin.dto.req.GroupSortReqDTO;
import com.shea.admin.dto.req.GroupUpdateReqDTO;
import com.shea.admin.dto.resp.GroupRespDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Shea
 * @since 2025-05-11
 */
public interface GroupService extends IService<GroupDO> {

    /**
     * 新增短链接分组
     *
     * @param groupName 短链接分组名称
     */
    void saveGroup(String groupName);

    /**
     * 新增短链接分组
     *
     * @param username  用户名
     * @param groupName 短链接分组名称
     */
    void saveGroup(String username, String groupName);

    /**
     * 获取所有短链接分组
     *
     * @return 短链接分组列表
     */
    List<GroupRespDTO> listGroup();

    /**
     * 更新短链接分组
     *
     * @param groupUpdateReqDTO 更新短链接分组请求参数
     */
    void updateGroup(GroupUpdateReqDTO groupUpdateReqDTO);

    /**
     * 删除短链接分组
     *
     * @param gid 短链接分组标识
     */
    void deleteGroup(String gid);

    /**
     * 批量更新短链接分组排序
     *
     * @param groupSortReqDTOS 短链接分组排序请求参数
     */
    void sortGroup(List<GroupSortReqDTO> groupSortReqDTOS);
}
