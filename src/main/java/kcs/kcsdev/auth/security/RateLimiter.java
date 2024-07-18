package kcs.kcsdev.auth.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.stereotype.Component;

@Component
public class RateLimiter {

      private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>> requestLogs = new ConcurrentHashMap<>();

      public boolean isRequestAllowed(String apiKey) {
            long currentTimeMillis = System.currentTimeMillis();
            requestLogs.putIfAbsent(apiKey, new ConcurrentLinkedQueue<>());

            ConcurrentLinkedQueue<Long> requestTimestamps = requestLogs.get(apiKey);

            synchronized (requestTimestamps) {
                  // 10 seconds
                  long timeWindowMillis = 10000;
                  while (!requestTimestamps.isEmpty()
                          && currentTimeMillis - requestTimestamps.peek() > timeWindowMillis) {
                        requestTimestamps.poll();
                  }

                  int maxRequests = 10;
                  if (requestTimestamps.size() < maxRequests) {
                        requestTimestamps.add(currentTimeMillis);
                        return true;
                  } else {
                        return false;
                  }
            }
      }
}
