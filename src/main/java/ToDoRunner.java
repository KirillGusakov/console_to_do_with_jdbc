import com.dao.CategoriesDao;
import com.dao.TasksDao;
import com.entity.CategoriesEntity;
import com.entity.TasksEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class ToDoRunner {
    private static final Scanner sc = new Scanner(System.in);
    private static final TasksDao tasksDao = TasksDao.get();

    public static void main(String[] args) {
        printWelcomeMessage();
        int num = sc.nextInt();
        while (num != 0) {
            mainMenu(num, TasksDao.get());
            num = sc.nextInt();
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("Добро пожаловать в простое консольное to_do приложение\n" +
                           "Доступные функции:\n" +
                           "1. Вставить задачу\n" +
                           "2. Удалить задачу\n" +
                           "3. Обновить задачу\n");
    }

    private static void mainMenu(int id, TasksDao tasksDao) {
        switch (id) {
            case 1 -> insertTask();
            case 2 -> deleteTask();
            default -> System.out.println("Неверный выбор. Повторите ввод.");
        }

    }

    private static void insertTask() {
        TasksEntity taskEntity = new TasksEntity();

        System.out.println("Введите имя задачи");
        sc.next();
        taskEntity.setTaskName(sc.nextLine());

        LocalDate dueDate = getUserInputDate("Введите дедлайн задачи (в формате dd.MM.yyyy): ");
        taskEntity.setDueDate(dueDate);

        System.out.println("Выберите номер категории из списка");
        Map<Integer, String> categoriesMap = CategoriesDao.getHashMap();
        categoriesMap.forEach((key, value) -> System.out.println(key + " " + value));

        int categoryNum = sc.nextInt();
        taskEntity.setCategories(new CategoriesEntity(categoryNum, categoriesMap.get(categoryNum)));

        tasksDao.create(taskEntity);
    }

    private static void deleteTask() {
        System.out.println("Введите номер задачи для удаления:");
        tasksDao.getAllTasks().forEach(System.out::println);
        int taskId = sc.nextInt();
        System.out.println(tasksDao.delete(taskId));
    }

    private static LocalDate getUserInputDate(String prompt) {
        System.out.println(prompt);
        return LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


}
