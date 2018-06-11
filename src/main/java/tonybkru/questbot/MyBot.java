/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonybkru.questbot;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.json.JSONObject;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import tonybkru.questbot.bot.document.spi.DocumentMarshaller;
import tonybkru.questbot.model.GroupsOfWord;
import tonybkru.questbot.model.IrregularLine;
import tonybkru.questbot.util.ActionBuilder;
import tonybkru.questbot.util.InlineKeyboardButtonBuilder;
import tonybkru.questbot.util.UpdateUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static tonybkru.questbot.model.GroupsOfWord.findVerb;

/**
 *
 * @author timofeevan
 */
public class MyBot extends TelegramLongPollingBot {

    public static String TOKEN = "****number**:AAGm6YSKvZhqQCxdQBTf7a73pUQDXsWj0cE";//****number**
    public static String NAME = "@irregulartonybot";

    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";
    public static final String SIX = "6";
    public static final String SEVEN = "7";
    public static final String EIGHT = "8";
    public static final String NINE = "9";
    public static final String TEN = "10";

    private DocumentMarshaller marshaller;

    public MyBot() {
    }

    public MyBot(DefaultBotOptions options) {
        super(options);
    }


    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public String getBotUsername() {
        return NAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("111111");
            processCommand(update);
        } else if (update.hasCallbackQuery()) {
            System.out.println("2222222");
            processCallbackQuery(update);//TODO i don't understand!!
        } else if (update.hasInlineQuery()) {
            System.out.println("3333");
            //processCallbackQuery(update);
        }
    }

    private void processCallbackQuery(Update update) {
        System.out.println("TONY im in processCallbackQuery!!!!!");
        List<SendMessage> answerMessage = null;
        String data = update.getCallbackQuery().getData();
        if (data == null) {
            return;
        }
        answerMessage = _processCallbackQuery(update);
        if (answerMessage != null && answerMessage.isEmpty()) {
            answerMessage.clear();
        }
    }

    public List<SendMessage> _processCallbackQuery(Update update) {
        List<SendMessage> answerMessages = new ArrayList<>();
        try {
            String data = update.getCallbackQuery().getData();
            System.out.println("Tony ____ name group"+data);
            if (data == null) {
                return null;
            }
            sendGroup(update, data);
        } catch (Exception ex) {
            Logger.getLogger(MyBot.class.getName()).log(Level.SEVERE, null, ex);
            answerMessages.add(errorMessage());
        }
        return answerMessages;
    }

    private void processCommand(Update update) {
        SendMessage answerMessage = null;
        try {
            answerMessage = _processCommand(update);
            if (answerMessage != null) {
                execute(answerMessage);
            }
        } catch (TelegramApiException ex) {
            Logger.getLogger(MyBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SendMessage _processCommand(Update update) {
        SendMessage answerMessage = null;
        Message inputMassage = update.getMessage();
        String text = update.getMessage().getText();

        System.out.println("Tony catch this:___  "+text);

        if ("/start".equals(text)) {
            answerMessage = new SendMessage();
            Message messege = update.getMessage();
            User user = messege.getFrom();
            answerMessage.setText("How is it going on, "+ user.getUserName()+"? Все неправильные глаголы разбиты на группы! Введи номер группы, с которой ты хочешь начать!");
            answerMessage.setChatId(update.getMessage().getChatId());

            //InlineKeyboardMarkup markup = keyboard(update);
            ReplyKeyboardMarkup markup = keyboardReply();
            answerMessage.setReplyMarkup(markup);

        } else if("/see".equals(text)) {
            System.out.println("In see part!!!!!");
//            SendPhoto msg = new SendPhoto()
//                    .setChatId(update.getMessage().getChatId())
//                    .setPhoto("http://aftamat4ik.ru/wp-content/uploads/2017/05/14277366494961.jpg")
//                    .setCaption("Beauty");
//            try {
//                sendPhoto(msg);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
            Message messege = update.getMessage();

        } else if ("/find".equals(text)){
            //TODO find 1. all form for verb + translate!
            //TODO 2.photo
            //TODO 3.sound
            System.out.println(inputMassage);
            findVerb();
        } else if (text.equals(ONE) || text.equals(THREE) || text.equals(TWO) || text.equals(FOUR) || text.equals(FIVE)
                || text.equals(SIX) || text.equals(SEVEN) || text.equals(EIGHT) || text.equals(NINE) || text.equals(TEN))   {
            System.out.println("Im here!!!text is : "+text);
            try {
                sendGroup(update, text.toString());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else {

        }
        return answerMessage;
    }

    private void sendGroup(Update update, String data) throws TelegramApiException {
        //JSONObject json = new JSONObject(data);
        //String groupName = (String) json.get("name");
        String groupName = data;
        System.out.println("I'm in sendGroup! groupName = "+ groupName);

        Long chatId = UpdateUtil.getChatFromUpdate(update).getId();
        GroupsOfWord groups = new GroupsOfWord();

        ArrayList<IrregularLine> group = groups.getGroup(groupName);
        SendMessage message = new SendMessage();
        if(data != null && group != null) {
            System.out.println("______*****____TONY groupName"+data);

            StringBuilder allText = new StringBuilder("Group " + groupName + ":\n");
            for(int i = 0; i < group.size(); i++) {
                allText.append(group.get(i).toString());
            }
            message.setText(allText.toString());
            message.setChatId(chatId);
            execute(message);
            //todo send Foto!!!

        } else {
            message.setText("Sorry!");
        }
    }

    public InlineKeyboardMarkup keyboard(Update update) {
        final InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Arrays.asList(makeButton(ONE)));
        keyboard.add(Arrays.asList(makeButton(TWO)));
        keyboard.add(Arrays.asList(makeButton(THREE)));
        keyboard.add(Arrays.asList(makeButton(FOUR)));
        keyboard.add(Arrays.asList(makeButton(FIVE)));
        markup.setKeyboard(keyboard);
        return markup;
    }

    public InlineKeyboardButton makeButton(String name) {
        InlineKeyboardButton button = new InlineKeyboardButtonBuilder()
                .setText(name)
                .setCallbackData(new ActionBuilder(marshaller)
                        .setName(name)
                        .asString())
                .build();
        return button;
    }

    public ReplyKeyboardMarkup keyboardReply() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("1");
        keyboardFirstRow.add("2");
        keyboardFirstRow.add("3");
        keyboardFirstRow.add("4");
        keyboardFirstRow.add("5");

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("6");
        keyboardSecondRow.add("7");
        keyboardSecondRow.add("8");
        keyboardSecondRow.add("9");
        keyboardSecondRow.add("10");

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public SendMessage errorMessage() {
        SendMessage answerMessage = new SendMessage();
        answerMessage.setText("Ой, что-то пошло не так, попробуйте еще раз или перейдите в главное меню");
        InlineKeyboardMarkup keyboardMain = keyboard(null);
        answerMessage.setReplyMarkup(keyboardMain);
        return answerMessage;
    }

    public void setMarshaller(DocumentMarshaller marshalFactory) {
        this.marshaller = marshalFactory;
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        HttpHost proxy = AppEnv.getContext().getProxy();
        System.out.println("________*******________"+proxy.toString());

        DefaultBotOptions instance = ApiContext
                .getInstance(DefaultBotOptions.class);
        RequestConfig rc = RequestConfig.custom()
                .setProxy(proxy).build();
        instance.setRequestConfig(rc);
        MyBot bot = new MyBot(instance);

        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

}
