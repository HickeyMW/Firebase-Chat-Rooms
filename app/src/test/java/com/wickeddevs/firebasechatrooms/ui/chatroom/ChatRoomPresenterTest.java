package com.wickeddevs.firebasechatrooms.ui.chatroom;

import com.wickeddevs.firebasechatrooms.data.model.Message;
import com.wickeddevs.firebasechatrooms.data.service.contract.ChatRoomService;
import com.wickeddevs.firebasechatrooms.utility.TestHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChatRoomPresenterTest {

    private String chatRoomName = TestHelper.randomString("Room Name");
    private String username = TestHelper.randomString("Username");

    private ChatRoomPresenter presenter;

    @Mock
    private ChatRoomContract.View view;

    @Mock
    private ChatRoomService chatRoomService;

    @Captor
    private ArgumentCaptor<ChatRoomService.ChatRoomMessagesListener> chatRoomMessagesListenerArgumentCaptor;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ChatRoomPresenter(chatRoomService);
    }

    @Test
    public void viewCreated_getRoomNameAndUsername_JoinChatRoom_messagesLoaded () {
        when(view.getRoomName()).thenReturn(chatRoomName);
        when(view.getUsername()).thenReturn(username);
        presenter.viewCreated(view);
        verify(view).toggleLoading(true);
        verify(view).getRoomName();
        verify(view).getUsername();
        verify(chatRoomService).joinChatRoom(eq(chatRoomName), eq(username), chatRoomMessagesListenerArgumentCaptor.capture());
        chatRoomMessagesListenerArgumentCaptor.getValue().initialMessagesLoaded();
        verify(view).toggleLoading(false);
    }

    @Test
    public void newMessage_isDisplayed () {
        Message message = new Message();
        viewCreated_getRoomNameAndUsername_JoinChatRoom_messagesLoaded();
        chatRoomMessagesListenerArgumentCaptor.getValue().newMessage(message);
        verify(view).displayMessage(message);
    }

    @Test
    public void sendMessage () {
        viewCreated_getRoomNameAndUsername_JoinChatRoom_messagesLoaded();
        String message = TestHelper.randomString("Message");
        presenter.sendMessage(message);
        verify(chatRoomService).sendMessage(message, username, chatRoomName);
    }

    @Test
    public void leaveChatRoom () {
        viewCreated_getRoomNameAndUsername_JoinChatRoom_messagesLoaded();
        presenter.leaveChatRoom();
        verify(chatRoomService).leaveChatRoom(chatRoomName);
    }

}