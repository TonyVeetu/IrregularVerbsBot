/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonybkru.questbot;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import netscape.javascript.JSObject;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.json.JSONObject;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import tonybkru.questbot.bot.document.spi.DocumentMarshaller;
import tonybkru.questbot.bot.schema.Action;
import tonybkru.questbot.bot.state.QuestEnumeration;
import tonybkru.questbot.bot.state.QuestStateHolder;
import tonybkru.questbot.model.ClsQuest;
import tonybkru.questbot.model.ClsQuestPhoto;
import tonybkru.questbot.model.GroupsOfWord;
import tonybkru.questbot.model.IrregularLine;
import tonybkru.questbot.model.repository.ClassifierRepository;
import tonybkru.questbot.model.repository.ClassifierRepositoryImpl;
import tonybkru.questbot.util.ActionBuilder;
import tonybkru.questbot.util.InlineKeyboardButtonBuilder;
import tonybkru.questbot.util.UpdateUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static tonybkru.questbot.AppEnv.*;
import static tonybkru.questbot.model.GroupsOfWord.findVerb;

/**
 *
 * @author timofeevan
 */
public class MyBot extends TelegramLongPollingBot {

    public static String TOKEN = "//****number**:AAGm6YSKvZhqQCxdQBTf7a73pUQDXsWj0cE";//****number**
    public static String NAME = "irregulartonybot";

    //public static final String OPEN_MAIN = "OM";
    //public static final String GET_ANSWER = "GA";

    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String FIVE = "5";

    public static final String winking_face = new String(Character.toChars(0x1F609));

    private DocumentMarshaller marshaller;
    private QuestStateHolder questStateHolder;
    private ClassifierRepositoryImpl classifierRepository;

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
            processCommand(update);
        } else if (update.hasCallbackQuery()) {
            processCallbackQuery(update);//TODO i don't understand!!
        } else if (update.hasInlineQuery()) {
            //processCallbackQuery(update);
        }
    }

    public SendMessage sendMsg (Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("Команда 1");
        keyboardFirstRow.add("Команда 2");

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("Команда 3");
        keyboardSecondRow.add("Команда 4");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        return sendMessage;
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
            //Action action = new ActionBuilder(marshaller).buld(update);
            String data = update.getCallbackQuery().getData();
            System.out.println("Tony ____ name group"+data);
            Long chatId = UpdateUtil.getChatFromUpdate(update).getId();//update.getCallbackQuery().getMessage().getChatId();
            if (data == null) {
                return null;
            }
//            if (OPEN_MAIN.equals(action.getName())) {
//                initQuests(update);
//
//                sendQuest(update);
//            }
//            if (GET_ANSWER.equals(action.getName())) {
//                Long answId = Long.parseLong(action.getValue());
//                ClsAnswer answ = classifierRepository.find(ClsAnswer.class, answId);
//                SendMessage comment = new SendMessage();
//                comment.setParseMode("HTML");
//                comment.setText("<b>Твой ответ:</b> "
//                        + answ.getAnswerText()
//                        + "\n<b>Комментарий к ответу:</b> "
//                        + answ.getAnswerComment()
//                        + "\n");
//                comment.setChatId(chatId);
//                execute(comment);
//
//                sendQuest(update);
//            }
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

    private void initQuests(Update update) {
        List<ClsQuest> q = classifierRepository.find(ClsQuest.class, false);
        Collections.shuffle(q);
        questStateHolder.put(update, new QuestEnumeration(q));
    }

    public SendMessage _processCommand(Update update) {
        SendMessage answerMessage = null;
        Message inputMassage = update.getMessage();
        String text = update.getMessage().getText();

        System.out.println("Tony catch this:___  "+text);

        if ("/start".equals(text)) {
            answerMessage = new SendMessage();
            //answerMessage.setText("*Title:* Алёнка, твой курс будет ЛУЧШИМ на всём белом свете!! Но не сразу!!!\n");
            Message messege = update.getMessage();
            User user = messege.getFrom();
            answerMessage.setText("How is it doing on, "+ user.getUserName()+"? Все неправильные глаголы разбиты на группы! Введи номер группы, с которой ты хочешь начать!");
            //answerMessage.setParseMode("HTML");
            answerMessage.setChatId(update.getMessage().getChatId());//TODO вернуть кнопки 1, 2, 3 ... соответствующие группе глаголов!!

            //TODO make another
            InlineKeyboardMarkup markup = keyboard(update);
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
            sendMsg(messege, "/see");

        } else if ("/find".equals(text)){
            //TODO find 1. all form for verb + translate!
            //TODO 2.photo
            //TODO 3.sound
            System.out.println(inputMassage);
            findVerb();
        }
        else {

        }
        return answerMessage;
    }

    private void sendQuest(Update update) throws TelegramApiException {
        QuestEnumeration qe = questStateHolder.get(update);
        Long chatId = UpdateUtil.getChatFromUpdate(update).getId();
        ClsQuest nextQuest = qe.nextElement();
        if (nextQuest != null) {
            SendMessage quest = new SendMessage();
            quest.setParseMode("HTML");
            quest.setText("<b>Вопрос " + qe.getCurrentQuest() + ":</b>  "
                    + nextQuest.getQuestText());
            quest.setChatId(chatId);
            execute(quest);
            for (ClsQuestPhoto clsQuestPhoto : nextQuest.getClsQuestPhotoCollection()) {
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                sendPhoto.setNewPhoto(new File(AppEnv.getContext().getRootPath()
                        + "\\photo" + clsQuestPhoto.getRelFilePath()));
                sendPhoto(sendPhoto);
            }

            SendMessage answers = new SendMessage();
            answers.setParseMode("HTML");
            answers.setText("<b>Варианты ответа:</b>");
            answers.setChatId(chatId);
            //answers.setReplyMarkup(keyboardAnswer(update, nextQuest));
            execute(answers);
        } else {
            SendMessage answers = new SendMessage();
            answers.setParseMode("HTML");
            answers.setText("<b>Ну вот и все! Подробности на процедуре награждения</b> \n "
                    + "Если хочешь начать заново нажми кнопку 'Начать' или введи /start");
            answers.setChatId(chatId);
            InlineKeyboardMarkup markup = keyboard(update);
            answers.setReplyMarkup(markup);
            execute(answers);

            SendSticker sticker = new SendSticker();
            sticker.setChatId(chatId);

            File stikerFile = new File(AppEnv.getContext().getRootPath() + "\\photo\\stiker.png");
            if (stikerFile.exists()) {
                sticker.setNewSticker(stikerFile);
                sendSticker(sticker);
            }
        }
    }

    private void sendGroup(Update update, String data) throws TelegramApiException {

        JSONObject json = new JSONObject(data);
        String groupName = (String) json.get("name");
        System.out.println("Im in sendGroup! groupName = "+ groupName);

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

//            SendMessage answers = new SendMessage();
//            answers.setParseMode("HTML");
//            answers.setText("<b>Варианты ответа:</b>");
//            answers.setChatId(chatId);
//            answers.setReplyMarkup(keyboard(update));
//            execute(answers);
        } else {
            message.setText("Sorry!");
        }
    }



    public SendMessage errorMessage() {
        SendMessage answerMessage = new SendMessage();
        answerMessage.setText("Ой, что-то пошло не так, попробуйте еще раз или перейдите в главное меню");
        InlineKeyboardMarkup keyboardMain = keyboard(null);
        answerMessage.setReplyMarkup(keyboardMain);
        return answerMessage;
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

//    public InlineKeyboardMarkup keyboardAnswer(Update update, ClsQuest quest) {
//        final InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
//        for (ClsAnswer clsAnswer : quest.getClsAnswerCollection()) {
//            keyboard.add(Arrays.asList(buttonAnswer(clsAnswer)));
//        }
//        markup.setKeyboard(keyboard);
//        return markup;
//    }


//    public InlineKeyboardButton buttonAnswer(ClsAnswer clsAnswer) {
//        InlineKeyboardButton button = new InlineKeyboardButtonBuilder()
//                .setText(clsAnswer.getAnswerText())
//                .setCallbackData(new ActionBuilder(marshaller)
//                        .setName(GET_ANSWER)
//                        .setValue(clsAnswer.getId().toString())
//                        .asString())
//                .build();
//        return button;
//    }

    public void setMarshaller(DocumentMarshaller marshalFactory) {
        this.marshaller = marshalFactory;
    }

    public void setQuestStateHolder(QuestStateHolder questStateHolder) {
        this.questStateHolder = questStateHolder;
    }

    public void setClassifierRepository(ClassifierRepository classifierRepository) {
        this.classifierRepository = (ClassifierRepositoryImpl) classifierRepository;
    }
}
