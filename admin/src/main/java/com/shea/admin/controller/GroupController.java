package com.shea.admin.controller;


import com.shea.admin.common.convention.result.Result;
import com.shea.admin.common.convention.result.Results;
import com.shea.admin.dto.req.GroupSaveReqDTO;
import com.shea.admin.dto.req.GroupSortReqDTO;
import com.shea.admin.dto.req.GroupUpdateReqDTO;
import com.shea.admin.dto.resp.GroupRespDTO;
import com.shea.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接分组控制层
 *
 * @author Shea
 * @since 2025-05-11
 */
@RestController
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/short-link/admin/v1/group")
    public Result<Void> saveGroup(@RequestBody GroupSaveReqDTO groupSaveReqDTO) {
        groupService.saveGroup(groupSaveReqDTO.getName());
        return Results.success();
    }

    /**
     * 获取短链接分组列表
     */
    @GetMapping("/api/short-link/admin/v1/group")
    public Result<List<GroupRespDTO>> listGroup() {
        List<GroupRespDTO> groupRespDTOS = groupService.listGroup();
        return Results.success(groupRespDTOS);
    }

    /**
     * 修改短链接分组
     */
    @PutMapping("/api/short-link/admin/v1/group")
    public Result<Void> updateGroup(@RequestBody GroupUpdateReqDTO groupUpdateReqDTO) {
        groupService.updateGroup(groupUpdateReqDTO);
        return Results.success();
    }

    /**
     * 删除短链接分组
     */
    @DeleteMapping("/api/short-link/admin/v1/group")
    public Result<Void> deleteGroup(@RequestParam String gid) {
        groupService.deleteGroup(gid);
        return Results.success();
    }

    @PostMapping("/api/short-link/admin/v1/group/sort")
    public Result<Void> sortGroup(@RequestBody List<GroupSortReqDTO> groupSortReqDTOS) {
        groupService.sortGroup(groupSortReqDTOS);
        return Results.success();
    }
}
