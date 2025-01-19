# LC Waikiki Test Otomasyon Projesi 🛒🛍️

Bu proje, **Selenium, TestNG, WebDriverManager, Allure** gibi test araçları kullanılarak **LC Waikiki** e-ticaret platformu üzerinde **otomatik test senaryolarını** yürütmek için geliştirilmiştir.

---

## 🚀 Projenin Amacı
Bu proje, **LC Waikiki e-ticaret web sitesi** üzerinde **kategori seçimi, ürün filtreleme, sıralama, sepete ekleme, favorilere ekleme ve ödeme adımına ilerleme** gibi **kritik kullanıcı senaryolarını** test etmek için oluşturulmuştur.

Otomatik testler ile:

✅ Kullanıcı arayüzü akışlarının doğrulanması  
🛍️ Sepet yönetimi ve favorilere ekleme süreçlerinin kontrol edilmesi  
💳 Ödeme sürecine geçiş işleminin test edilmesi

---

## 🔧 Kullanılan Teknolojiler

| Teknoloji | Açıklama |
|-----------|---------|
| **Java 17** | Test senaryolarının yazıldığı dil |
| **Selenium WebDriver 4.18.1** | Web tarayıcısını kontrol etmek için kullanıldı |
| **TestNG 7.7.1** | Testleri yönetmek ve senaryoları sıralamak için kullanıldı |
| **WebDriverManager 5.7.0** | Selenium WebDriver bağımlılıklarını yönetmek için kullanıldı |
| **Allure Report 2.20.1** | Test sonuçlarını detaylı raporlamak için kullanıldı |

---

## 📌 Test Senaryoları

| # | Test Senaryosu | Açıklama |
|---|--------------------|--------------|
| **1** | 🔑 **Login Testi** | Geçerli kullanıcı bilgileri ile giriş yapılmasını test eder. |
| **2** | 📂 **Kategori Navigasyon Testi** | Çocuk & Bebek > Kız Çocuk > Mont & Kaban kategorisine erişimi test eder. |
| **3** | 🎨 **Filtreleme Testi** | Ürünlerin **beden ve renk filtreleri** ile filtrelenmesini test eder. |
| **4** | 📌 **Sıralama Testi** | Ürünleri "En çok satanlar" kriterine göre sıralar ve doğrular. |
| **5** | 🛒 **Sepete Ürün Ekleme Testi** | 4. sıradaki ürünü seçerek stoktaki beden ile sepete ekler. |
| **6** | 🔍 **Sepet Kontrol Testi** | Sepetteki ürün adını, rengini, fiyatını ve adedini doğrular. |
| **7** | ➕➖ **Ürün Adedi Güncelleme Testi** | Sepetteki ürün adedini **artırıp azaltmayı** test eder. |
| **8** | ❤️ **Favorilere Ekleme Testi** | Sepetteki ürünü favorilere ekleyip doğru şekilde eklendiğini doğrular. |
| **9** | 💳 **Ödeme Adımına Geçiş Testi** | Sepetten ödeme adımına başarıyla geçişi test eder. |

---
## 🚀 Projenin Loom Linki
  https://www.loom.com/share/b0c46e5f358c41149246cc4da1a682d6?sid=8598c415-dc96-4e09-8547-7632c5c55982
---
### 📽️ Test Senaryosu Çalışırken:

---
## 📦 Proje Kurulumu

### 1️⃣ Maven ile Bağımlılıkları İndir
```sh
mvn clean install
