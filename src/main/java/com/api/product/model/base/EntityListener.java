package com.api.product.model.base;

import com.api.product.utils.AppUtil;
import com.api.product.utils.DateUtil;
import lombok.SneakyThrows;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import jakarta.persistence.*;

import java.lang.reflect.Field;

@Component
public class EntityListener {

    @PrePersist
    public void prePersistFunction(Object object) {
        this.assignValueToCommonFields(object, "CREATE");
    }

    @PreUpdate
    public void preUpdateFunction(Object object) {
        this.assignValueToCommonFields(object,"UPDATE");
    }

    @SuppressWarnings("null")
    @SneakyThrows
    private void assignValueToCommonFields(Object arg, String status) {

        String user = null;
        Authentication authen = SecurityContextHolder.getContext().getAuthentication();
        if (AppUtil.isNotNull(authen) && authen.getPrincipal() != "anonymousUser") {
            UserDetails userDetails = (UserDetails) authen.getPrincipal();
            if (AppUtil.isNotNull(userDetails) && AppUtil.isNotNull(userDetails.getUsername())) {
                user = userDetails.getUsername();
            }
        }

        if (status.equals("CREATE")) {
            BeanUtils.setProperty(arg, "createdBy", user != null ? user : "SYSTEM");
            BeanUtils.setProperty(arg, "createdDate", DateUtil.getCurrentDate());
        }else{
            BeanUtils.setProperty(arg, "updatedBy", user != null ? user : "SYSTEM");
            BeanUtils.setProperty(arg, "updatedDate", DateUtil.getCurrentDate());
        }

        Class<?> cls = arg.getClass();
        for (Field field : cls.getDeclaredFields()) {
            
            Field strField = ReflectionUtils.findField(cls, field.getName());
            if (strField.getType().equals(String.class)) {

                strField.setAccessible(true);
                Object value = ReflectionUtils.getField(strField, arg);

                if (AppUtil.isNotNull(value) && AppUtil.isEmpty(value.toString())) {
                    ReflectionUtils.makeAccessible(strField);
                    ReflectionUtils.setField(strField, arg, null);
                }
            }
        }
    }
}


