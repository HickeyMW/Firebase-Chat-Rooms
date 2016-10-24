package com.wickeddevs.firebasechatrooms.ui.chatroom;

import com.wickeddevs.firebasechatrooms.data.model.Message;
import com.wickeddevs.firebasechatrooms.data.service.contract.ChatRoomService;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

public class ChatRoomPresenter implements ChatRoomContract.Presenter {

    private ChatRoomContract.View view;
    private ChatRoomService chatRoomService;

    @Inject
    public ChatRoomPresenter(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @Override
    public void viewCreated(ChatRoomContract.View createdView) {
        view = createdView;
        view.toggleLoading(true);
        chatRoomService.joinChatRoom(view.getRoomName(), view.getUsername(), new ChatRoomService.ChatRoomMessagesListener() {
            @Override
            public void newMessage(Message message) {
                message.formattedTimestamp = formatDateTime(message.timestamp);
                view.displayMessage(message);
            }

            @Override
            public void initialMessagesLoaded() {
                view.toggleLoading(false);
            }
        });
    }

    @Override
    public void sendMessage(String body) {
        chatRoomService.sendMessage(body, view.getUsername(), view.getRoomName());
    }

    @Override
    public void leaveChatRoom() {
        chatRoomService.leaveChatRoom(view.getRoomName());
    }

    private String formatDateTime(long dateTime) {
        String[] monthArray = DateFormatSymbols.getInstance(Locale.getDefault()).getMonths();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        long todayStart = todayCalendar.getTimeInMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateTime);
        String hour = calendar.get(Calendar.HOUR) == 0 ? "12" : calendar.get(Calendar.HOUR) + "";
        String minute = (calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE) + "");
        String am_pm = (calendar.get(Calendar.AM_PM) == 0 ? "AM" : "PM");
        String time = hour + ":" + minute + " " + am_pm;

        if (todayStart > dateTime) {
            String month = monthArray[calendar.get(Calendar.MONTH)];
            String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            return month + " " + day + ", " + time;
        }
        return time;
    }
}
