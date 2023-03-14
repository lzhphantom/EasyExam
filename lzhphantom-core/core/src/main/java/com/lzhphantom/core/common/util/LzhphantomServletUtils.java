package com.lzhphantom.core.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.hutool.core.collection.ArrayIter;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.CaseInsensitiveMap;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.net.multipart.MultipartFormData;
import cn.hutool.core.net.multipart.UploadSetting;
import cn.hutool.core.util.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;

public class LzhphantomServletUtils {
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";

    public LzhphantomServletUtils() {
    }

    public static Map<String, String[]> getParams(ServletRequest request) {
        Map<String, String[]> map = request.getParameterMap();
        return Collections.unmodifiableMap(map);
    }

    public static Map<String, String> getParamMap(ServletRequest request) {
        Map<String, String> params = new HashMap();

        for (Map.Entry<String, String[]> stringEntry : getParams(request).entrySet()) {
            params.put(stringEntry.getKey(),
                    ArrayUtil.join((Object[]) stringEntry.getValue(), ","));
        }

        return params;
    }

    public static String getBody(ServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            Throwable var2 = null;

            String var3;
            try {
                var3 = IoUtil.read(reader);
            } catch (Throwable var13) {
                var2 = var13;
                throw var13;
            } finally {
                if (reader != null) {
                    if (var2 != null) {
                        try {
                            reader.close();
                        } catch (Throwable var12) {
                            var2.addSuppressed(var12);
                        }
                    } else {
                        reader.close();
                    }
                }

            }

            return var3;
        } catch (IOException var15) {
            throw new IORuntimeException(var15);
        }
    }

    public static byte[] getBodyBytes(ServletRequest request) {
        try {
            return IoUtil.readBytes(request.getInputStream());
        } catch (IOException var2) {
            throw new IORuntimeException(var2);
        }
    }

    public static <T> T fillBean(final ServletRequest request, T bean, CopyOptions copyOptions) {
        final String beanName = StrUtil.lowerFirst(bean.getClass().getSimpleName());
        return BeanUtil.fillBean(bean, new ValueProvider<>() {
            public Object value(String key, Type valueType) {
                String[] values = request.getParameterValues(key);
                if (ArrayUtil.isEmpty(values)) {
                    values = request.getParameterValues(beanName + "." + key);
                    if (ArrayUtil.isEmpty(values)) {
                        return null;
                    }
                }

                return 1 == values.length ? values[0] : values;
            }

            public boolean containsKey(String key) {
                return null != request.getParameter(key) || null != request.getParameter(beanName + "." + key);
            }
        }, copyOptions);
    }

    public static <T> T fillBean(ServletRequest request, T bean, boolean isIgnoreError) {
        return fillBean(request, bean, CopyOptions.create().setIgnoreError(isIgnoreError));
    }

    public static <T> T toBean(ServletRequest request, Class<T> beanClass, boolean isIgnoreError) {
        return fillBean(request, ReflectUtil.newInstanceIfPossible(beanClass), isIgnoreError);
    }

    public static String getClientIP(HttpServletRequest request, String... otherHeaderNames) {
        String[] headers = new String[]{"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
        if (ArrayUtil.isNotEmpty(otherHeaderNames)) {
            headers = ArrayUtil.addAll(headers, otherHeaderNames);
        }

        return getClientIPByHeader(request, headers);
    }

    public static String getClientIPByHeader(HttpServletRequest request, String... headerNames) {
        int var4 = headerNames.length;

        String ip;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (!NetUtil.isUnknown(ip)) {
                return NetUtil.getMultistageReverseProxyIp(ip);
            }
        }

        ip = request.getRemoteAddr();
        return NetUtil.getMultistageReverseProxyIp(ip);
    }

    public static MultipartFormData getMultipart(ServletRequest request) throws IORuntimeException {
        return getMultipart(request, new UploadSetting());
    }

    public static MultipartFormData getMultipart(ServletRequest request, UploadSetting uploadSetting) throws IORuntimeException {
        MultipartFormData formData = new MultipartFormData(uploadSetting);

        try {
            formData.parseRequestStream(request.getInputStream(), CharsetUtil.charset(request.getCharacterEncoding()));
            return formData;
        } catch (IOException var4) {
            throw new IORuntimeException(var4);
        }
    }

    public static Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap();
        Enumeration<String> names = request.getHeaderNames();

        while(names.hasMoreElements()) {
            String name = names.nextElement();
            headerMap.put(name, request.getHeader(name));
        }

        return headerMap;
    }

    public static Map<String, List<String>> getHeadersMap(HttpServletRequest request) {
        Map<String, List<String>> headerMap = new LinkedHashMap();
        Enumeration<String> names = request.getHeaderNames();

        while(names.hasMoreElements()) {
            String name = names.nextElement();
            headerMap.put(name, ListUtil.list(false, request.getHeaders(name)));
        }

        return headerMap;
    }

    public static Map<String, Collection<String>> getHeadersMap(HttpServletResponse response) {
        Map<String, Collection<String>> headerMap = new HashMap();
        Collection<String> names = response.getHeaderNames();

        for (String name : names) {
            headerMap.put(name, response.getHeaders(name));
        }

        return headerMap;
    }

    public static String getHeaderIgnoreCase(HttpServletRequest request, String nameIgnoreCase) {
        Enumeration<String> names = request.getHeaderNames();

        String name;
        do {
            if (!names.hasMoreElements()) {
                return null;
            }

            name = names.nextElement();
        } while(name == null || !name.equalsIgnoreCase(nameIgnoreCase));

        return request.getHeader(name);
    }

    public static String getHeader(HttpServletRequest request, String name, String charsetName) {
        return getHeader(request, name, CharsetUtil.charset(charsetName));
    }

    public static String getHeader(HttpServletRequest request, String name, Charset charset) {
        String header = request.getHeader(name);
        return null != header ? CharsetUtil.convert(header, CharsetUtil.CHARSET_ISO_8859_1, charset) : null;
    }

    public static boolean isIE(HttpServletRequest request) {
        String userAgent = getHeaderIgnoreCase(request, "User-Agent");
        if (!StrUtil.isNotBlank(userAgent)) {
            return false;
        } else {
            userAgent = Objects.requireNonNull(userAgent).toUpperCase();
            return userAgent.contains("MSIE") || userAgent.contains("TRIDENT");
        }
    }

    public static boolean isGetMethod(HttpServletRequest request) {
        return "GET".equalsIgnoreCase(request.getMethod());
    }

    public static boolean isPostMethod(HttpServletRequest request) {
        return "POST".equalsIgnoreCase(request.getMethod());
    }

    public static boolean isMultipart(HttpServletRequest request) {
        if (!isPostMethod(request)) {
            return false;
        } else {
            String contentType = request.getContentType();
            return StrUtil.isBlank(contentType) ? false : contentType.toLowerCase().startsWith("multipart/");
        }
    }

    public static Cookie getCookie(HttpServletRequest httpServletRequest, String name) {
        return readCookieMap(httpServletRequest).get(name);
    }

    public static Map<String, Cookie> readCookieMap(HttpServletRequest httpServletRequest) {
        Cookie[] cookies = httpServletRequest.getCookies();
        return ArrayUtil.isEmpty(cookies) ? MapUtil.empty() : IterUtil.toMap(new ArrayIter(httpServletRequest.getCookies()), new CaseInsensitiveMap(), Cookie::getName);
    }

    public static void addCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse response, String name, String value) {
        response.addCookie(new Cookie(name, value));
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds, String path, String domain) {
        Cookie cookie = new Cookie(name, value);
        if (domain != null) {
            cookie.setDomain(domain);
        }

        cookie.setMaxAge(maxAgeInSeconds);
        cookie.setPath(path);
        addCookie(response, cookie);
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        addCookie(response, name, value, maxAgeInSeconds, "/", null);
    }

    public static PrintWriter getWriter(HttpServletResponse response) throws IORuntimeException {
        try {
            return response.getWriter();
        } catch (IOException var2) {
            throw new IORuntimeException(var2);
        }
    }

    public static void write(HttpServletResponse response, String text, String contentType) {
        response.setContentType(contentType);
        Writer writer = null;

        try {
            writer = response.getWriter();
            writer.write(text);
            writer.flush();
        } catch (IOException var8) {
            throw new UtilException(var8);
        } finally {
            IoUtil.close(writer);
        }

    }

    public static void write(HttpServletResponse response, File file) {
        String fileName = file.getName();
        String contentType = ObjectUtil.defaultIfNull(FileUtil.getMimeType(fileName), "application/octet-stream");
        BufferedInputStream in = null;

        try {
            in = FileUtil.getInputStream(file);
            write(response, in, contentType, fileName);
        } finally {
            IoUtil.close(in);
        }

    }

    public static void write(HttpServletResponse response, InputStream in, String contentType, String fileName) {
        String charset = ObjectUtil.defaultIfNull(response.getCharacterEncoding(), "UTF-8");
        String encodeText = URLUtil.encodeAll(fileName, CharsetUtil.charset(charset));
        response.setHeader("Content-Disposition", StrUtil.format("attachment;filename=\"{}\";filename*={}''{}", encodeText, charset, encodeText));
        response.setContentType(contentType);
        write(response, in);
    }

    public static void write(HttpServletResponse response, InputStream in, String contentType) {
        response.setContentType(contentType);
        write(response, in);
    }

    public static void write(HttpServletResponse response, InputStream in) {
        write(response, in, 8192);
    }

    public static void write(HttpServletResponse response, InputStream in, int bufferSize) {
        ServletOutputStream out = null;

        try {
            out = response.getOutputStream();
            IoUtil.copy(in, out, bufferSize);
        } catch (IOException var8) {
            throw new UtilException(var8);
        } finally {
            IoUtil.close(out);
            IoUtil.close(in);
        }

    }

    public static void setHeader(HttpServletResponse response, String name, Object value) {
        if (value instanceof String) {
            response.setHeader(name, (String)value);
        } else if (Date.class.isAssignableFrom(value.getClass())) {
            response.setDateHeader(name, ((Date)value).getTime());
        } else if (!(value instanceof Integer) && !"int".equalsIgnoreCase(value.getClass().getSimpleName())) {
            response.setHeader(name, value.toString());
        } else {
            response.setIntHeader(name, (Integer)value);
        }

    }
}
