# Otel Rezervasyon Mikroservis Sistemi

Bu proje, otel rezervasyon sistemi için geliştirilmiş üç mikroservis içerir: Hotel Service, Reservation Service ve Notification Service. Ayrıca, API Gateway olarak Kong ve mesajlaşma için Kafka kullanılmıştır.

## 1. Gereksinimler

- Docker & Docker Compose
- Git
- Java 17
- Maven

## 2. Servisleri Başlatma

```bash
docker-compose up --build
```

Bu komut tüm servisleri ve bağımlılıkları (PostgreSQL, Kafka, Zookeeper, Kong) birlikte başlatır.

> ❗️ Not: İlk kez çalıştırıyorsanız, Kong servisinin düzgün ayağa kalkması için aşağıdaki adımları sırayla uygulamanız gerekebilir:

```bash
docker-compose run --rm kong-migrations
docker-compose up -d --build kong
```

## 3. Kong Yapılandırması

Aşağıdaki komut ile Kong üzerinde servis, route, jwt plugin ve kullanıcı tanımlamaları yapılır:

```bash
bash setup-kong.sh
```



## 4. JWT Token Üretimi

Kong üzerinden oluşturulan JWT `key` (iss) ve `secret` bilgileri terminale yazdırılır. Bu bilgilerle örnek bir token oluşturabilirsiniz.

### 📥 Örnek Payload (jwt.io'da test edilebilir)

```json
{
  "iss": "<KEY>",
  "sub": "42",
  "exp": 9999999999
}
```

### 🛠 Token Üretimi İçin Adımlar

1. `setup-kong.sh` çalıştırıldıktan sonra terminalde `KEY` ve `SECRET` bilgileri görünecek.
2. [https://jwt.io](https://jwt.io) adresine gidin.
3. Yukarıdaki payload'ı girin (`iss` kısmına terminaldeki key yazılmalı).
4. Sağ taraftaki `Verify Signature` kutusuna secret değeri girin.
5. Aşağıda oluşan token'ı Postman'de veya frontend'de `Authorization: Bearer <TOKEN>` olarak kullanabilirsiniz.

> ✅ JWT HS256 algoritması ile imzalanır. Key uzunluğu minimum 32 karakter olmalıdır (otomatik üretilir).




## 5. Hotel Service API Örnekleri

Bu servis, otel ve oda tanımlama işlemlerini içerir. CRUD işlemleri için JWT doğrulama gerektirmez.


## 6. Reservation Service

Bu servis yalnızca JWT token ile erişilebilir. Rezervasyon oluşturma isteği yaparken, Authorization header’ı ile token gönderilmelidir.

## 7. Notification Service

Kafka üzerinden gelen "reservation-created" event'lerini dinler. Dış dünyaya açık bir endpoint sağlamaz.

## 8. API Endpointleri

Aşağıda sistemde tanımlı tüm endpointler ve örnek URL path’leri yer almaktadır:

### 🔹 Hotel Service

Tüm istekler için base path: `/hotel-service/api/v1/hotels`

| Metot | Path                    | Açıklama             |
|-------|-------------------------|----------------------|
| POST  | /create                 | Otel oluştur         |
| PUT   | /update                 | Otel güncelle        |
| DELETE| /deletePermanently      | Otel kalıcı sil      |
| GET   | /findAll?page=0&size=10 | Otelleri listele     |

---

### 🔹 Room Service

Tüm istekler için base path: `/hotel-service/api/v1/rooms`

| Metot | Path                    | Açıklama             |
|-------|-------------------------|----------------------|
| POST  | /create                 | Oda oluştur          |
| PUT   | /update                 | Oda güncelle         |
| DELETE| /deletePermanently      | Oda kalıcı sil       |
| GET   | /findAll?page=0&size=10 | Odaları listele      |

---

### 🔹 Reservation Service

Tüm istekler için base path: `/reservation-service/api/v1/reservations`

| Metot | Path                    | Açıklama             |
|-------|-------------------------|----------------------|
| POST  | /create                 | Rezervasyon oluştur  |
| PUT   | /update                 | Rezervasyon güncelle |
| DELETE| /delete                 | Rezervasyon sil      |
| GET   | /findAll?page=0&size=10 | Rezervasyonları listele |

> 🔐 Reservation servisinde tüm isteklerde JWT token gereklidir.