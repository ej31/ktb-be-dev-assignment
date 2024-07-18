package kcs.kcsdev.auth.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Component;

@Component
public class RateLimiter {

      private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>> requestLogs = new ConcurrentHashMap<>();

      /**
       * 이 메서드는 주어진 API 키(apiKey)에 대한 요청이 허용되는지를 판단.
       *
       * @param apiKey 요청을 보낸 클라이언트의 API 키
       * @return 요청이 허용되면 true, 그렇지 않으면 false 를 반환
       */
      public boolean isRequestAllowed(String apiKey) {
            long currentTimeMillis = System.currentTimeMillis();
            requestLogs.putIfAbsent(apiKey, new ConcurrentLinkedQueue<>());

            ConcurrentLinkedQueue<Long> requestTimestamps = requestLogs.get(apiKey);

            synchronized (requestTimestamps) {
                  // 10초 시간 창
                  long timeWindowMillis = 10000; // 10,000 밀리초 = 10초
                  // 현재 시간에서 10초 이전의 요청 타임스탬프를 제거
                  while (!requestTimestamps.isEmpty()
                          && currentTimeMillis - requestTimestamps.peek() > timeWindowMillis) {
                        requestTimestamps.poll();
                  }

                  // 최대 요청 수
                  int maxRequests = 10;
                  // 현재 시간 창 내 요청 수가 최대 요청 수보다 적으면 요청을 허용
                  if (requestTimestamps.size() < maxRequests) {
                        requestTimestamps.add(currentTimeMillis);
                        return true;
                  } else {
                        // 요청이 최대 요청 수를 초과하면 요청을 거부
                        return false;
                  }
            }
      }
}
