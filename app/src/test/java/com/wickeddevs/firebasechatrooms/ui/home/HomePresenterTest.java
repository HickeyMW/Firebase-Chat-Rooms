package com.wickeddevs.firebasechatrooms.ui.home;

import com.wickeddevs.firebasechatrooms.data.service.contract.ChatRoomService;
import com.wickeddevs.firebasechatrooms.data.service.contract.NameService;
import com.wickeddevs.firebasechatrooms.data.service.contract.UserService;
import com.wickeddevs.firebasechatrooms.utility.TestHelper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomePresenterTest {

    private HomePresenter presenter;

    @Mock
    private HomeContract.View view;

    @Mock
    private ChatRoomService chatRoomService;

    @Mock
    private UserService userService;

    @Mock
    private NameService nameService;

    @Captor
    private ArgumentCaptor<ChatRoomService.ChatRoomNamesCallback> chatRoomNamesCallbackArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new HomePresenter(chatRoomService, userService, nameService);
    }

    @Test
    public void viewCreated_logInIfNotLoggedIn_displayChatRooms() {
        ArrayList<String> chatRoomNames = TestHelper.randomArrayListOfStrings(7);

        presenter.viewCreated(view);
        userService.logInIfNotLoggedIn();
        verify(chatRoomService).getChatRoomNames(chatRoomNamesCallbackArgumentCaptor.capture());
        chatRoomNamesCallbackArgumentCaptor.getValue().onLoaded(chatRoomNames);
        verify(view).displayChatRooms(chatRoomNames);
    }

    @Test
    public void selectedChatRoom_noUsernameSaved_displayNameChangeUi() {
        String chatRoomName = "Chat Room Name " + TestHelper.randomString();
        String username = null;

        helper_selectedChatRoom(chatRoomName, username);
        verify(view).displayChangeNameUi(username);
    }

    @Test
    public void selectedChatRoom_usernameSaved_displayChatRoomUi() {
        String chatRoomName = "Chat Room Name " + TestHelper.randomString();
        String username = "Username " + TestHelper.randomString();

        helper_selectedChatRoom(chatRoomName, username);
        verify(view).displayChatRoomUi(chatRoomName, username);
    }

    public void helper_selectedChatRoom(String roomName, String username) {
        viewCreated_logInIfNotLoggedIn_displayChatRooms();
        when(nameService.getUsername()).thenReturn(username);
        presenter.selectedRoom(roomName);
        verify(nameService).getUsername();
    }

    @Test
    public void changeUsername_noUsernameSaved_displayNameChangeUi() {
        String userName = null;
        helper_changeUsername(userName);

        verify(view).displayChangeNameUi("");
    }

    @Test
    public void changeUsername_usernameSaved_displayNameChangeUi() {
        String userName = TestHelper.randomString();
        helper_changeUsername(userName);

        verify(view).displayChangeNameUi(userName);
    }

    public void helper_changeUsername(String username) {
        viewCreated_logInIfNotLoggedIn_displayChatRooms();
        when(nameService.getUsername()).thenReturn(username);
        presenter.changeName();
        verify(nameService).getUsername();
    }
}