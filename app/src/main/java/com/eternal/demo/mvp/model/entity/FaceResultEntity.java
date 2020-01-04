package com.eternal.demo.mvp.model.entity;

import java.io.Serializable;
import java.util.List;

public class FaceResultEntity implements Serializable {

    /**
     * error_code : 0.0
     * error_msg : SUCCESS
     * log_id : 4.589157579157E12
     * timestamp : 1.577939994E9
     * cached : 0.0
     * result : {"face_num":1,"face_list":[{"face_token":"f288be63de24eaee695063071dbffcb9","location":{"left":317.58,"top":794.9,"width":833,"height":780,"rotation":-3},"face_probability":1,"angle":{"yaw":-18.4,"pitch":15.82,"roll":0.77},"age":28,"beauty":41.95,"expression":{"type":"smile","probability":1},"face_shape":{"type":"oval","probability":0.63},"gender":{"type":"female","probability":1},"glasses":{"type":"none","probability":1},"race":{"type":"white","probability":0.97},"quality":{"occlusion":{"left_eye":0,"right_eye":0.01,"nose":0.02,"mouth":0.01,"left_cheek":0.23,"right_cheek":0.48,"chin_contour":0.17},"blur":0,"illumination":190,"completeness":1},"eye_status":{"left_eye":1,"right_eye":1},"emotion":{"type":"happy","probability":1},"face_type":{"type":"human","probability":1}}]}
     */

    private double error_code;
    private String error_msg;
    private double log_id;
    private double timestamp;
    private double cached;
    private ResultBean result;

    public double getError_code() {
        return error_code;
    }

    public void setError_code(double error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public double getLog_id() {
        return log_id;
    }

    public void setLog_id(double log_id) {
        this.log_id = log_id;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }

    public double getCached() {
        return cached;
    }

    public void setCached(double cached) {
        this.cached = cached;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * face_num : 1.0
         * face_list : [{"face_token":"f288be63de24eaee695063071dbffcb9","location":{"left":317.58,"top":794.9,"width":833,"height":780,"rotation":-3},"face_probability":1,"angle":{"yaw":-18.4,"pitch":15.82,"roll":0.77},"age":28,"beauty":41.95,"expression":{"type":"smile","probability":1},"face_shape":{"type":"oval","probability":0.63},"gender":{"type":"female","probability":1},"glasses":{"type":"none","probability":1},"race":{"type":"white","probability":0.97},"quality":{"occlusion":{"left_eye":0,"right_eye":0.01,"nose":0.02,"mouth":0.01,"left_cheek":0.23,"right_cheek":0.48,"chin_contour":0.17},"blur":0,"illumination":190,"completeness":1},"eye_status":{"left_eye":1,"right_eye":1},"emotion":{"type":"happy","probability":1},"face_type":{"type":"human","probability":1}}]
         */

        private double face_num;
        private List<FaceListBean> face_list;

        public double getFace_num() {
            return face_num;
        }

        public void setFace_num(double face_num) {
            this.face_num = face_num;
        }

        public List<FaceListBean> getFace_list() {
            return face_list;
        }

        public void setFace_list(List<FaceListBean> face_list) {
            this.face_list = face_list;
        }

        public static class FaceListBean implements Serializable {
            /**
             * face_token : f288be63de24eaee695063071dbffcb9
             * location : {"left":317.58,"top":794.9,"width":833,"height":780,"rotation":-3}
             * face_probability : 1.0
             * angle : {"yaw":-18.4,"pitch":15.82,"roll":0.77}
             * age : 28.0
             * beauty : 41.95
             * expression : {"type":"smile","probability":1}
             * face_shape : {"type":"oval","probability":0.63}
             * gender : {"type":"female","probability":1}
             * glasses : {"type":"none","probability":1}
             * race : {"type":"white","probability":0.97}
             * quality : {"occlusion":{"left_eye":0,"right_eye":0.01,"nose":0.02,"mouth":0.01,"left_cheek":0.23,"right_cheek":0.48,"chin_contour":0.17},"blur":0,"illumination":190,"completeness":1}
             * eye_status : {"left_eye":1,"right_eye":1}
             * emotion : {"type":"happy","probability":1}
             * face_type : {"type":"human","probability":1}
             */

            private String face_token;
            private LocationBean location;
            private double face_probability;
            private AngleBean angle;
            private double age;
            private double beauty;
            private ExpressionBean expression;
            private FaceShapeBean face_shape;
            private GenderBean gender;
            private GlassesBean glasses;
            private RaceBean race;
            private QualityBean quality;
            private EyeStatusBean eye_status;
            private EmotionBean emotion;
            private FaceTypeBean face_type;

            public String getFace_token() {
                return face_token;
            }

            public void setFace_token(String face_token) {
                this.face_token = face_token;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public double getFace_probability() {
                return face_probability;
            }

            public void setFace_probability(double face_probability) {
                this.face_probability = face_probability;
            }

            public AngleBean getAngle() {
                return angle;
            }

            public void setAngle(AngleBean angle) {
                this.angle = angle;
            }

            public double getAge() {
                return age;
            }

            public void setAge(double age) {
                this.age = age;
            }

            public double getBeauty() {
                return beauty;
            }

            public void setBeauty(double beauty) {
                this.beauty = beauty;
            }

            public ExpressionBean getExpression() {
                return expression;
            }

            public void setExpression(ExpressionBean expression) {
                this.expression = expression;
            }

            public FaceShapeBean getFace_shape() {
                return face_shape;
            }

            public void setFace_shape(FaceShapeBean face_shape) {
                this.face_shape = face_shape;
            }

            public GenderBean getGender() {
                return gender;
            }

            public void setGender(GenderBean gender) {
                this.gender = gender;
            }

            public GlassesBean getGlasses() {
                return glasses;
            }

            public void setGlasses(GlassesBean glasses) {
                this.glasses = glasses;
            }

            public RaceBean getRace() {
                return race;
            }

            public void setRace(RaceBean race) {
                this.race = race;
            }

            public QualityBean getQuality() {
                return quality;
            }

            public void setQuality(QualityBean quality) {
                this.quality = quality;
            }

            public EyeStatusBean getEye_status() {
                return eye_status;
            }

            public void setEye_status(EyeStatusBean eye_status) {
                this.eye_status = eye_status;
            }

            public EmotionBean getEmotion() {
                return emotion;
            }

            public void setEmotion(EmotionBean emotion) {
                this.emotion = emotion;
            }

            public FaceTypeBean getFace_type() {
                return face_type;
            }

            public void setFace_type(FaceTypeBean face_type) {
                this.face_type = face_type;
            }

            public static class LocationBean implements Serializable{
                /**
                 * left : 317.58
                 * top : 794.9
                 * width : 833.0
                 * height : 780.0
                 * rotation : -3.0
                 */

                private double left;
                private double top;
                private double width;
                private double height;
                private double rotation;

                public double getLeft() {
                    return left;
                }

                public void setLeft(double left) {
                    this.left = left;
                }

                public double getTop() {
                    return top;
                }

                public void setTop(double top) {
                    this.top = top;
                }

                public double getWidth() {
                    return width;
                }

                public void setWidth(double width) {
                    this.width = width;
                }

                public double getHeight() {
                    return height;
                }

                public void setHeight(double height) {
                    this.height = height;
                }

                public double getRotation() {
                    return rotation;
                }

                public void setRotation(double rotation) {
                    this.rotation = rotation;
                }
            }

            public static class AngleBean implements Serializable {
                /**
                 * yaw : -18.4
                 * pitch : 15.82
                 * roll : 0.77
                 */

                private double yaw;
                private double pitch;
                private double roll;

                public double getYaw() {
                    return yaw;
                }

                public void setYaw(double yaw) {
                    this.yaw = yaw;
                }

                public double getPitch() {
                    return pitch;
                }

                public void setPitch(double pitch) {
                    this.pitch = pitch;
                }

                public double getRoll() {
                    return roll;
                }

                public void setRoll(double roll) {
                    this.roll = roll;
                }
            }

            public static class ExpressionBean implements Serializable{
                /**
                 * type : smile
                 * probability : 1.0
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class FaceShapeBean implements Serializable{
                /**
                 * type : oval
                 * probability : 0.63
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class GenderBean implements Serializable{
                /**
                 * type : female
                 * probability : 1.0
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class GlassesBean implements Serializable{
                /**
                 * type : none
                 * probability : 1.0
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class RaceBean implements Serializable{
                /**
                 * type : white
                 * probability : 0.97
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class QualityBean implements Serializable{
                /**
                 * occlusion : {"left_eye":0,"right_eye":0.01,"nose":0.02,"mouth":0.01,"left_cheek":0.23,"right_cheek":0.48,"chin_contour":0.17}
                 * blur : 0.0
                 * illumination : 190.0
                 * completeness : 1.0
                 */

                private OcclusionBean occlusion;
                private double blur;
                private double illumination;
                private double completeness;

                public OcclusionBean getOcclusion() {
                    return occlusion;
                }

                public void setOcclusion(OcclusionBean occlusion) {
                    this.occlusion = occlusion;
                }

                public double getBlur() {
                    return blur;
                }

                public void setBlur(double blur) {
                    this.blur = blur;
                }

                public double getIllumination() {
                    return illumination;
                }

                public void setIllumination(double illumination) {
                    this.illumination = illumination;
                }

                public double getCompleteness() {
                    return completeness;
                }

                public void setCompleteness(double completeness) {
                    this.completeness = completeness;
                }

                public static class OcclusionBean implements Serializable{
                    /**
                     * left_eye : 0.0
                     * right_eye : 0.01
                     * nose : 0.02
                     * mouth : 0.01
                     * left_cheek : 0.23
                     * right_cheek : 0.48
                     * chin_contour : 0.17
                     */

                    private double left_eye;
                    private double right_eye;
                    private double nose;
                    private double mouth;
                    private double left_cheek;
                    private double right_cheek;
                    private double chin_contour;

                    public double getLeft_eye() {
                        return left_eye;
                    }

                    public void setLeft_eye(double left_eye) {
                        this.left_eye = left_eye;
                    }

                    public double getRight_eye() {
                        return right_eye;
                    }

                    public void setRight_eye(double right_eye) {
                        this.right_eye = right_eye;
                    }

                    public double getNose() {
                        return nose;
                    }

                    public void setNose(double nose) {
                        this.nose = nose;
                    }

                    public double getMouth() {
                        return mouth;
                    }

                    public void setMouth(double mouth) {
                        this.mouth = mouth;
                    }

                    public double getLeft_cheek() {
                        return left_cheek;
                    }

                    public void setLeft_cheek(double left_cheek) {
                        this.left_cheek = left_cheek;
                    }

                    public double getRight_cheek() {
                        return right_cheek;
                    }

                    public void setRight_cheek(double right_cheek) {
                        this.right_cheek = right_cheek;
                    }

                    public double getChin_contour() {
                        return chin_contour;
                    }

                    public void setChin_contour(double chin_contour) {
                        this.chin_contour = chin_contour;
                    }
                }
            }

            public static class EyeStatusBean implements Serializable{
                /**
                 * left_eye : 1.0
                 * right_eye : 1.0
                 */

                private double left_eye;
                private double right_eye;

                public double getLeft_eye() {
                    return left_eye;
                }

                public void setLeft_eye(double left_eye) {
                    this.left_eye = left_eye;
                }

                public double getRight_eye() {
                    return right_eye;
                }

                public void setRight_eye(double right_eye) {
                    this.right_eye = right_eye;
                }
            }

            public static class EmotionBean implements Serializable{
                /**
                 * type : happy
                 * probability : 1.0
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }

            public static class FaceTypeBean implements Serializable{
                /**
                 * type : human
                 * probability : 1.0
                 */

                private String type;
                private double probability;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }
            }
        }
    }
}
