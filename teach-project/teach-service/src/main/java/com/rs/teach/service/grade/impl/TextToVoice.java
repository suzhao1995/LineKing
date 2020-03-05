package com.rs.teach.service.grade.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.rs.teach.mapper.grade.entity.GradePreschool;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.HttpProfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tencentcloudapi.common.profile.ClientProfile;

import com.tencentcloudapi.tts.v20190823.TtsClient;

import com.tencentcloudapi.tts.v20190823.models.TextToVoiceRequest;
import com.tencentcloudapi.tts.v20190823.models.TextToVoiceResponse;

/**
 * @author wanghang
 * @Description
 * @create 2020-03-05 16:11
 */
public class TextToVoice {

    private static final String IMGPATH = "D:\\RSUpLoad\\ceping\\yinpin";    //图片路径

    private static final String SERVERPATH = "http://teachatlke.com/teach-web/upLoad/ceping/yinpin";    //服务器地址

    private static final String FILETYPE = ".mp3";    //文件格式

    private static final String SECRETID = "AKIDBZmibCFeaHtMZgFAZkguzocBZxGULLPf";    //SecretId

    private static final String SECRETKEY = "OqDVipsoviQchGPciomiRyJRKxYu1z2r";    //SecretKey

    private static final String ENDPOINT = "tts.tencentcloudapi.com";    //ENDPOINT


    /**
     * 生成题目语音MP3
     *
     * @param list textToVoice
     * @return
     */
    public static List<GradePreschool> questionAudio(List<GradePreschool> list) {
        try {
            List<GradePreschool> gradePreschools = new ArrayList<>();

            for (GradePreschool gradePreschool : list) {
                String questionAudio = textToVoice(gradePreschool);
                GradePreschool preschool = new GradePreschool();
                preschool.setQuestionAudio(questionAudio);
                preschool.setPreschoolId(gradePreschool.getPreschoolId());
                gradePreschools.add(preschool);
            }
            return gradePreschools;

        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    /**
     * base64装换为MP3
     *
     * @param audio
     * @param preschoolId
     * @return
     */
    public static String base64Decode(String audio, Integer preschoolId) {
        File file = new File(StrUtil.format("{}{}{}", IMGPATH, StrUtil.format("{}TI", preschoolId), FILETYPE));
        Base64.decodeToFile(audio, file);
        return StrUtil.format("{}{}{}", SERVERPATH, StrUtil.format("{}TI", preschoolId), FILETYPE);
    }

    /**
     * 生成base64音频
     *
     * @param gradePreschool
     * @return
     * @throws TencentCloudSDKException
     */
    public static String textToVoice(GradePreschool gradePreschool) throws TencentCloudSDKException {
        Credential cred = new Credential(SECRETID, SECRETKEY);

        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(ENDPOINT);

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        TtsClient client = new TtsClient(cred, "ap-guangzhou", clientProfile);

        //String params = "{\"Text\":\"" + gradePreschool.getQuestion() + "\",\"SessionId\":\"955e96db-1561-4349-85f7-e91998b73ee1\",\"ModelType\":1,\"PrimaryLanguage\":2,\"Codec\":\"mp3\"}";
        String params = "{\"Text\":\"" + gradePreschool.getQuestion() + "\",\"SessionId\":\"955e96db-1561-4349-85f7-e91998b73ee1\",\"Volume\":3,\"Speed\":-2,\"ModelType\":1,\"VoiceType\":4,\"PrimaryLanguage\":2,\"Codec\":\"mp3\"}";
        TextToVoiceRequest req = TextToVoiceRequest.fromJsonString(params, TextToVoiceRequest.class);

        TextToVoiceResponse resp = client.TextToVoice(req);
        String questionAudio = base64Decode(resp.getAudio(), gradePreschool.getPreschoolId());

        return questionAudio;
    }

}
