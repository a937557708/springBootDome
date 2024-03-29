package com.tjr.test;

public class Test {
    public static void main(String[] args) {
        String str="A371";
        System.out.println(str.matches("(A|W|S|D)\\d{0,2}"));

//        String line="A37;S1;S72;S41;W21;W32;A45;A98;D97;D69;W57;W11;S41;S0;S24;S83;A75;W77;S32;D18;A24;D20;A65;D95;S18;W56;A84;W30;S3;S50;D64;S84;D82;A86;A36;S85;S94;W64;W62;A12;W12;S84;W29;A52;A73;W55;W71;D43;D76;D49;W19;S56;W63;W56;A68;D68;D40;A48;W5;A12;S84;A2;S44;A93;D51;D64;S4;W32;W27;W15;W70;A47;S77;S88;A25;D51;D8;A34;A71;A5;D83;S36;S72;A34;D46;S29;S5;W57;W16;S42;A23;A30;D43;S0;W62;A34;D60;D31;W89;W91;S87;A15;S15;S18;W83;S82;W87;W73;D42;A92;D48;A65;D36;A11;W50;W38;W2;A0;D65;W29;D56;S64;D31;W8;A56;A45;A56;W54;A97;D97;A90;S72;A95;A89;S78;A35;A31;W68;W42;A73;S73;A24;S28;D69;W53;S54;D80;D27;W24;S86;A17;A36;A41;A1;D19;S53;S96;A31;A52;A63;A18;S54;A35;A82;W95;D8;W48;S75;W11;S9;W60;D68;A92;A96;W32;S30;D26;W61;S0;D10;S89;W31;D4;W37;S49;D79;S56;A87;S61;A61;D96;W86;S81;D50;S91;A68;A82;A36;A16;S6;W25;D76;D94;A20;A37;D91;S58;A54;S77;S27;A35;S6;A88;A14;S72;D12;A95;W93;W1;A73;A55;A13;S55;W43;W6;D37;W19;W79;W56;S26;A36;W85;A6;W94;A54;A12;S0;A13;D18;W14;A52;W44;D83;W17;W73;A72;D56;A63;S14;A64;A84;S54;D67;A92;D46;A51;D99;W42;W2;D22;S14;D96;A79;A41;S65;D8;S41;D30;S61;D77;A52;W2;S11;W26;D47;A65;S11;D2;W98;A30;D36;W26;S41;S71;D65;D76;D28;D25;S24;S24;S58;W13;D80;A58;S64;S52;W54;S51;S40;W20;D54;W13;A7;D37;D93;A74;D24;S0;A5;W52;D86;D22;W22;S13;D56;S99;D67;S34;S5;D47;D87;D41;S15;S66;W10;D55;A63;S57;W12;S96;W28;W96;S33;D70;D75;S20;W76;D83;A34;W29;S24;A7;W11;W81;S85;W1;W71;W73;D20;A62;S2;W43;S22;D18;W35;A15;S72;W12;S12;D93;S37;S55;D33;D52;S85;W97;A88;W47;A17;A50;A40;D9;A0;D13;A16;S87;S61;D12;D8;D69;S59;S71;A7;W99;D81;W6;D48;A92;D23;D47;W37;W30;A5;S96;D28;A13;D53;D17;D9;D58;S78;S26;W17;D88;A11;W87;W94;D45;D60;W52;S71;S71;D35;D60;W60;D73;A42;D57;A25;W87;A4;W74;W70;A50;S22;S63;W15;A62;S70;A15;W58;D19;D56;A80;S95;S57;D75;A16;S8;D88;S33;S94;A43;D97;S11;D53;W55;S52;W59;S92;D39;D40;W34;W33;D52;D19;S87;A21;D94;W5;W42;A50;D27;A29;D47;W12;D5;S70;D47;S16;W52;W30;D2;A48;S1;D68;W82;A47;S84;S56;S60;S14;W64;W8;A41;W8;S4;D80;A49;D72;";
//
//        String[] strArr=line.split(";");
//        int x=0,y=0;
//        for(String str:strArr){
//            if(str.matches("(A|S|W|D)\\d[0-2]")){
//                String ms=str.substring(0,1);
//                int num=Integer.parseInt(str.substring(1));
//                if("A".equals(ms)){
//                    x-=num;
//                }else if("D".equals(ms)){
//                    x+=num;
//                }else if("W".equals(ms)){
//                    y+=num;
//                }else if("S".equals(ms)){
//                    y-=num;
//                }
//
//            }
//        }
//        System.out.println(x+","+y);
        System.out.println(ipToNum("255.255.255.32"));
        long ip=31l;
        System.out.println(Long.toBinaryString(ip));
        System.out.println(Long.toBinaryString((((ip ^ 0XFFL) + 1) )));
        System.out.println(Long.toBinaryString((((ip ^ 0XFFL) + 1) | ip)));
        System.out.println((((ip ^ 0XFFFFFFFFL) + 1) | ip)!= ip);
    }

    public static long ipToNum(String ip) {
        long num = 0;
        int flag = 1, temp = 0;
        char[] chars = ip.toCharArray();
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] == '.') {
                num = num << 8 | temp;
                temp = 0;
                flag ++;
                continue;
            }
            if(flag >= 2) {
                return -1;
            }
            if(chars[i] >= '0' && chars[i] <= '9') {
                temp = temp * 10 + chars[i] - '0';
                flag = 0;
            }
            else {
                flag = 3;
                break;
            }
        }
        if(flag >= 2) {
            return -1;
        }
        num = num << 8 | temp;
        return num;
    }
}
