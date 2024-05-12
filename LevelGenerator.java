import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;


/**
The LevelGenerator Class handles the level design of each level
seen in the game. It also draws <Code> Walls </code> that acts as
the border of each map.

@author Anthony B. Deocadiz Jr. (232166)
@author Ramona Miekaela S. Laciste (233403)
@version May 11, 2024
**/

/*
We have not discussed the Java language code in our program
with anyone other than my instructor or the teaching assistants
assigned to this course.

We have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.

If any Java language code or documentation used in our program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of our program.
*/


public class LevelGenerator implements BackgroundManager {

    private BufferedImage level1,level2,level3,level4,level5;
    private ArrayList<Walls> levelWalls;

    /** 
        Instantiates a LevelGenerator Object that will also get the images
        of each level.
    **/
    public LevelGenerator(){

        levelWalls = new ArrayList<>();

        try {
            
            level1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/LevelDesign/My_Melody.png"));
            level2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/LevelDesign/Hello_Kitty.png"));
            level3 = ImageIO.read(getClass().getResourceAsStream("/Sprites/LevelDesign/Pompompurin.png"));
            level4 = ImageIO.read(getClass().getResourceAsStream("/Sprites/LevelDesign/Keroppi.png"));
            level5 = ImageIO.read(getClass().getResourceAsStream("/Sprites/LevelDesign/Badtz_Maru.png"));

        } catch (IOException e) {
            System.out.println("IOEXception in LevelGenerator()");
        }

        // collision / walls for objects per level

        // Walls for Level 1 -- My_Melody | My Melody's Music Store

        Walls w1 = new Walls(369, 1404, 105, 71);
        Walls w2 = new Walls(616, 1406, 145, 84);
        Walls w3 = new Walls(413, 1551, 26, 37);
        Walls w4 = new Walls(386, 1590, 37, 117);
        Walls w5 = new Walls(403, 1711, 23, 30);
        Walls w6 = new Walls(506, 1619, 55, 91);
        Walls w7 = new Walls(644, 1615, 6, 68);
        Walls w8 = new Walls(656, 1647, 34, 39);
        Walls w9 = new Walls(942, 1404, 107, 89);
        Walls w10 = new Walls(1351, 1405, 119, 110);
        Walls w11 = new Walls(1303, 1536, 31, 89);
        Walls w12 = new Walls(1282, 1557, 16, 60);
        Walls w13 = new Walls(1261, 1569, 18, 47);
        Walls w14 = new Walls(1437, 1637, 33, 106);
        Walls w15 = new Walls(1406, 1815, 40, 62);
        Walls w16 = new Walls(1366, 1936, 58, 51);
        Walls w17 = new Walls(1438, 1896, 35, 49);
        Walls w18 = new Walls(843, 1725, 16, 12);
        Walls w19 = new Walls(901, 1701, 20, 11);
        Walls w20 = new Walls(964, 1691, 21, 12);
        Walls w21 = new Walls(1024, 1723, 25, 9);
        Walls w22 = new Walls(839, 1778, 22, 61);
        Walls w23 = new Walls(871, 1770, 142, 92);
        Walls w24 = new Walls(1026, 1776, 22, 61);
        Walls w25 = new Walls(381, 2118, 31, 74);
        Walls w26 = new Walls(484, 2169, 36, 32);
        Walls w27 = new Walls(483, 2089, 70, 42);
        Walls w28 = new Walls(527, 2131, 25, 25);
        Walls w29 = new Walls(576, 2132, 25, 33);
        Walls w30 = new Walls(844, 2084, 22, 54);
        Walls w31 = new Walls(876, 2044, 89, 58);
        Walls w32 = new Walls(958, 2029, 14, 19);
        Walls w33 = new Walls(979, 2089, 32, 43);
        Walls w34 = new Walls(1239, 2133, 136, 59);
        Walls w35 = new Walls(1401, 2118, 65, 87);

        levelWalls.add(w1);
        levelWalls.add(w2);
        levelWalls.add(w3);
        levelWalls.add(w4);
        levelWalls.add(w5);
        levelWalls.add(w6);
        levelWalls.add(w7);
        levelWalls.add(w8);
        levelWalls.add(w9);
        levelWalls.add(w10);
        levelWalls.add(w11);
        levelWalls.add(w12);
        levelWalls.add(w13);
        levelWalls.add(w14);
        levelWalls.add(w15);
        levelWalls.add(w16);
        levelWalls.add(w17);
        levelWalls.add(w18);
        levelWalls.add(w19);
        levelWalls.add(w20);
        levelWalls.add(w21);
        levelWalls.add(w22);
        levelWalls.add(w23);
        levelWalls.add(w24);
        levelWalls.add(w25);
        levelWalls.add(w26);
        levelWalls.add(w27);
        levelWalls.add(w28);
        levelWalls.add(w29);
        levelWalls.add(w30);
        levelWalls.add(w31);
        levelWalls.add(w32);
        levelWalls.add(w33);
        levelWalls.add(w34);
        levelWalls.add(w35);

        // Walls for Level 2 -- Hello_Kitty | Hello Kitty Cafe

        Walls w36 = new Walls(2740, 1437, 67, 56);
        Walls w37 = new Walls(2738, 1521, 29, 52);
        Walls w38 = new Walls(2803, 1499, 42, 26);
        Walls w39 = new Walls(2803, 1529, 70, 65);
        Walls w40 = new Walls(2906, 1427, 27, 57);
        Walls w41 = new Walls(2952, 1422, 128, 63);
        Walls w42 = new Walls(3093, 1427, 24, 59);
        Walls w43 = new Walls(3166, 1419, 91, 97);
        Walls w44 = new Walls(3265, 1419, 32, 61);
        Walls w45 = new Walls(3307, 1433, 182, 42);
        Walls w46 = new Walls(3096, 1690, 63, 24);
        Walls w47 = new Walls(3116, 1670, 43, 12);
        Walls w48 = new Walls(3128, 1655, 31, 9);
        Walls w49 = new Walls(3164, 1646, 51, 38);
        Walls w50 = new Walls(3189, 1634, 26, 5);
        Walls w51 = new Walls(3216, 1627, 71, 49);
        Walls w52 = new Walls(3288, 1633, 55, 41);
        Walls w53 = new Walls(3348, 1638, 31, 37);
        Walls w54 = new Walls(3328, 1678, 51, 10);
        Walls w55 = new Walls(3368, 1692, 15, 10);
        Walls w56 = new Walls(3382, 1652, 19, 56);
        Walls w57 = new Walls(3405, 1668, 27, 57);
        Walls w58 = new Walls(3436, 1689, 15, 31);
        Walls w59 = new Walls(3513, 1433, 25, 55);
        Walls w60 = new Walls(3595, 1435, 61, 44);
        Walls w61 = new Walls(3650, 1498, 71, 58);
        Walls w62 = new Walls(3770, 1411, 59, 116);
        Walls w63 = new Walls(3754, 1567, 60, 84);
        Walls w64 = new Walls(3688, 1999, 42, 55);
        Walls w65 = new Walls(3752, 2047, 62, 45);
        Walls w66 = new Walls(3608, 2044, 56, 50);
        Walls w67 = new Walls(3543, 2131, 64, 56);
        Walls w68 = new Walls(3654, 2127, 164, 84);
        Walls w69 = new Walls(3451, 1854, 15, 56);
        Walls w70 = new Walls(3429, 1859, 18, 65);
        Walls w71 = new Walls(3407, 1864, 16, 69);
        Walls w72 = new Walls(3386, 1882, 18, 59);
        Walls w73 = new Walls(3355, 1894, 24, 65);
        Walls w74 = new Walls(3323, 1907, 25, 56);
        Walls w75 = new Walls(3245, 1915, 72, 55);
        Walls w76 = new Walls(3209, 1909, 29, 57);
        Walls w77 = new Walls(3175, 1899, 31, 61);
        Walls w78 = new Walls(3155, 1882, 16, 62);
        Walls w79 = new Walls(3139, 1868, 16, 68);
        Walls w80 = new Walls(3111, 1858, 20, 67);
        Walls w81 = new Walls(3095, 1900, 16, 14);
        Walls w82 = new Walls(3079, 1852, 33, 44);
        Walls w83 = new Walls(3348, 2137, 55, 59);
        Walls w84 = new Walls(3194, 2110, 45, 98);
        Walls w85 = new Walls(3242, 2148, 18, 63);
        Walls w86 = new Walls(2828, 1937, 65, 52);
        Walls w87 = new Walls(2738, 1958, 60, 59);
        Walls w88 = new Walls(2803, 2026, 70, 60);
        Walls w89 = new Walls(2749, 2123, 40, 64);
        Walls w90 = new Walls(2850, 2120, 254, 83);
        Walls w91 = new Walls(2893, 2095, 31, 20);
        Walls w92 = new Walls(3046, 2092, 21, 23);

        levelWalls.add(w36);
        levelWalls.add(w37);
        levelWalls.add(w38);
        levelWalls.add(w39);
        levelWalls.add(w40);
        levelWalls.add(w41);
        levelWalls.add(w42);
        levelWalls.add(w43);
        levelWalls.add(w44);
        levelWalls.add(w45);
        levelWalls.add(w46);
        levelWalls.add(w47);
        levelWalls.add(w48);
        levelWalls.add(w49);
        levelWalls.add(w50);
        levelWalls.add(w51);
        levelWalls.add(w52);
        levelWalls.add(w53);
        levelWalls.add(w54);
        levelWalls.add(w55);
        levelWalls.add(w56);
        levelWalls.add(w57);
        levelWalls.add(w58);
        levelWalls.add(w59);
        levelWalls.add(w60);
        levelWalls.add(w61);
        levelWalls.add(w62);
        levelWalls.add(w63);
        levelWalls.add(w64);
        levelWalls.add(w65);
        levelWalls.add(w66);
        levelWalls.add(w67);
        levelWalls.add(w68);
        levelWalls.add(w69);
        levelWalls.add(w70);
        levelWalls.add(w71);
        levelWalls.add(w72);
        levelWalls.add(w73);
        levelWalls.add(w74);
        levelWalls.add(w75);
        levelWalls.add(w76);
        levelWalls.add(w77);
        levelWalls.add(w78);
        levelWalls.add(w79);
        levelWalls.add(w80);
        levelWalls.add(w81);
        levelWalls.add(w82);
        levelWalls.add(w83);
        levelWalls.add(w84);
        levelWalls.add(w85);
        levelWalls.add(w86);
        levelWalls.add(w87);
        levelWalls.add(w88);
        levelWalls.add(w89);
        levelWalls.add(w90);
        levelWalls.add(w91);
        levelWalls.add(w92);

        // Walls for level 3 -- Pompompurin | Pompompurin

        Walls w93 = new Walls(5088, 1449, 76, 79);
        Walls w94 = new Walls(5184, 1405, 318, 96);
        Walls w95 = new Walls(5502, 1405, 43, 75);
        Walls w96 = new Walls(5714, 1403, 50, 75);
        Walls w97 = new Walls(5767, 1405, 385, 97);
        Walls w98 = new Walls(6059, 1511, 25, 46);
        Walls w99 = new Walls(6032, 1541, 24, 38);
        Walls w100 = new Walls(6060, 1559, 83, 137);
        Walls w101 = new Walls(5999, 1639, 23, 36);
        Walls w102 = new Walls(6022, 1675, 18, 40);
        Walls w103 = new Walls(5102, 1672, 136, 117);
        Walls w104 = new Walls(5515, 1683, 35, 142);
        Walls w105 = new Walls(5573, 1647, 94, 201);
        Walls w106 = new Walls(5686, 1685, 36, 144);
        Walls w107 = new Walls(5534, 1878, 172, 70);
        Walls w108 = new Walls(5109, 1966, 101, 224);
        Walls w109 = new Walls(6090, 1841, 51, 125);
        Walls w110 = new Walls(5338, 2065, 34, 76);
        Walls w111 = new Walls(5374, 2087, 74, 95);
        Walls w112 = new Walls(5451, 2107, 72, 35);
        Walls w113 = new Walls(5526, 2075, 189, 69);
        Walls w114 = new Walls(5525, 2146, 30, 43);
        Walls w115 = new Walls(5583, 2147, 56, 45);
        Walls w116 = new Walls(5683, 2144, 28, 52);
        Walls w117 = new Walls(5792, 2087, 74, 88);
        Walls w118 = new Walls(5869, 2065, 30, 80);
        Walls w119 = new Walls(5985, 2098, 61, 91);
        Walls w120 = new Walls(6075, 2101, 70, 81);

        levelWalls.add(w93);
        levelWalls.add(w94);
        levelWalls.add(w95);
        levelWalls.add(w96);
        levelWalls.add(w97);
        levelWalls.add(w98);
        levelWalls.add(w99);
        levelWalls.add(w100);
        levelWalls.add(w101);
        levelWalls.add(w102);
        levelWalls.add(w103);
        levelWalls.add(w104);
        levelWalls.add(w105);
        levelWalls.add(w106);
        levelWalls.add(w107);
        levelWalls.add(w108);
        levelWalls.add(w109);
        levelWalls.add(w110);
        levelWalls.add(w111);
        levelWalls.add(w112);
        levelWalls.add(w113);
        levelWalls.add(w114);
        levelWalls.add(w115);
        levelWalls.add(w116);
        levelWalls.add(w117);
        levelWalls.add(w118);
        levelWalls.add(w119);
        levelWalls.add(w120);

        // Walls for level 4 -- Keroppi | Keroppi's pond

        Walls w121 = new Walls(7416, 1403, 99, 234);
        Walls w122 = new Walls(7517, 1405, 189, 98);
        Walls w123 = new Walls(7524, 1539, 56, 61);
        Walls w124 = new Walls(7708, 1403, 510, 50);
        Walls w125 = new Walls(8219, 1400, 38, 75);
        Walls w126 = new Walls(8262, 1398, 38, 95);
        Walls w127 = new Walls(8302, 1400, 40, 112);
        Walls w128 = new Walls(8341, 1400, 84, 139);
        Walls w129 = new Walls(8426, 1400, 104, 234);
        Walls w130 = new Walls(8387, 1547, 35, 77);
        Walls w131 = new Walls(8322, 1558, 55, 65);
        Walls w132 = new Walls(8265, 1520, 52, 66);
        Walls w133 = new Walls(8431, 1746, 93, 95);
        Walls w134 = new Walls(8147, 1671, 53, 146);
        Walls w135 = new Walls(8144, 1817, 33, 114);
        Walls w136 = new Walls(8054, 1639, 95, 177);
        Walls w137 = new Walls(8053, 1816, 85, 129);
        Walls w138 = new Walls(8053, 1945, 56, 33);
        Walls w139 = new Walls(8012, 1681, 35, 33);
        Walls w140 = new Walls(7914, 1705, 139, 276);
        Walls w141 = new Walls(7857, 1716, 49, 261);
        Walls w142 = new Walls(7819, 1741, 39, 200);
        Walls w143 = new Walls(7799, 1758, 14, 175);
        Walls w144 = new Walls(7777, 1774, 16, 111);
        Walls w145 = new Walls(7764, 1791, 8, 64);
        Walls w146 = new Walls(7415, 1983, 95, 228);
        Walls w147 = new Walls(7517, 2053, 27, 92);
        Walls w148 = new Walls(7513, 2145, 163, 65);
        Walls w149 = new Walls(7636, 2113, 136, 31);
        Walls w150 = new Walls(7649, 2078, 109, 26);
        Walls w151 = new Walls(7677, 2060, 52, 12);
        Walls w152 = new Walls(7683, 2150, 88, 65);
        Walls w153 = new Walls(7776, 2165, 120, 46);
        Walls w154 = new Walls(7896, 2169, 165, 46);
        Walls w155 = new Walls(7898, 2132, 149, 28);
        Walls w156 = new Walls(8102, 2203, 276, 12);
        Walls w157 = new Walls(8332, 2087, 26, 40);
        Walls w158 = new Walls(8359, 2060, 17, 122);
        Walls w159 = new Walls(8386, 2038, 14, 175);
        Walls w160 = new Walls(8406, 2018, 20, 197);
        Walls w161 = new Walls(8433, 1951, 102, 263);

        levelWalls.add(w121);
        levelWalls.add(w122);
        levelWalls.add(w123);
        levelWalls.add(w124);
        levelWalls.add(w125);
        levelWalls.add(w126);
        levelWalls.add(w127);
        levelWalls.add(w128);
        levelWalls.add(w129);
        levelWalls.add(w130);
        levelWalls.add(w131);
        levelWalls.add(w132);
        levelWalls.add(w133);
        levelWalls.add(w134);
        levelWalls.add(w135);
        levelWalls.add(w136);
        levelWalls.add(w137);
        levelWalls.add(w138);
        levelWalls.add(w139);
        levelWalls.add(w140);
        levelWalls.add(w141);
        levelWalls.add(w142);
        levelWalls.add(w143);
        levelWalls.add(w144);
        levelWalls.add(w145);
        levelWalls.add(w146);
        levelWalls.add(w147);
        levelWalls.add(w148);
        levelWalls.add(w149);
        levelWalls.add(w150);
        levelWalls.add(w151);
        levelWalls.add(w152);
        levelWalls.add(w153);
        levelWalls.add(w154);
        levelWalls.add(w155);
        levelWalls.add(w156);
        levelWalls.add(w157);
        levelWalls.add(w158);
        levelWalls.add(w159);
        levelWalls.add(w160);
        levelWalls.add(w161);

        // Walls for level 5 -- Badtz_Maru | Badtz Maru Skatepark 

        Walls w162 = new Walls(9818, 1424, 82, 63);
        Walls w163 = new Walls(9768, 1522, 26, 32);
        Walls w164 = new Walls(9765, 1600, 24, 32);
        Walls w165 = new Walls(9827, 1553, 80, 47);
        Walls w166 = new Walls(10110, 1520, 57, 21);
        Walls w167 = new Walls(10084, 1542, 52, 25);
        Walls w168 = new Walls(10178, 1544, 21, 32);
        Walls w169 = new Walls(10218, 1541, 81, 27);
        Walls w170 = new Walls(10371, 1539, 78, 28);
        Walls w171 = new Walls(10467, 1539, 23, 33);
        Walls w172 = new Walls(10511, 1553, 33, 16);
        Walls w173 = new Walls(10539, 1573, 20, 13);
        Walls w174 = new Walls(10740, 1414, 132, 40);
        Walls w175 = new Walls(9798, 1729, 55, 88);
        Walls w176 = new Walls(9910, 1840, 27, 13);
        Walls w177 = new Walls(9895, 1856, 34, 26);
        Walls w178 = new Walls(9774, 1979, 36, 29);
        Walls w179 = new Walls(9810, 1995, 20, 23);
        Walls w180 = new Walls(9906, 2173, 25, 34);
        Walls w181 = new Walls(10090, 2027, 29, 21);
        Walls w182 = new Walls(10073, 2048, 37, 20);
        Walls w183 = new Walls(10118, 2062, 18, 23);
        Walls w184 = new Walls(10142, 2080, 16, 19);
        Walls w185 = new Walls(10200, 1775, 18, 21);
        Walls w186 = new Walls(10220, 1787, 18, 27);
        Walls w187 = new Walls(10390, 1956, 30, 27);
        Walls w188 = new Walls(10423, 1948, 31, 27);
        Walls w189 = new Walls(10449, 1908, 21, 15);
        Walls w190 = new Walls(10475, 1887, 14, 27);
        Walls w191 = new Walls(10747, 1818, 20, 21);
        Walls w192 = new Walls(10762, 1835, 28, 18);
        Walls w193 = new Walls(10803, 1768, 48, 53);
        Walls w194 = new Walls(10816, 1844, 37, 21);
        Walls w195 = new Walls(10837, 1868, 22, 14);
        Walls w196 = new Walls(10805, 2067, 80, 29);
        Walls w197 = new Walls(10768, 2112, 110, 21);
        Walls w198 = new Walls(10719, 2143, 159, 61);

        levelWalls.add(w162);
        levelWalls.add(w163);
        levelWalls.add(w164);
        levelWalls.add(w165);
        levelWalls.add(w166);
        levelWalls.add(w167);
        levelWalls.add(w168);
        levelWalls.add(w169);
        levelWalls.add(w170);
        levelWalls.add(w171);
        levelWalls.add(w172);
        levelWalls.add(w173);
        levelWalls.add(w174);
        levelWalls.add(w175);
        levelWalls.add(w176);
        levelWalls.add(w177);
        levelWalls.add(w178);
        levelWalls.add(w179);
        levelWalls.add(w180);
        levelWalls.add(w181);
        levelWalls.add(w182);
        levelWalls.add(w183);
        levelWalls.add(w184);
        levelWalls.add(w185);
        levelWalls.add(w186);
        levelWalls.add(w187);
        levelWalls.add(w188);
        levelWalls.add(w189);
        levelWalls.add(w190);
        levelWalls.add(w191);
        levelWalls.add(w192);
        levelWalls.add(w193);
        levelWalls.add(w194);
        levelWalls.add(w195);
        levelWalls.add(w196);
        levelWalls.add(w197);
        levelWalls.add(w198);

        // outer walls for each level 

        // Level 1
        Walls w199 = new Walls(345, 1380, 20, 865);
        Walls w200 = new Walls(345, 1380, 1160, 20);
        Walls w201 = new Walls(1485, 1380, 20, 865);
        Walls w202 = new Walls(345, 2220, 1160, 20);

        levelWalls.add(w199);
        levelWalls.add(w200);
        levelWalls.add(w201);
        levelWalls.add(w202);

        // Level 2

        Walls w203 = new Walls(2695, 1380, 20, 865);
        Walls w204 = new Walls(2695, 1380, 1160, 20);
        Walls w205 = new Walls(3835, 1380, 20, 865);
        Walls w206 = new Walls(2695, 2220, 1160, 20);

        levelWalls.add(w203);
        levelWalls.add(w204);
        levelWalls.add(w205);
        levelWalls.add(w206);

        // Level 3

        Walls w207 = new Walls(5045, 1380, 20, 865);
        Walls w208 = new Walls(5045, 1380, 1160, 20);
        Walls w209 = new Walls(6185, 1380, 20, 865);
        Walls w210 = new Walls(5045, 2220, 1160, 20);

        levelWalls.add(w207);
        levelWalls.add(w208);
        levelWalls.add(w209);
        levelWalls.add(w210);

        // Level 4

        Walls w211 = new Walls(7395, 1380, 20, 865);
        Walls w212 = new Walls(7395, 1380, 1160, 20);
        Walls w213 = new Walls(8535, 1380, 20, 865);
        Walls w214 = new Walls(7395, 2220, 1160, 20);

        levelWalls.add(w211);
        levelWalls.add(w212);
        levelWalls.add(w213);
        levelWalls.add(w214);

        // Level 5

        Walls w215 = new Walls(9745, 1380, 20, 865);
        Walls w216 = new Walls(9745, 1380, 1160, 20);
        Walls w217 = new Walls(10885, 1380, 20, 865);
        Walls w218 = new Walls(9745, 2220, 1160, 20);

        levelWalls.add(w215);
        levelWalls.add(w216);
        levelWalls.add(w217);
        levelWalls.add(w218);

    }

    /** 
        Draws the images in the canvas.
    **/
    @Override
    public void draw(Graphics2D g2d) {
        
        g2d.setColor(new Color(177, 224, 141));
        g2d.fillRect(-500, 1000, 11850, 1800);
        g2d.drawImage(level1, 0, 1200, 1850, 1201, null);
        g2d.drawImage(level2, 2350, 1200, 1850, 1201, null);
        g2d.drawImage(level3, 4700, 1200, 1850, 1201, null);
        g2d.drawImage(level4, 7050, 1200, 1850, 1201, null);
        g2d.drawImage(level5, 9400, 1200, 1850, 1201, null);

    }

    public ArrayList<Walls> getWalls(){
        return levelWalls;
    }
 
}
