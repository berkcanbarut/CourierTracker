> # MigrosOne Courier Tracking

## Uygulama amacı

- Bu proje, **courier (kurye) lokasyonlarını gerçek zamanlı olarak endpoint üzerinden dinleyen** ve belirtilen kurallara göre loglama yapan bir **RESTful web uygulamasıdır**:


- Courier'ların belirli bir mesafe kadar (100 metreden az) Migros mağazalarına yaklaşması durumunda kayıt al
- Aynı mağazaya 1 dakika içinde tekrar girilmesini tekrarlı giriş olarak saymama
- Her bir courier'ın toplamda ne kadar yol kat ettiğini hesaplama

## 🛠️ Uygulama Gereksinimleri

- Java 17+
- Spring Boot 3+
- Maven
- Lombok
- H2 Database

## 📬 REST API Endpointleri
> **POST /api/v1/logistic/entries**\
> Content-Type: application/json
> 
> {\
    "time": "2025-05-17T17:56:56Z"\
    "courierId": 18,\
    "lat": 40.986375,\
    "lng": 29.116129,\
    "courierType" : "motorcycleCourier"\
}

> **GET /api/v1/logistic/travel/{courierId}**\
>
> Headers\
> courierType=motorcycleCourier

## Database Bilgileri
> URL = jdbc:h2:mem:migrosone;MODE=PostgreSQL;\
> Username = migrosone\
> Password = migrosone\
> Path = localhost:{your-port}/h2-console
