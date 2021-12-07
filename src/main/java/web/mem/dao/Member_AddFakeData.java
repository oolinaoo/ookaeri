package web.mem.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;

import util.Util;

public class Member_AddFakeData {
	
	private static final String INSERT_STMT =
			"INSERT INTO OKAERI.MEMBER(MEM_ACCT, MEM_PWD, MEM_NAME, MEM_ID, MEM_SEX, MEM_EMAIL, MEM_BIRTHDAY, ADDR_NO, MEM_PHOTO, MEM_PHONE, ACCT_CREATETIME, MEM_STATE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static String firstName="趙錢孫李周吳鄭王馮陳褚衛蔣沈韓楊朱秦尤許何呂施張孔曹嚴華金魏陶姜戚謝鄒喻柏水竇章雲蘇潘葛奚範彭郎魯韋昌馬苗鳳花方俞任袁柳酆鮑史唐費廉岑薛雷賀倪湯滕殷羅畢郝鄔安常樂於時傅皮卞齊康伍餘元卜顧孟平黃和穆蕭尹姚邵湛汪祁毛禹狄米貝明臧計伏成戴談宋茅龐熊紀舒屈項祝董樑杜阮藍閔席季麻強賈路婁危江童顏郭梅盛林刁鍾徐邱駱高夏蔡田樊胡凌霍虞萬支柯咎管盧莫經房裘繆幹解應宗宣丁賁鄧鬱單杭洪包諸左石崔吉鈕龔程嵇邢滑裴陸榮翁荀羊於惠甄魏加封芮羿儲靳汲邴糜鬆井段富巫烏焦巴弓牧隗山谷車侯宓蓬全郗班仰秋仲伊宮寧仇欒暴甘鈄厲戎祖武符劉姜詹束龍葉幸司韶郜黎薊薄印宿白懷蒲臺從鄂索鹹籍賴卓藺屠蒙池喬陰鬱胥能蒼雙聞莘黨翟譚貢勞逄姬申扶堵冉宰酈雍卻璩桑桂濮牛壽通邊扈燕冀郟浦尚農溫別莊晏柴瞿閻充慕連茹習宦艾魚容向古易慎戈廖庚終暨居衡步都耿滿弘匡國文寇廣祿闕東毆殳沃利蔚越夔隆師鞏厙聶晁勾敖融冷訾辛闞那簡饒空曾毋沙乜養鞠須豐巢關蒯相查後江紅遊竺權逯蓋益桓公万俟司馬上官歐陽夏侯諸葛聞人東方赫連皇甫尉遲公羊澹臺公冶宗政濮陽淳于仲孫太叔申屠公孫樂正軒轅令狐鍾離閭丘長孫慕容鮮于宇文司徒司空亓官司寇仉督子車顓孫端木巫馬公西漆雕樂正壤駟公良拓拔夾谷宰父谷粱晉楚閻法汝鄢塗欽段幹百里東郭南門呼延歸海羊舌微生嶽帥緱亢況後有琴樑丘左丘東門西門商牟佘佴伯賞南宮墨哈譙笪年愛陽佟第五言福百家姓續";
	private static String[] name = {"秀娟英華慧巧美娜靜淑惠珠翠雅芝玉萍紅娥玲芬芳燕彩春菊蘭鳳潔梅琳素雲蓮真環雪榮愛妹霞香月鶯媛豔瑞凡佳嘉瓊勤珍貞莉桂娣葉璧璐婭琦晶妍茜秋珊莎錦黛青倩婷姣婉嫻瑾穎露瑤怡嬋雁蓓紈儀荷丹蓉眉君琴蕊薇菁夢嵐苑婕馨瑗琰韻融園藝詠卿聰瀾純毓悅昭冰爽琬茗羽希寧欣飄育瀅馥筠柔竹靄凝曉歡霄楓芸菲寒伊亞宜可姬舒影荔枝思麗", "偉剛勇毅俊峰強軍平保東文輝力明永健世廣志義興良海山仁波寧貴福生龍元全國勝學祥才發武新利清飛彬富順信子傑濤昌成康星光天達安巖中茂進林有堅和彪博誠先敬震振壯會思群豪心邦承樂紹功鬆善厚慶磊民友裕河哲江超浩亮政謙亨奇固之輪翰朗伯巨集言若鳴朋斌樑棟維啟克倫翔旭鵬澤晨辰士以建家致樹炎德行時泰盛雄琛鈞冠策騰楠榕風航弘"}; 
	
	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		

		try {
			Class.forName(Util.DRIVER);
			con = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);
			String[] sex = {"男", "女", "其他"};
			String[] photo = {"items/Cliff.png", "items/Gina.png" , "items/Gru.png", "items/Joe.png", "items/Olina.png"};
			
			for(int i = 1; i <= 75; i++) {
				String acct = "gina" + i;
				String rdsex = null;
				pstmt.setString(1, acct);
				pstmt.setString(2, acct + "pwd");
				
				char first = firstName.charAt((int)(Math.random() * firstName.length()));
		        int num = (int)Math.random() * 2;
		        char sec = name[num].charAt((int)(Math.random() * name[num].length()));
		        char third = name[num].charAt((int)(Math.random() * name[num].length()));
		        String full_name = String.valueOf(first) + String.valueOf(sec) + String.valueOf(third);
				pstmt.setString(3, full_name);
				
				if(i <= 9) {
					pstmt.setString(4, "B00000000" + i);
				}
				else {
					pstmt.setString(4, "B0000000" + i);
				}	
				
		        rdsex = sex[(int)(Math.random() * 3)];
		        pstmt.setString(5, rdsex);
		        pstmt.setString(6, acct + "@gmail.com");
		        
		        GregorianCalendar gc = new GregorianCalendar();
		        int year = randBetween(1900, 2010);
		        gc.set(gc.YEAR, year);
		        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
		        gc.set(gc.DAY_OF_YEAR, dayOfYear);
		        String birthday = gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH);
		        pstmt.setDate(7, java.sql.Date.valueOf(birthday));
		        
		        pstmt.setInt(8, i);
		        
		        String rdphoto = null;
		        rdphoto = photo[(int)(Math.random() * 5)];
				try {
					byte[] pic = getPictureByteArray(rdphoto);
					pstmt.setBytes(9, pic);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(i <= 9) {
					pstmt.setString(10, "090000000" + i);
				}
				else {
					pstmt.setString(10, "09000000" + i);
				}	
				pstmt.setTimestamp(11, Timestamp.valueOf(LocalDateTime.now()));
				pstmt.setInt(12, 0);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			System.out.println("新增成功");

		} catch (ClassNotFoundException ce) {
			System.out.println(ce);
		} catch (SQLException se) {
			System.out.println(se);
		} finally {

			// 依建立順序關閉資源 (越晚建立越早關閉)
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					System.out.println(se);
				}
			}
		}
		
		
	}
	
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    
	public static byte[] getPictureByteArray(String path) throws IOException{
		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		fis.close();
		return buffer;
	}

}
