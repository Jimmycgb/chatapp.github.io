package Common;

public interface MessageType {
    String ONE_TO_ONE_F = "0";//私聊消息(发起者)
    String ONE_TO_ONE_M = "1";//私聊消息(维护者)
    String ONE_TO_ALL = "2";//群发消息
    String EXIT = "3";//退出请求
    String USER_ONLINE = "4";//查看用户列表请求
    String CREATE_ONE_TO_ONE = "5";//创建私聊窗口申请
    String CREATE_CHAT_ROOM = "6";//创建聊天室请求
    String ENTER_CHAT_ROOM = "7";//加入聊天室请求
    String CHAT_ROOM = "8";//查看聊天室列表请求
    String CHAT_ROOM_MESSAGE = "9";//在聊天室发送消息
    String CHAT_ROOM_USER = "10";//查看指定聊天室内的在线用户
    String EXIT_CHAT_ROOM = "11";//退出创建或加入的聊天室
}
