package com.hyl.animalpersonality;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    private final String[] animals = {"老鷹型", "獅子型", "海豚型", "貓頭鷹型", "鹿型", "狼型"};
    private final int[] scores = new int[6];
    private int current = 0;
    private LinearLayout root;
    private TextView title, content, progress;

    private final String[][] questions = {
            {"面對一個新機會，你第一反應通常是？", "我先看格局與未來性", "我想知道能不能贏、能不能快", "我先感覺人與氣氛對不對", "我會先查資料、看風險", "我會先觀察大家的反應", "我會判斷這件事能不能形成團隊戰力"},
            {"在團隊裡，你最自然扮演的角色是？", "帶方向的人", "衝鋒決策的人", "凝聚氣氛的人", "分析規劃的人", "照顧支持的人", "守紀律、帶節奏的人"},
            {"當別人反對你時，你比較可能？", "拉高視野談願景", "直接回應核心問題", "先緩和情緒、建立關係", "要求拿出事實與證據", "避免衝突，找安全說法", "看對方是否值得繼續合作"},
            {"你最重視合作夥伴哪一點？", "有夢想與企圖心", "有行動力與戰鬥力", "有熱情與親和力", "有邏輯與專業度", "有善意與穩定度", "有忠誠與紀律感"},
            {"你做決定時最常依靠什麼？", "願景與大方向", "速度與結果", "感覺與關係", "數據與推理", "安全感與和諧", "規則與戰略"},
            {"你最怕團隊出現什麼問題？", "沒有夢想、沒有方向", "拖延、軟弱、沒戰力", "冷漠、沒有溫度", "混亂、沒有方法", "衝突、傷害感情", "沒有紀律、各做各的"},
            {"你邀約別人時，最擅長用哪種方式？", "描繪未來藍圖", "直接提出機會與目標", "聊天關心、自然帶入", "用資料與案例說明", "溫和陪伴、慢慢引導", "建立信任後再精準出擊"},
            {"在壓力之下，你會變成？", "更想突破現況", "更強勢、更急", "更需要人支持", "更鑽細節、更挑剔", "更退縮、更敏感", "更冷靜、更防衛"},
            {"你喜歡的學習方式是？", "聽大師談趨勢與格局", "實戰演練，馬上用", "有人帶、有互動、有鼓勵", "有架構、有步驟、有資料", "慢慢理解，不要壓迫", "跟著制度與紀律反覆操練"},
            {"別人最常稱讚你？", "有高度、有遠見", "有魄力、有效率", "親切、會鼓勵人", "細心、專業、嚴謹", "溫柔、穩定、可靠", "沉著、有策略、有義氣"},
            {"你心中成功的關鍵是？", "選對方向，放大格局", "拿出行動，創造成果", "建立信任，凝聚人心", "掌握方法，降低錯誤", "穩定累積，不傷關係", "複製紀律，形成系統"},
            {"如果要用一句話形容你，你比較像？", "飛得高，看得遠", "敢衝敢拚，目標明確", "有溫度，喜歡連結人", "理性冷靜，重視真相", "安定柔和，重視感受", "忠誠堅定，重視團隊"}
    };

    @Override public void onCreate(Bundle b) { super.onCreate(b); showIntro(); }

    private void base() {
        ScrollView scroll = new ScrollView(this);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(24), dp(20), dp(24));
        root.setBackgroundColor(Color.rgb(255,248,239));
        scroll.addView(root);
        setContentView(scroll);
    }

    private TextView tv(String s, int size, int color, int style) {
        TextView v = new TextView(this);
        v.setText(s);
        v.setTextSize(size);
        v.setTextColor(color);
        v.setTypeface(Typeface.DEFAULT, style);
        v.setLineSpacing(4, 1.15f);
        v.setPadding(0, dp(6), 0, dp(6));
        return v;
    }

    private Button btn(String s) {
        Button b = new Button(this);
        b.setText(s);
        b.setTextSize(16);
        b.setAllCaps(false);
        b.setPadding(dp(8), dp(10), dp(8), dp(10));
        return b;
    }

    private void showIntro() {
        base();
        TextView logo = tv("🦅", 54, Color.rgb(59,36,20), Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        root.addView(logo);
        title = tv("動物人格識人系統", 28, Color.rgb(59,36,20), Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        root.addView(title);
        content = tv("用 12 題快速了解一個人的溝通風格、決策習慣與團隊位置。\n\n這不是醫學或心理診斷，而是一套適合課程、團隊訓練、識人用人的實用工具。", 17, Color.rgb(60,45,35), Typeface.NORMAL);
        root.addView(content);
        Button start = btn("開始測驗");
        start.setOnClickListener(v -> showQuestion());
        root.addView(start);
        root.addView(tv("© 許譽櫳｜譽櫳語錄 H.Y.L.", 13, Color.DKGRAY, Typeface.NORMAL));
    }

    private void showQuestion() {
        base();
        progress = tv("第 " + (current + 1) + " / " + questions.length + " 題", 15, Color.rgb(139,69,19), Typeface.BOLD);
        root.addView(progress);
        root.addView(tv(questions[current][0], 22, Color.rgb(59,36,20), Typeface.BOLD));
        for (int i = 1; i <= 6; i++) {
            final int idx = i - 1;
            Button b = btn(questions[current][i]);
            b.setOnClickListener(v -> {
                scores[idx]++;
                current++;
                if (current < questions.length) showQuestion(); else showResult();
            });
            root.addView(b);
        }
    }

    private void showResult() {
        base();
        int max = 0;
        for (int i = 1; i < scores.length; i++) if (scores[i] > scores[max]) max = i;
        root.addView(tv("你的主要人格：" + animals[max], 28, Color.rgb(59,36,20), Typeface.BOLD));
        root.addView(tv(resultText(max), 17, Color.rgb(60,45,35), Typeface.NORMAL));
        root.addView(tv("各型分數", 20, Color.rgb(59,36,20), Typeface.BOLD));
        for (int i = 0; i < animals.length; i++) root.addView(tv(animals[i] + "：" + scores[i] + " 分", 16, Color.rgb(60,45,35), Typeface.NORMAL));
        Button again = btn("重新測驗");
        again.setOnClickListener(v -> { Arrays.fill(scores, 0); current = 0; showIntro(); });
        root.addView(again);
        root.addView(tv("提醒：本測驗適合教學、溝通與團隊識人使用，結果僅供參考。", 13, Color.DKGRAY, Typeface.NORMAL));
    }

    private String resultText(int i) {
        switch (i) {
            case 0: return "老鷹型的人重視高度、方向與願景，適合擔任開路者、精神領袖與策略推動者。\n\n優勢：看得遠、有格局、能激勵人。\n溝通建議：少講瑣碎細節，直接談趨勢、使命、未來與舞台。\n帶領提醒：要補上執行節奏，避免只停留在大方向。";
            case 1: return "獅子型的人重視速度、結果與掌控感，適合衝刺、成交、帶動氣勢。\n\n優勢：有魄力、敢決定、抗壓性強。\n溝通建議：直接、明確、給目標，不要繞太久。\n帶領提醒：要多聽別人感受，避免讓團隊覺得壓力過大。";
            case 2: return "海豚型的人重視關係、氣氛與互動，適合暖場、連結、服務與社群經營。\n\n優勢：親和力強、會鼓勵人、容易建立信任。\n溝通建議：先建立感覺，再談機會與行動。\n帶領提醒：要加強目標感，避免只聊天不推進。";
            case 3: return "貓頭鷹型的人重視邏輯、資料與正確性，適合規劃、教學、制度與風險控管。\n\n優勢：專業、細心、可靠。\n溝通建議：給資料、案例、步驟與證據。\n帶領提醒：不要等到百分之百完美才行動，市場需要速度。";
            case 4: return "鹿型的人重視安全、和諧與穩定，適合陪伴新人、照顧團隊、維持長期關係。\n\n優勢：溫和、穩定、值得信賴。\n溝通建議：不要逼太快，要給安全感與明確支持。\n帶領提醒：要練習表達需求與設定界線，不要委屈自己。";
            default: return "狼型的人重視紀律、忠誠與團隊戰略，適合建立系統、守住規則、帶動複製。\n\n優勢：穩、準、狠，有團隊意識與戰略感。\n溝通建議：談承諾、規則、任務與團隊榮譽。\n帶領提醒：要增加彈性，避免過度防衛或過度要求。";
        }
    }

    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }
}
