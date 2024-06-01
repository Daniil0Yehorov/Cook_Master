package com.CG.CookGame.Controllers;

import com.CG.CookGame.Enums.Role;
import com.CG.CookGame.Models.*;
import com.CG.CookGame.Repositorys.*;
import com.CG.CookGame.Services.LevelService;
import com.CG.CookGame.Services.UserService;
import com.CG.CookGame.Services.ValidationService;
import com.CG.CookGame.bean.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@AllArgsConstructor
@Controller
public class AuthorizationController {
    //репозиторії ці тільки для додавання в бд даних про левели; поки адмін панелі додавання немає
    @Autowired
    ProductRepository productRepository;
    @Autowired
    DishRepository dishRepository;
    @Autowired
    LevelRepository levelRepository;
    @Autowired
    DishHaveProductsRepository dishHaveProductsRepository;
    @Autowired
    LevelService levelService;
    private void initData() {
        //Products
        Product bread= new Product(1L,"Хліб");
        Product vershkmasl=new Product(2L,"Вершкове масло");
        Product kovbasa=new Product(3L,"Ковбаса");
        Product kapusta=new Product(4L,"Капуста");
        Product Pharsh=new Product(5L,"Фарш");
        Product TomatPasta=new Product(6L,"Томатна паста");
        Product KurkaGrudka=new Product(7L,"Куряча грудка");
        Product muka=new Product(8L,"Мука");
        Product Egg=new Product(9L,"Яйце");
        Product Suhar=new Product(10L,"Панірувальні сухарі");
        Product Tisto=new Product(11L,"Тісто");
        Product Cheese=new Product(12L,"Сир");
        Product Zelen=new Product(13L,"Зелень");
        Product Tomatsous=new Product(14L,"Томатний соус");
        Product Mozarella=new Product(15L,"Моцарелла");
        Product Bazilik=new Product(16L,"Базилік");
        Product Sous=new Product(17L,"Соус");
        Product Salami=new Product(18L,"Салямі");
        Product  Nori=new Product(19L,"Норі");
        Product Ris=new Product(20L,"Рис");
        Product Vershkcheese=new Product(21L,"Вершковий сир");
        Product Cucumber=new Product(22L,"Огірок");
        Product Redfish=new Product(23L,"Червона риба");
        Product Avokado=new Product(24L,"Авокадо");
        Product Masago=new Product(25L,"Масаго");
        Product meat=new Product(26L,"М'ясо");
        Product Cibula=new Product(27L,"Цибуля");
        Product Potato=new Product(28L,"Картопля");
        Product Mayonezii=new Product(29L,"Майонез");
        Product Oseledez=new Product(30L,"Оселедець");
        Product Carrot=new Product(31L,"Морква");
        Product Buryak=new Product(32L,"Буряк");
        productRepository.saveAll(Arrays.asList(
                bread,vershkmasl,kovbasa,kapusta,Pharsh,TomatPasta,KurkaGrudka,muka
                ,Egg,Suhar,Tisto,Cheese,Zelen,Tomatsous,Mozarella,Bazilik,Sous,Salami
                ,Nori,Ris,Vershkcheese,Cucumber,Redfish,Avokado,Masago,meat,Cibula,Potato
                ,Mayonezii,Oseledez,Carrot,Buryak
        ));
        //Dishes
        Dish buter = new Dish(1L,"Бутерброд","Рецепт: \n" +
                "Візьміть шматок свіжого хліба. Намажте тонким слоєм вершкове масло. Наріжте ковбасу \n" +
                "тонкими скибочками і викладіть на хліб. Бутерброд готовий до подачі! \n" +
                "Історія: \n" +
                "Слово \"бутерброд\" походить від німецького Butterbrot, що буквально означає \"хліб з \n" +
                "олією\". У Європі це слово фіксується не пізніше останньої третини XIX століття і означає \n" +
                "скибочку хліба з начинкою. \n" +
                "У середні віки в Європі замість тарілок використовували великі шматки хліба, на які \n" +
                "клали різні продукти. Після трапези хліб, який служив основою їжі, віддавали собакам чи \n" +
                "з'їдали. \n" +
                "Найбільший бутерброд у світі завдовжки 720 метрів було виготовлено мешканцями \n" +
                "ліванського села Кфар Катра. ","https://uk.wikipedia.org/wiki/%D0%91%D1%83%D1%82%D0%B5%D1%80%D0%B1%D1%80%D0%BE%D0%B4#:~:text=%D0%91%D1%83%D1%82%D0%B5%D1%80%D0%B1%D1%80%D0%BE%CC%81%D0%B4%20(%D0%B2%D1%96%D0%B4%20%D0%BD%D1%96%D0%BC.,%D1%8F%D0%BA%D1%83%20%D0%BF%D0%BE%D0%BA%D0%BB%D0%B0%D0%B4%D0%B5%D0%BD%D1%96%20%D0%B4%D0%BE%D0%B4%D0%B0%D1%82%D0%BA%D0%BE%D0%B2%D1%96%20%D1%85%D0%B0%D1%80%D1%87%D0%BE%D0%B2%D1%96%20%D0%BF%D1%80%D0%BE%D0%B4%D1%83%D0%BA%D1%82%D0%B8.",
                "/images/1buter.png");
        Dish Golubci = new Dish(2L,"Голубці","Рецепт: \n" +
                "Відваріть капусту до м’якості листків. Приправте фарш сіллю та перцем. На кожен лист \n" +
                "капусти викладіть порцію фаршу і загорніть у форму конверта. Укладіть голубці в \n" +
                "каструлю, залийте томатною пастою, розведеною водою, і тушкуйте на повільному вогні \n" +
                "близько 40 хвилин. \n" +
                "Історія: \n" +
                "Вважається, що ця страва прийшла до Європи від литовців, які, у свою чергу, запозичили \n" +
                "рецепт у турків і татар, замінивши виноградне листя на капустяне та баранину на свинину. \n" +
                "Є й версія, що голубці - це “бюджетний” варіант французької страви з голубів, \n" +
                "підсмажених на ґратах, яка перетворилася на фарш у капустяному листі. \n" +
                "Голупці є традиційною стравою у багатьох країнах Східної Європи та мають різні назви та \n" +
                "варіації у кожній культурі. Наприклад, у Польщі вони відомі як “гоłbki”, що буквально \n" +
                "перекладається як “маленькі голуби”. Ця назва, можливо, походить від форми страви, що \n" +
                "нагадує гніздо з птахами. ","https://uk.wikipedia.org/wiki/%D0%93%D0%BE%D0%BB%D1%83%D0%B1%D1%86%D1%96",
                "/images/2Golubci.png");
        Dish Naggetsi = new Dish(3L,"Нагетси","Рецепт: \n" +
                "Наріжте курячу грудку на невеликі шматочки. В одній мисці висипте муку, в іншій збийте \n" +
                "яйце, а в третій покладіть панірувальні сухарі. Посоліть і поперчіть шматочки курячої \n" +
                "грудки. Обваляйте кожен шматочок спочатку в муці, потім занурте в яйце, і нарешті \n" +
                "обваляйте в панірувальних сухарях. Розігрійте олію в сковороді і обсмажте нагетси до \n" +
                "золотистої скоринки з обох боків. Викладіть готові нагетси на паперовий рушник, щоб \n" +
                "позбутися зайвої олії. \n" +
                "Історія: \n" +
                "Історія нагетсів розпочалася у 1950-х роках завдяки американському вченому Роберту \n" +
                "Бейкеру. Він придумав обсмажувати невеликі шматочки курячого філе в ідеальній, міцній \n" +
                "і дуже хрусткій скоринці.  З того часу нагетси стали популярними у ресторанах швидкого \n" +
                "харчування та продажу заморожених варіантів для домашнього використання. Вони \n" +
                "завоювали популярність у США, а й у країнах Європи та Азії. Хрумкі зовні та м'які \n" +
                "всередині, вони стали улюбленою закускою багатьох. \n" +
                "Назва \"наггет\" походить від англійського слова \"nugget\", що перекладається як \"золотий \n" +
                "самородок\". Ця назва відображає форму страви, що нагадує золоті зливки. ","https://en.wikipedia.org/wiki/Chicken_McNuggets",
                "/images/3Naggetsi.png");
        Dish Hachap= new Dish(4L,"Хачапурі","Рецепт: \n" +
                "Розкачайте тісто у великий круглий пласт. Натріть сир на тертці. Викладіть сирну начинку \n" +
                "на середину тіста, залишаючи вільними краї приблизно 2-3 см. Загорніть краї тіста до \n" +
                "центру, залишаючи відкритою середину з начинкою. Випікайте хачапурі в духовці при \n" +
                "200°C до золотистої скоринки, близько 15-20 хвилин. За кілька хвилин до готовності \n" +
                "розбийте яйце на середину хачапурі та додайте зелень, дайте йому затвердіти в духовці. \n" +
                "Історія: \n" +
                "Спочатку хачапурі було популярно лише у гірських районах північно-західної Грузії. Часу \n" +
                "робітників не вистачало для повноцінного обіду, і коржик з сиром та яйцем відмінно \n" +
                "підходив для швидкого та ситного перекушування. Потім ця ситна та ароматна страва \n" +
                "набула широкого поширення по всій країні, але в кожному регіоні її готували за власним \n" +
                "рецептом. Деякі грузини додавали додаткові інгредієнти або змінювали форму хачапурі - \n" +
                "від квадратного конверта до великого кола та човна. Тому на даний момент відомо \n" +
                "щонайменше десять різновидів цієї страви. ","https://uk.wikipedia.org/wiki/%D0%A5%D0%B0%D1%87%D0%B0%D0%BF%D1%83%D1%80%D1%96",
                "/images/4Hachap.png");
        Dish Margarit = new Dish(5L,"Маргарита","Рецепт: \n" +
                "Розігрійте духовку до 250°C. Розкачайте тісто на злегка посипаній борошном поверхні до \n" +
                "бажаної товщини. Перенесіть тісто на деко для піци або на пергаментний папір. \n" +
                "Рівномірно розподіліть томатний соус по тісту, залишаючи вільними краї приблизно 2 см. \n" +
                "Посипте зверху тертою моцареллою. Додайте листя свіжого базиліку і крапельку \n" +
                "оливкової олії. Посоліть за смаком. Випікайте піцу 10-15 хвилин або до золотистої \n" +
                "скоринки і розплавленої моцарелли. \n" +
                "Історія: \n" +
                "Історія про піцу Маргарита стосується її назви. Існує теорія, що піца була названа на честь \n" +
                "королеви Маргарити Савойської, коли в 1889 неаполітанське піцайоло Рафаеле Еспозіто \n" +
                "створив піцу, яка символізувала кольори італійського прапора: червоний (помідор), \n" +
                "зелений (базилик) і білий (моцарелла). Однак пізніші дослідження поставили під сумнів \n" +
                "цю історію, засумнівавшись у справжності листа, який нібито було написано королевою, і \n" +
                "вказали, що історія та назва “Маргарита” вперше прозвучали у 1930-1940-х роках.","https://uk.wikipedia.org/wiki/%D0%9F%D1%96%D1%86%D0%B0_%D0%9C%D0%B0%D1%80%D0%B3%D0%B0%D1%80%D0%B8%D1%82%D0%B0#:~:text=%D0%9F%D1%96%D1%86%D0%B0%20%C2%AB%D0%9C%D0%B0%D1%80%D0%B3%D0%B0%D1%80%D0%B8%D1%82%D0%B0%C2%BB%20%E2%80%94%20%D1%86%D0%B5%20%D1%82%D0%B8%D0%BF%D0%BE%D0%B2%D0%B0,%D0%B1%D0%B0%D0%B7%D0%B8%D0%BB%D1%96%D0%BA%D0%BE%D0%BC%2C%20%D1%81%D1%96%D0%BB%D0%BB%D1%8E%20%D1%82%D0%B0%20%D0%BE%D0%BB%D0%B8%D0%B2%D0%BA%D0%BE%D0%B2%D0%BE%D1%8E%20%D0%BE%D0%BB%D1%96%D1%94%D1%8E.",
                "/images/5Margarit.png");
        Dish Peperoni = new Dish(6L,"Пепероні","Рецепт: \n" +
                "Розігрійте духовку до 250°C. Розкачайте тісто на злегка посипаній борошном поверхні до \n" +
                "бажаної товщини. Перенесіть тісто на деко для піци або на пергаментний папір. \n" +
                "Рівномірно розподіліть томатний соус по тісту, залишаючи вільними краї приблизно 2 см. \n" +
                "Посипте зверху тертою моцареллою. Розкладіть салямі на піці. Випікайте піцу 10-15 \n" +
                "хвилин або до золотистої скоринки і розплавленої моцарелли. \n" +
                "Історія: \n" +
                "Коли йдеться про історію піци пепероні, її коріння повертається до невеликого містечка у \n" +
                "Південній Італії, де майстер піцци вирішив додати до класичної рецептури новий \n" +
                "інгредієнт - гострий салямі, відомий як \"пепероні\". Цей італійський майстер кулінарії \n" +
                "вперше використав пепероні на піці в 1960-х роках. Спочатку ця ідея зустріла сумніви, але \n" +
                "швидко стала популярною серед місцевих та туристів. Її популярність поширилася, і піца \n" +
                "пепероні стала однією з найулюбленіших та найвідоміших піц у всьому світі, \n" +
                "завойовуючи смаки гурманів у кожному куточку земної кулі. ","https://uk.wikipedia.org/wiki/%D0%9F%D0%B5%D0%BF%D0%B5%D1%80%D0%BE%D0%BD%D1%96#:~:text=%D0%9F%D0%B5%D0%BF%D0%B5%D1%80%D0%BE%D0%BD%D1%96%20%E2%80%94%20%D0%B3%D0%BE%D1%81%D1%82%D1%80%D0%B8%D0%B9%20%D1%80%D1%96%D0%B7%D0%BD%D0%BE%D0%B2%D0%B8%D0%B4%20%D1%81%D0%B0%D0%BB%D1%8F%D0%BC%D1%96%20%D0%B2,%D1%80%D0%B5%D1%86%D0%B5%D0%BF%D1%82%20%D0%B7%D0%B3%D1%96%D0%B4%D0%BD%D0%BE%20%D0%B7%20%D0%BC%D1%96%D1%81%D1%86%D0%B5%D0%B2%D0%B8%D0%BC%D0%B8%20%D1%80%D0%B5%D0%B0%D0%BB%D1%96%D1%8F%D0%BC%D0%B8.",
                "/images/6Peperoni.png");
        Dish Philadelp = new Dish(7L,"Філадельфія","Рецепт: \n" +
                "Відваріть рис. Змішайте рис з рисовим оцтом, цукром та сіллю. Дайте йому охолонути. \n" +
                "Наріжте огірок на тонкі смужки. Розріжте червону рибу на тонкі смужки або кубики. \n" +
                "Покладіть норі на бамбуковий килимок для суші. Рівномірно розподіліть рис на норі, \n" +
                "залишаючи вільні краї. Покладіть вершковий сир, огірок та червону рибу на рис. Зверніть \n" +
                "норі в рулон, використовуючи бамбуковий килимок для допомоги. Поріжте рулон на \n" +
                "кусочки. Подавайте суші “Філадельфія” з соєвим соусом, васабі та імбиром. \n" +
                "Історія: \n" +
                "Історія ролу \"Філадельфія\" сягає свої корені у 1970-х роках в Сполучених Штатах. Цей \n" +
                "смаколик виник завдяки винаходу японського шеф-кухаря, який працював у ресторані в \n" +
                "місті Лос-Анджелес. Він вирішив експериментувати зі складом суші, поєднуючи \n" +
                "кремовий сир зі свіжим лососем та авокадо. Відмінність ролу \"Філадельфія\" в тому, що \n" +
                "лосось розміщувався всередині рису, що дозволило йому набути популярності як серед \n" +
                "любителів суші, так і тих, хто був новачком у цій кухні. З часом рол \"Філадельфія\" став \n" +
                "невід'ємною частиною японської кухні в США та пізніше у всьому світі, завдяки своєму \n" +
                "неповторному смаку та апетитному вигляду. \n" +
                "Популярність ролу Філадельфія вплинула навіть на виробництво крем-сиру. Компанія \n" +
                "\"Philadelphia\", яка виробляє відомий крем-сир, зауважила значне зростання попиту на свій \n" +
                "продукт завдяки популярності цього роллу. \n","https://en.wikipedia.org/wiki/Philadelphia_roll",
                "/images/7Philadel.png");
        Dish Kaliphorn = new Dish(8L,"Каліфорнія","Рецепт: \n" +
                "Покладіть норі на бамбукову підставку для суші. Розташуйте рис на норі, залишаючи \n" +
                "невеликий проміжок по краях. Наріжте авокадо на тонкі смужки та розташуйте на рисі. \n" +
                "Додайте шматочки червоної риби. Додайте масаго для витонченого смаку. \n" +
                "Історія: \n" +
                "Ролл “Каліфорнія” є однією з найвідоміших різновидностей суші у світі. Його історія \n" +
                "починається в 1973 році, коли шеф-повар Ітиро Масіта з ресторану Tokyo Kaikan у Лос\n" +
                "Анджелесі створив цей ролл. Він використовував інгредієнти, які були популярними в \n" +
                "Каліфорнії, такі як авокадо та крабове м’ясо, і зробив ролл з рисом назовні, що було \n" +
                "нетипово для традиційних японських суші. \n" +
                "Цей ролл швидко набув популярності не тільки в США, але й по всьому світу, ставши \n" +
                "символом американської адаптації японської кухні. Ролл “Каліфорнія” часто \n" +
                "використовується як вступ до світу суші для тих, хто вперше пробує цю страву, завдяки \n" +
                "своєму м’якому смаку та відсутності сирої риби. ","https://en.wikipedia.org/wiki/California_roll",
                "/images/8Kaliphorn.png");
        Dish myasopoFranz = new Dish(9L,"М'ясо по французьки","Рецепт: \n" +
                "Наріжте м’ясо на порційні шматки та відбийте їх до тонкості близько 1 см. Посоліть і \n" +
                "поперчіть м’ясо з обох боків. Наріжте цибулю кільцями або півкільцями. Наріжте \n" +
                "картоплю на тонкі кружечки. Натріть сир на тертці. На деко, застелене пергаментним \n" +
                "папером, викладіть шматки м’яса. Зверху на м’ясо викладіть цибулю, потім картоплю. \n" +
                "Посипте тертим сиром і змастіть майонезом. Запікайте в духовці при 180°C близько 40 \n" +
                "хвилин або до готовності м’яса та золотистої скоринки на сирі. \n" +
                "Історія: \n" +
                "Забавний факт про \"М'ясо по-французьки\" полягає в тому, що багато людей в різних \n" +
                "країнах вважають, що ця страва дійсно походить з Франції, і нерідко французькі туристи \n" +
                "дивуються, коли зустрічають її в меню ресторанів у слов'янських країнах. Часто французи \n" +
                "навіть не підозрюють про існування цієї страви, адже в їх національній кухні немає \n" +
                "аналогів з такою назвою. Це створює кумедні ситуації, коли французькі гості намагаються \n" +
                "пояснити, що вони ніколи не їли \"Mouton à la française\" (буквальний переклад \"М'яса по\n" +
                "французьки\") у себе на батьківщині. ","https://uk.wikipedia.org/wiki/%D0%9C%27%D1%8F%D1%81%D0%BE_%D0%BF%D0%BE-%D1%84%D1%80%D0%B0%D0%BD%D1%86%D1%83%D0%B7%D1%8C%D0%BA%D0%B8#:~:text=%D0%9C'%D1%8F%D1%81%D0%BE%20%D0%BF%D0%BE%2D%D1%84%D1%80%D0%B0%D0%BD%D1%86%D1%83%D0%B7%D1%8C%D0%BA%D0%B8%20(,%D1%8F%D0%BB%D0%BE%D0%B2%D0%B8%D1%87%D0%B8%D0%BD%D0%B0)%2C%20%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D0%BF%D0%BB%D1%96%20%D1%82%D0%B0%20%D1%81%D0%B8%D1%80%D1%83.",
                "/images/9myaso.png");
        Dish Shuba = new Dish(10L,"Шуба","Рецепт: \n" +
                "Відваріть картоплю, моркву, буряк та яйця до готовності. Очистіть овочі та яйця, а потім \n" +
                "натріть їх на крупній тертці. Наріжте оселедець на дрібні шматочки. Наріжте цибулю \n" +
                "дрібно. Викладіть інгредієнти слоями у велику тарілку або форму для салату, змащуючи \n" +
                "кожен слой майонезом. Зверху змастіть майонезом і прикрасьте за бажанням. Дайте \n" +
                "салату настоятися в холодильнику протягом кількох годин перед подачею. \n" +
                "Історія: \n" +
                "Салат “Шуба” має цікаву історію, яка починається в першій половині XIX століття. Цей \n" +
                "салат виготовлявся з інгредієнтів, типових для шуби, і був популярний в кухнях \n" +
                "Скандинавії та Німеччини. Однак справжня популярність салата “Шуба” прийшла в 60-70\n" +
                "х роках минулого століття, коли він став невід'ємною частиною новорічного столу. \n" +
                "Назва \"ШУБА\" — це абревіатура, що означає \"Шовінізму та Упадку — Бойкот і \n" +
                "Анафема\". Таким чином, цей салат не лише став популярним святковим блюдом, але й \n" +
                "мав символічне значення, виражаючи протест проти політичних настроїв того часу.","https://uk.wikipedia.org/wiki/%D0%9E%D1%81%D0%B5%D0%BB%D0%B5%D0%B4%D0%B5%D1%86%D1%8C_%D0%BF%D1%96%D0%B4_%D1%88%D1%83%D0%B1%D0%BE%D1%8E#:~:text=%D0%9E%D1%81%D0%B5%D0%BB%D0%B5%D0%B4%D0%B5%D1%86%D1%8C%20%D0%BF%D1%96%D0%B4%20%D1%88%D1%83%D0%B1%D0%BE%D1%8E%20(%D1%87%D0%B0%D1%81%D1%82%D0%BE%20%C2%AB%D1%88%D1%83%D0%B1%D0%B0,%2C%20%D0%BE%D0%B2%D0%BE%D1%87%D1%96%D0%B2%2C%20%D1%8F%D1%94%D1%86%D1%8C%20%D1%96%20%D0%BC%D0%B0%D0%B9%D0%BE%D0%BD%D0%B5%D0%B7%D1%83",
                "/images/10Shuba.png");
        dishRepository.saveAll(Arrays.asList(
                buter, Golubci, Naggetsi, Hachap, Margarit, Peperoni,
                Philadelp, Kaliphorn, myasopoFranz, Shuba
        ));
        //DischesHaveProducts
        dishHaveProductsRepository.saveAll(Arrays.asList(
                new DishHaveProducts(1L,buter,bread,1),
                new DishHaveProducts(2L,buter,vershkmasl,2),
                new DishHaveProducts(3L,buter,kovbasa,3),
                new DishHaveProducts(4L,Golubci,kapusta,1),
                new DishHaveProducts(5L,Golubci,Pharsh,2),
                new DishHaveProducts(6L,Golubci,TomatPasta,3),
                new DishHaveProducts(7L,Naggetsi,KurkaGrudka,1),
                new DishHaveProducts(8L,Naggetsi,muka,2),
                new DishHaveProducts(9L,Naggetsi,Egg,3),
                new DishHaveProducts(10L,Naggetsi,Suhar,4),
                new DishHaveProducts(11L,Hachap,Tisto,1),
                new DishHaveProducts(12L,Hachap,Cheese,2),
                new DishHaveProducts(13L,Hachap,Egg,3),
                new DishHaveProducts(14L,Hachap,Zelen,4),
                new DishHaveProducts(15L,Margarit,Tisto,1),
                new DishHaveProducts(16L,Margarit,Tomatsous,2),
                new DishHaveProducts(17L,Margarit,Mozarella,3),
                new DishHaveProducts(18L,Margarit,Bazilik,4),
                new DishHaveProducts(19L,Peperoni,Tisto,1),
                new DishHaveProducts(20L,Peperoni,Sous,2),
                new DishHaveProducts(21L,Peperoni,Cheese,3),
                new DishHaveProducts(22L,Peperoni,Salami,4),
                new DishHaveProducts(23L,Philadelp,Nori,1),
                new DishHaveProducts(24L,Philadelp,Ris,2),
                new DishHaveProducts(25L,Philadelp,Vershkcheese,3),
                new DishHaveProducts(26L,Philadelp,Cucumber,4),
                new DishHaveProducts(27L,Philadelp,Redfish,5),
                new DishHaveProducts(28L,Kaliphorn,Nori,1),
                new DishHaveProducts(29L,Kaliphorn,Ris,2),
                new DishHaveProducts(30L,Kaliphorn,Avokado,3),
                new DishHaveProducts(31L,Kaliphorn,Redfish,4),
                new DishHaveProducts(32L,Kaliphorn,Masago,5),
                new DishHaveProducts(32L,Kaliphorn,Masago,5),
                new DishHaveProducts(33L,myasopoFranz,meat,1),
                new DishHaveProducts(34L,myasopoFranz,Cibula,2),
                new DishHaveProducts(35L,myasopoFranz,Potato,3),
                new DishHaveProducts(36L,myasopoFranz,Cheese,4),
                new DishHaveProducts(37L,myasopoFranz,Mayonezii,5),
                new DishHaveProducts(38L,Shuba,Oseledez,1),
                new DishHaveProducts(39L,Shuba,Cibula,2),
                new DishHaveProducts(40L,Shuba,Potato,3),
                new DishHaveProducts(41L,Shuba,Egg,4),
                new DishHaveProducts(42L,Shuba,Carrot,5),
                new DishHaveProducts(43L,Shuba,Buryak,6),
                new DishHaveProducts(44L,Shuba,Mayonezii,7)));
        //Levels
        levelRepository.saveAll(Arrays.asList(
                new Level(1L,"Цей простий, але універсальний продукт харчування складається з кількох \n" +
                        "базових компонентів і часто є швидким рішенням для сніданку або перекусу \n" +
                        "в дорозі.",buter,10),
                new Level(2L,"Ця традиційна страва східноєвропейської кухні які є м'якими соковитими \n" +
                        "рулетиками. Вона часто подається на сімейних вечерях та великих зборах, \n" +
                        "символізуючи затишок та гостинність.",Golubci,15),
                new Level(3L,"Ця страва стала культовим вибором у меню таких знаменитих мереж фаст\n" +
                        "фуду, як KFC та McDonald's. Воно любимо за свою хрумку текстуру та \n" +
                        "універсальність, ідеально підходить для поєднання з різноманітними \n" +
                        "соусами. ",Naggetsi,20),
                new Level(4L,"Це популярна грузинська страва. Вона може бути як основною стравою, так і \n" +
                        "закускою. Вгадайте назву цих традиційних кавказьких ласощів. ",Hachap,25),
                new Level(5L,"Це класична італійська їжа, яка символізує кольори італійського прапора: \n" +
                        "зелений, білий та червоний. Ще можна сказати що назва цієї страви \n" +
                        "збігається з одним жіночим ім'ям.",Margarit,33),
                new Level(6L,"Ця популярна кругла італійська їжа, що відрізняється своїм пікантним \n" +
                        "смаком завдяки особливому інгредієнту – гострій салямі.",Peperoni,45),
                new Level(7L,"Названий на честь відомого американського міста, став популярним за \n" +
                        "межами Японії завдяки своїй унікальній кремовій текстурі та м'якому смаку. \n" +
                        "Він часто вибирається як початкова страва для тих, хто тільки-но починає \n" +
                        "своє знайомство з японською кухнею. \n",Philadelp,67),
                new Level(8L,"Цей рол є одним із найпопулярніших у світі та визнаний введенням у світ \n" +
                        "суші для багатьох новачків, також його назва пов'язана з батьківщиною \n" +
                        "Міккі-Мауса та Дональда Дака.",Kaliphorn,78),
                new Level(9L,"З'їдавши цю страву ви можете відчути смак країни кохання, але незважаючи \n" +
                        "на свою назву, не має французького коріння. ",myasopoFranz,88),
                new Level(10L,"Це знаменита страва із слоїв овочів та риби, що часто прикрашає святковий \n" +
                        "стіл на новий рік.",Shuba,100)));
        //додані адміністратори до бд, ломает бд с ключами.
        //resolved; одноразово вставить данные про  первого админа, а далее закоментить его
    /*User admin1= new User("Administra2","Adminbla2@",Role.ADMIN_ROLE);
    if(admin1.getLogin().equals(userService.login(admin1.getLogin()))){

    }
    else { userService.save(admin1);}
    */
    }

    @Autowired
    private  final HttpSession session;
    @Autowired
    private final ValidationService validationService;
    @Autowired
    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password, Model model) {

        User user = userService.login(login);
        initData();//поки немає адмін панелі для додавання моделєй, левели закидує через цю функцію
        if (user == null) {
            model.addAttribute("error", "Логіну не існує");
        } else {
            if (!user.getPassword().equals(password)) {
                model.addAttribute("error", "Не правильний пароль");
                return "index";
            } else {
                session.setUser(user);
                if (user.getRole() == Role.ADMIN_ROLE) {
                    return "redirect:/admin/" + user.getId();
                } else {
                    return "redirect:/" + user.getId();
                }
            }
        }
        return "index";
    }

    @PostMapping("/registration")
    public String register(@RequestParam String login, @RequestParam String password,
                           @RequestParam String passwordCheck, Model model) {
        initData();//поки немає адмін панелі для додавання моделєй, левели закидує через цю функцію
        // Перевірка валідності логіну
        if (!validationService.isLoginValid(login)) {
            model.addAttribute("error", "Невалідний логін");
            return "index";
        }
        // Перевірка унікальності нового логіну у бд
        if (!validationService.isLoginUnique(login)) {
            model.addAttribute("error", "Логін вже занят");
            return "index";
        }
        if(!validationService.isValidPassword(password)){
            model.addAttribute("error", "Невалідний пароль");
            return "index";
        }
        if (!password.equals(passwordCheck)) {
            model.addAttribute("error", "Паролі не співпадають");
            return "index";
        }
        User newUser = new User(login, password);
        userService.save(newUser);
        session.setUser(newUser);
        //Без проверки на роль, ибо в регистрационной форме админ зарегаться не может
        //ведь ему создаст аккаунт,другой админ
        return "redirect:/" + newUser.getId();
    }
}
