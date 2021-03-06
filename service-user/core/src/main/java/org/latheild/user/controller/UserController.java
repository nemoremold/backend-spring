package org.latheild.user.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.user.api.dto.*;
import org.latheild.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.user.api.constant.UserUrl.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = CHECK_USER_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkUserExistence(
            @RequestParam(value = "userId") String userId
    ) {
        return userService.checkUserExistence(userId);
    }

    @RequestMapping(value = USER_REGISTER_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object register(
            @RequestBody RegisterDTO registerDTO
    ) {
        try {
            UserDTO userDTO = userService.register(registerDTO);

            UserProfileDTO userProfileDTO = new UserProfileDTO();
            userProfileDTO.setUserId(userDTO.getUserId());
            userProfileDTO.setEmail(userDTO.getEmail());
            userProfileDTO.setAddress(registerDTO.getAddress());
            userProfileDTO.setAvatar(registerDTO.getAvatar());
            userProfileDTO.setGender(registerDTO.getGender());
            userProfileDTO.setJob(registerDTO.getJob());
            userProfileDTO.setPhoneNumber(registerDTO.getPhoneNumber());
            userProfileDTO.setWebsite(registerDTO.getWebsite());
            userProfileDTO.setName(registerDTO.getName());
            userProfileDTO.setBirthday(registerDTO.getBirthday());

            return new BaseResponseBody(CommonErrorCode.SUCCESS, userProfileDTO);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_RESET_PASSWORD_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object resetPassword(
            @RequestBody ResetPasswordDTO resetPasswordDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.resetPassword(resetPasswordDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_CHECK_PASSWORD_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object checkPassword(
            @RequestBody RegisterDTO registerDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.checkPassword(registerDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_USER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getUser(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "email", required = false) String email
    ) {
        try {
            if (id != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getUserByUserId(id));
            } else if (email != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getUserByEmail(email));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_USERS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getAllUsers(
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "scheduleId", required = false) String scheduleId,
            @RequestParam(value = "taskId", required = false) String taskId
    ) {
        try {
            if (projectId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getAllUsersByProjectId(projectId));
            } else if (scheduleId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getAllUsersByScheduleId(scheduleId));
            } else if (taskId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getAllUsersByTaskId(taskId));
            } else {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getAllUsers());
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_USER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteUser(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "code") String code
    ) {
        try {
            if (id != null) {
                userService.adminDeleteUserByUserId(id, code);
            } else if (email != null) {
                userService.adminDeleteUserByEmail(email, code);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_ALL_USERS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteAllUsers(
            @RequestParam(value = "code") String code
    ) {
        try {
            userService.adminDeleteAllUsers(code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_PARTICIPATE_PROJECT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object userParticipateProject(
            @RequestBody UserOperationDTO userOperationDTO
    ) {
        try {
            userService.addUserProject(userOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_QUIT_PROJECT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object userQuitProject(
            @RequestBody UserOperationDTO userOperationDTO
    ) {
        try {
            userService.removeUserProject(userOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_PARTICIPATE_SCHEDULE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object userParticipateSchedule(
            @RequestBody UserOperationDTO userOperationDTO
    ) {
        try {
            userService.addUserSchedule(userOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_QUIT_SCHEDULE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object userQuitSchedule(
            @RequestBody UserOperationDTO userOperationDTO
    ) {
        try {
            userService.removeUserSchedule(userOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_PARTICIPATE_TASK_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object userParticipateTask(
            @RequestBody UserOperationDTO userOperationDTO
    ) {
        try {
            userService.addUserTask(userOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_QUIT_TASK_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object userQuitTask(
            @RequestBody UserOperationDTO userOperationDTO
    ) {
        try {
            userService.removeUserTask(userOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
