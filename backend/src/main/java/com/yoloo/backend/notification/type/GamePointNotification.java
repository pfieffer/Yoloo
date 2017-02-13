package com.yoloo.backend.notification.type;

import com.yoloo.backend.device.DeviceRecord;
import com.yoloo.backend.gamification.Tracker;
import com.yoloo.backend.notification.MessageConstants;
import com.yoloo.backend.notification.Notification;
import com.yoloo.backend.notification.PushMessage;
import com.yoloo.backend.notification.action.Action;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;

@AllArgsConstructor(staticName = "create")
public class GamePointNotification implements NotificationBundle {

  private DeviceRecord record;

  private Tracker tracker;

  @Override
  public List<Notification> getNotifications() {
    Notification notification = Notification.builder()
        .receiverKey(record.getParentUserKey())
        .action(Action.GAME)
        .object("points", tracker.getPoints())
        .object("bounties", tracker.getBounties())
        .created(DateTime.now())
        .build();

    return Collections.singletonList(notification);
  }

  @Override
  public PushMessage getPushMessage() {
    PushMessage.DataBody dataBody = PushMessage.DataBody.builder()
        .value(MessageConstants.ACTION, Action.GAME.getValueString())
        .value("points", String.valueOf(tracker.getPoints()))
        .value("badges", String.valueOf(tracker.getBounties()))
        .build();

    return PushMessage.builder()
        .to(record.getRegId())
        .data(dataBody)
        .build();
  }
}