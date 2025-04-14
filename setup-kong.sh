#!/bin/bash

# Kong Admin API adresi
KONG_API="http://localhost:8001"

echo "🔧 Kong yapılandırması başlatılıyor..."

# 1. hotel-service için service ve route oluştur
echo "➕ hotel-service için service ve route oluşturuluyor..."

curl -s -X POST $KONG_API/services \
  --data name=hotel-service \
  --data url=http://hotel-service:8081 > /dev/null

curl -s -X POST $KONG_API/services/hotel-service/routes \
  --data "paths[]=/hotel-service" > /dev/null

# 2. reservation-service için service ve route oluştur
echo "➕ reservation-service için service ve route oluşturuluyor..."

curl -s -X POST $KONG_API/services \
  --data name=reservation-service \
  --data url=http://reservation-service:8082 > /dev/null

curl -s -X POST $KONG_API/services/reservation-service/routes \
  --data "paths[]=/reservation-service" > /dev/null

# 3. reservation-service için JWT plugin aktif et
echo "🔐 reservation-service için JWT plugin aktif ediliyor..."

curl -s -X POST $KONG_API/services/reservation-service/plugins \
  --data "name=jwt" > /dev/null

# 4. Test consumer oluştur
echo "👤 Consumer: testuser oluşturuluyor..."

curl -s -X POST $KONG_API/consumers \
  --data "username=testuser" > /dev/null

# 5. JWT credential üret
# 5. JWT credential oluşturuluyor...
echo "🔑 JWT credential oluşturuluyor..."

# Rastgele key ve en az 32 karakterlik secret üret
JWT_KEY=1a0efd2e69a7e259432aba15121ca1f1   # 32 karakterlik issuer/key
JWT_SECRET=8a8f55026ec39b834448257f29a6b80d60ef603d5abf84716afaf3beb0ded92a

# Kong'a kaydet
CREDS=$(curl -s -X POST $KONG_API/consumers/testuser/jwt \
  --data "key=$JWT_KEY" \
  --data "secret=$JWT_SECRET")

KEY=$JWT_KEY
SECRET=$JWT_SECRET

# 6. Secret'ı .env dosyasına yaz
echo "JWT_SECRET=$SECRET" > .env

echo ""
echo "✅ Kong yapılandırması tamamlandı."
echo ""
echo "📢 JWT token oluşturmak için şu bilgileri kullan:"
echo ""
echo "👉 Payload:"
echo "    {"
echo "      \"iss\": \"$KEY\","
echo "      \"sub\": \"42\","
echo "      \"exp\": 9999999999"
echo "    }"
echo ""
echo "👉 Secret (HS256): $SECRET"
echo ""
echo "📁 .env dosyasına da yazıldı: JWT_SECRET=$SECRET"
echo ""
echo "🧪 Test etmek için:"
echo "curl -X GET http://localhost:8000/reservation-service/xxx \\"
echo "  -H \"Authorization: Bearer <JWT_TOKEN>\""
echo ""