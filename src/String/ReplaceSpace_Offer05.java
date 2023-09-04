package String;

public class ReplaceSpace_Offer05 { //剑指 Offer 05. 替换空格
    public String replaceSpace(String s) {
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            if (aChar == ' ') {
                stringBuilder.append("%20");
            }else {
                stringBuilder.append(aChar);
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println((new ReplaceSpace_Offer05()).replaceSpace("We are happy."));
    }
}
